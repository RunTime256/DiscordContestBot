package me.spider.contestbot.contestcommand.votes;

import lombok.Data;
import lombok.Getter;
import net.dv8tion.jda.core.entities.Message;

public @Data class Vote {
    private @Getter long voterID;
    private @Getter long submissionID;

    public Vote(long voterID, long submissionID)
    {
        this.voterID = voterID;
        this.submissionID = submissionID;
    }
}
