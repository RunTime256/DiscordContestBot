package me.spider.contestbot.contestcommand.submissions;

import lombok.Data;
import lombok.Getter;
import net.dv8tion.jda.core.entities.Message;

public @Data
class Submission {
    private @Getter String submissionID;
    private @Getter String submitterID;
    private @Getter Message submissionContent;

    public Submission(Message submissionContent){
        this.submissionContent = submissionContent;
        this.submissionID = submissionContent.getId();
        this.submitterID = submissionContent.getAuthor().getId();
    }

}
