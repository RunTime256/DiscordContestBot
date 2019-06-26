package me.spider.contestbot.contestcommand;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.AllArgsConstructor;
import me.spider.contestbot.ContestBot;
import me.spider.contestbot.contestcommand.contests.ContestCollection;
import me.spider.contestbot.contestcommand.structures.Category;
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
                            create(event, me.spider.contestbot.contestcommand.structures.Category.getCategory(category), suggestion, rules, period)
                        )
                    )
                )
            );
        }



        private void create(CommandEvent event, me.spider.contestbot.contestcommand.structures.Category category, String suggestion, String rules, Period period) {

        }

        // These are all placeholders, I'm not sure what types these will end up being


        private String findCategory(Message message){
            return message.getContentRaw();
        }

        private String findSuggestion(Message message) {
            return message.getContentRaw();
        }

        private String findRules(Message message) {
            return message.getContentRaw();
        }

        private Period findPeriod(Message message) {
            return new Period(getEpochFromText(message.getContentRaw())); // This is a bad way to get dates
        }

        //todo this can be improved a bit
        private long getEpochFromText(String input) throws IllegalArgumentException{
            String[] single = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
            String[] differentNums = input.toLowerCase().split("and");
            long epoch = 0;
            for(String str : differentNums){
                String[] pieces = str.split(" ");
                long amount = -1;
                String potentialNum = pieces[0];
                if(potentialNum.matches("\\d+")){
                    amount = Integer.parseInt(pieces[0]);
                } else {
                    for(int i = 0; i < single.length; i++){
                        if(potentialNum.equals(single[i])){
                            amount = i + 1;
                        }
                    }
                }
                if(amount == -1){
                    throw new IllegalArgumentException();
                }
                String unit = pieces[1];
                switch (unit) {
                    case "day":
                    case "days":
                        epoch += TimeUnit.DAYS.toMillis(amount);
                        break;
                    case "hour":
                    case "hours":
                        epoch += TimeUnit.HOURS.toMillis(amount);
                        break;
                    case "minute":
                    case "minutes":
                        epoch += TimeUnit.MINUTES.toMillis(amount);
                        break;
                    case "week":
                    case "weeks":
                        epoch += TimeUnit.DAYS.toMillis(1) * 7;
                        break;
                }


            }

            return -1;
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
