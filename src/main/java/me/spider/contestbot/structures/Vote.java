package me.spider.contestbot.structures;

import lombok.Data;
import lombok.Getter;
import net.dv8tion.jda.core.entities.Message;

public @Data class Vote {
    private @Getter String voterID;
    private @Getter String submitterID;
    private @Getter String submissionID;

    public Vote(String userID, Message message){
        this.voterID = userID;
        this.submitterID = message.getAuthor().getId();
        this.submissionID = message.getId();
    }

    public Vote(String userID, String submitterID, String submissionID){
        this.voterID = userID;
        this.submitterID = submitterID;
        this.submissionID = submissionID;
    }
}
