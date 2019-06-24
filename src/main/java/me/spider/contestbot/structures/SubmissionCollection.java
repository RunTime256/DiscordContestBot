package me.spider.contestbot.structures;

import java.util.ArrayList;

public class SubmissionCollection {
    private ArrayList<Submission> submissions = new ArrayList<>();
    private String contestID;
    public SubmissionCollection(String contestID){
        this.contestID = contestID;
    }
    public SubmissionCollection(String contestID, ArrayList<Submission> submissions){
        this.submissions = submissions;
        this.contestID = contestID;
    }

    public boolean addSubmission(Submission submission){
        return submissions.add(submission);
    }

    public boolean removeSubmission(Submission submission){
        return submissions.remove(submission);
    }

    public boolean removeSubmission(String submission){
        submissions.forEach( sub -> {

        });
        return false;
    }
}
