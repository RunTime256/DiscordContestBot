package me.spider.contestbot.structures;

import lombok.Data;
import net.dv8tion.jda.core.entities.Message;

public @Data
class Submission {
    private String submissionID;
    private String submitterID;
    private Message submissionContent;

    public Submission(Message submissionContent){
        this.submissionContent = submissionContent;
        this.submissionID = submissionContent.getId();
        this.submitterID = submissionContent.getAuthor().getId();
    }

}
