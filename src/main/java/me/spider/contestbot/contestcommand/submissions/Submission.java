package me.spider.contestbot.contestcommand.submissions;

import lombok.Data;
import lombok.Getter;

public @Data class Submission {
    private @Getter long submitterID;
    private @Getter long submissionID;
    private @Getter String content;

    public Submission(long submitterID, long submissionID, String content)
    {
        this.submitterID = submitterID;
        this.submissionID = submissionID;
        this.content = content;
    }
}
