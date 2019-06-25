package me.spider.contestbot.contestcommand;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.AllArgsConstructor;
import me.spider.contestbot.ContestBot;
import me.spider.contestbot.contestcommand.contests.ContestCollection;
import me.spider.contestbot.contestcommand.structures.Period;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.sql.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class ContestCommands {
    private static ContestCollection collection;

    public static void registerCommands(CommandClientBuilder builder) {
        builder.addCommand(new Create());
    }

    public static class Create extends Command {
        private Create() {
            name = "create";
            aliases = new String[] {
                "cr", "+", "make", "start"
            };
        }

        @Override
        protected void execute(CommandEvent event) {
            // PLACEHOLDER Insure a contest doesn't already exist in the channel
            new CommandQuery<String>("What category will the contest be? (emoji, art, music, writing)", this::findCategory, category ->
                new CommandQuery<String>("What suggestion will you give to applicants?", this::findSuggestion, suggestion ->
                    new CommandQuery<String>("What rules will you define?", this::findRules, rules ->
                        new CommandQuery<Period>("When will the contest end?", this::findPeriod, period ->
                            create(event, category, suggestion, rules, period)
                        )
                    )
                )
            );
        }

        private void create(CommandEvent event, String category, String suggestion, String rules, Period period) {

        }

        // These are all placeholders, I'm not sure what types these will end up being

        private String findCategory(Message message) {
            return message.getContentRaw();
        }

        private String findSuggestion(Message message) {
            return message.getContentRaw();
        }

        private String findRules(Message message) {
            return message.getContentRaw();
        }

        private Period findPeriod(Message message) {
            return new Period(Date.valueOf(message.getContentRaw()).getTime()); // This is a bad way to get dates
        }
    }

    // Waits for a response upon receiving a message
    @AllArgsConstructor
    private static class CommandQuery<T> {
        // Sent to end user to request the input
        private String input;

        // Turns this message into the wanted object
        private Function<Message, T> transform;

        private Consumer<T> handler;

        private void request(CommandEvent event) {
            event.reply(input);
            ContestBot.getWaiter().waitForEvent(MessageReceivedEvent.class,
                    newEvent -> event.getMember().equals(newEvent.getMember()) && event.getGuild().equals(newEvent.getGuild()),
                    e -> {
                        T transformed = transform.apply(e.getMessage());
                        if (transformed == null) event.reply("Invalid option provided.");
                        else handler.accept(transformed);
                    }, 1, TimeUnit.MINUTES, () -> event.reply("The command expired, please try again."));
        }
    }

}
