package me.spider.contestbot.contestcommand.votes;

import me.spider.contestbot.contestcommand.votes.exceptions.PreviouslyVotedForSubmissionException;
import me.spider.contestbot.contestcommand.votes.exceptions.ReachedMaxVotesException;
import me.spider.contestbot.sql.Session;
import me.spider.contestbot.sql.SessionFactory;

public class VoteCollection {
    private int maxVotes;

    public VoteCollection(int maxVotes)
    {
        this.maxVotes = maxVotes;
    }

    public long getVoteTotal(long submissionID, long contestID)
    {
        try (Session session = SessionFactory.getSession())
        {
            VoteMapper mapper = session.getMapper(VoteMapper.class);
            return mapper.getTotalVotes(submissionID, contestID);
        }
    }

    public void addVote(long messageID, long voterID, long submissionID, long contestID) throws PreviouslyVotedForSubmissionException, ReachedMaxVotesException
    {
        if (hasVotedForSubmission(voterID, submissionID, contestID))
            throw new PreviouslyVotedForSubmissionException();
        if (hasReachedMaxVotes(voterID, contestID))
            throw new ReachedMaxVotesException();

        try (Session session = SessionFactory.getSession())
        {
            VoteMapper mapper = session.getMapper(VoteMapper.class);
            mapper.addVote(messageID, voterID, submissionID, contestID);
        }
    }

    public void removeVote(long voterID, long submissionID)
    {
        try (Session session = SessionFactory.getSession())
        {
            VoteMapper mapper = session.getMapper(VoteMapper.class);
            mapper.removeVote(voterID, submissionID);
        }
    }

    private boolean hasVotedForSubmission(long voterID, long submissionID, long contestID)
    {
        try (Session session = SessionFactory.getSession())
        {
            VoteMapper mapper = session.getMapper(VoteMapper.class);
            return mapper.getSubmissionVoteCount(voterID, submissionID, contestID) != 0;
        }
    }

    private boolean hasReachedMaxVotes(long voterID, long contestID)
    {
        try (Session session = SessionFactory.getSession())
        {
            VoteMapper mapper = session.getMapper(VoteMapper.class);
            return mapper.getVoteCount(voterID, contestID) >= maxVotes;
        }
    }
}
