package me.spider.contestbot.contestcommand.submissions;

import java.util.ArrayList;

public class SubmissionCollection {
    private ArrayList<Submission> submissions = new ArrayList<>();
    private String contestID;

    public SubmissionCollection(String contestID) {
        this.contestID = contestID;
    }

    public SubmissionCollection(String contestID, ArrayList<Submission> submissions) {
        this.submissions = submissions;
        this.contestID = contestID;
    }

    public boolean addSubmission(Submission submission) {
        return submissions.add(submission);
    }

    public boolean removeSubmission(Submission submission) {
        return submissions.remove(submission);
    }

    public void removeSubmission(String submissionID) {
        submissions.forEach(sub -> {
            if (sub.getSubmissionID().equals(submissionID)) {
                submissions.remove(sub);
            }
        });
    }

    public void removeAllSubmissions(String unluckyUserID){
        submissions.forEach(sub -> {
            if(sub.getSubmitterID().equals(unluckyUserID)){
                submissions.remove(sub);
            }
        });
    }

    public boolean hasSubmitted(String userID) {
        for (Submission sub : submissions) {
            if (sub.getSubmitterID().equals(userID)) {
                return true;
            }
        }
        return false;
    }

    public int countSubmissions(String userID){
        int amount = 0;
        for(Submission sub : submissions){
            if(sub.getSubmitterID().equals(userID)){
                amount++;
            }
        }
        return amount;
    }
}
