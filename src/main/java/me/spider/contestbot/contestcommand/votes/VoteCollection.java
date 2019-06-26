package me.spider.contestbot.contestcommand.votes;

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

    public void removeVote(long userID, long submissionID){
        votes.forEach(v -> {
            if(v.getSubmissionID() == submissionID && v.getVoterID() == userID){
                votes.remove(v);
            }
        });
    }

    public void clearVotesFromSubmission(long submissionID){
        votes.forEach(v -> {
            if(v.getSubmissionID() == submissionID){
                votes.remove(v);
            }
        });
    }

    public void clearVotesVotesFromUser(long userID){
        votes.forEach(v -> {
            if(v.getVoterID() == userID){
                votes.remove(v);
            }
        });
    }

    public boolean hasUserVoted(long userID){
        for (Vote vote : votes) {
            if(vote.getVoterID() == userID){
                return true;
            }
        }
        return false;
    }

    public boolean hasUserVoted(long userID, Message submission){
        long submissionID = submission.getIdLong();
        for(Vote vote : votes){
            if(vote.getSubmissionID() == submissionID){
                if(vote.getVoterID() == userID){
                    return true;
                }
            }
        }
        return false;
    }

}
