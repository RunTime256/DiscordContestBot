package me.spider.contestbot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.sun.corba.se.impl.activation.CommandHandler;
import lombok.Getter;
import me.spider.contestbot.contestcommand.ContestCommands;
import me.spider.contestbot.sql.SessionFactory;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContestBot {
    @Getter private static JSONObject dataJson;
    @Getter private static CommandClient commands;
    @Getter private static EventWaiter waiter;
    @Getter private static JDA jda;

    public static void main(String[] args) {
        try {
            // Data.json: String prefix, String token, String owner
            String dataRaw = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
            dataJson = new JSONObject(dataRaw);
        } catch (Exception e) {
            System.out.println("Exception reading data.json");
            e.printStackTrace();
        }
        try {
            SessionFactory.init("PostgreSQLConfig.xml");
        } catch (IOException e) {
            System.out.println("IOException on SQL init");
            e.printStackTrace();
        }
        try {
            CommandClientBuilder builder = new CommandClientBuilder()
                    .setPrefix(dataJson.getString("prefix"))
                    .useDefaultGame().setOwnerId(dataJson.getString("owner"));
            ContestCommands.registerCommands(builder);
            commands = builder.build();

            waiter = new EventWaiter();

            jda = new JDABuilder()
                    .setToken(dataJson.getString("token"))
                    .setStatus(OnlineStatus.IDLE).setGame(Game.playing("loading"))
                    .addEventListener(commands).addEventListener(waiter)
                    .build();
        } catch (Exception e) {
            System.out.println("Exception starting JDA");
            e.printStackTrace();
        }
    }
}
