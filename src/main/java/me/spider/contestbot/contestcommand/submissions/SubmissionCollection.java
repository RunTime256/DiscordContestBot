package me.spider.contestbot.contestcommand.submissions;

import me.spider.contestbot.contestcommand.submissions.exceptions.ReachedMaxSubmissionsException;
import me.spider.contestbot.sql.Session;
import me.spider.contestbot.sql.SessionFactory;

public class SubmissionCollection
{
    private int maxSubmissions;

    public SubmissionCollection(int maxSubmissions)
    {
        this.maxSubmissions = maxSubmissions;
    }

    public int getSubmissionCount(long contestID)
    {
        try (Session session = SessionFactory.getSession())
        {
            SubmissionMapper mapper = session.getMapper(SubmissionMapper.class);
            return mapper.getTotalSubmissionCount(contestID).intValue();
        }
    }

    public void addSubmission(long submitterID, long submissionID, String content, long contestID) throws ReachedMaxSubmissionsException
    {
        if (hasReachedMaxSubmissions(submitterID, contestID))
            throw new ReachedMaxSubmissionsException();

        try (Session session = SessionFactory.getSession())
        {
            SubmissionMapper mapper = session.getMapper(SubmissionMapper.class);
            mapper.addSubmission(submissionID, submitterID, content, contestID);
        }
    }

    public void removeSubmission(long submitterID, long submissionID)
    {
        try (Session session = SessionFactory.getSession())
        {
            SubmissionMapper mapper = session.getMapper(SubmissionMapper.class);
            mapper.removeSubmission(submitterID, submissionID);
        }
    }

    private boolean hasReachedMaxSubmissions(long submitterID, long contestID)
    {
        try (Session session = SessionFactory.getSession())
        {
            SubmissionMapper mapper = session.getMapper(SubmissionMapper.class);
            return mapper.getSubmissionCount(submitterID, contestID) >= maxSubmissions;
        }
    }
}
