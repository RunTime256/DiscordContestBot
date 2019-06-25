package me.spider.contestbot.contestcommand.votes;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface VoteMapper
{
    @Select("SELECT voter_id, submission_id FROM contest.vote WHERE contest_id = #{contestID}")
    @Results(value = {
            @Result(property = "voterID", column = "voter_id"),
            @Result(property = "submissionID", column = "submission_id")
    })
    List<Vote> getVotes(long contestID);

    @Select("SELECT count(*) AS Count FROM contest.vote WHERE voter_id = #{voterID} AND contest_id = #{contestID}")
    long getVoteCount(long voterID, long contestID);

    @Insert("INSERT INTO contest.vote (id, voter_id, submission_id, contest_id) VALUES " +
            "(#{messageID}, #{voterID}, #{submissionID}, #{contestID}")
    void addVote(long messageID, long voterID, long submissionID, long contestID);

    @Delete("DELETE FROM contest.vote WHERE voter_id = #{voterID} AND submission_id = #{submissionID}")
    void removeVote(long voterID, long submissionID);
}
