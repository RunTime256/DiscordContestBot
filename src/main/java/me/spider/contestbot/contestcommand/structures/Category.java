package me.spider.contestbot.contestcommand.structures;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public enum  Category {
    EMOJI("Emoji"), ART("Art"), MUSIC("Music"), WRITING("Writing");
    private final String categoryStr;
    Category(final String categoryStr){
        this.categoryStr = categoryStr;
    }

    public String getCategoryStr() {
        return categoryStr;
    }

    public static Category getCategory(String str)throws IllegalArgumentException{
        str = str.toLowerCase();
        if(str.equalsIgnoreCase("Emoji")){
            return Category.EMOJI;
        } else if(str.equalsIgnoreCase("Art")){
            return Category.ART;
        } else if(str.equalsIgnoreCase("Music")){
            return Category.MUSIC;
        } else if(str.equalsIgnoreCase("Writing")){
            return Category.WRITING;
        } else {
            throw new IllegalArgumentException();
        }

    }

    public static Category getCategory(Message message)throws IllegalArgumentException{
        String str = message.getContentRaw().toLowerCase();
        if(str.equalsIgnoreCase("Emoji")){
            return Category.EMOJI;
        } else if(str.equalsIgnoreCase("Art")){
            return Category.ART;
        } else if(str.equalsIgnoreCase("Music")){
            return Category.MUSIC;
        } else if(str.equalsIgnoreCase("Writing")){
            return Category.WRITING;
        } else {
            throw new IllegalArgumentException();
        }

    }


}
