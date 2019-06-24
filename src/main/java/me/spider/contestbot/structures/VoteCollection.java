package me.spider.contestbot.structures;

import net.dv8tion.jda.core.entities.Message;

import java.util.ArrayList;

public class VoteCollection {
    private ArrayList<Vote> votes = new ArrayList<>();
    private String contestID;

    public VoteCollection(String channelID){
        this.contestID = channelID;
    }

    public VoteCollection(String channelID, ArrayList<Vote> votes){
        this.contestID = channelID;
        this.votes = votes;
    }

    public boolean addVote(Vote v){
        return votes.add(v);
    }

    public boolean removeVote(Vote v){
        return votes.remove(v);
    }

    public void removeVote(String userID, String submissionID){
        votes.forEach(v -> {
            if(v.getSubmissionID().equals(submissionID) && v.getVoterID().equals(userID)){
                votes.remove(v);
            }
        });
    }

    public void clearVotesFromSubmission(String submissionID){
        votes.forEach(v -> {
            if(v.getSubmissionID().equals(submissionID)){
                votes.remove(v);
            }
        });
    }

    public void clearVotesVotesFromUser(String userID){
        votes.forEach(v -> {
            if(v.getVoterID().equals(userID)){
                votes.remove(v);
            }
        });
    }

    public boolean hasUserVoted(String userID){
        for (Vote vote : votes) {
            if(vote.getVoterID().equals(userID)){
                return true;
            }
        }
        return false;
    }

    public boolean hasUserVoted(String userID, Message submission){
        String submissionID = submission.getId();
        for(Vote vote : votes){
            if(vote.getSubmissionID().equals(submissionID)){
                if(vote.getVoterID().equals(userID)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasUserVoted(String userID, String submitterID){
        for(Vote vote : votes){
            if(vote.getSubmitterID().equals(submitterID)){
                if(vote.getVoterID().equals(userID)){
                    return true;
                }
            }
        }
        return false;
    }
}
