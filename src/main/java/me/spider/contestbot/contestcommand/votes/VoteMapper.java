package me.spider.contestbot.contestcommand.votes;

import org.apache.ibatis.annotations.*;

public interface VoteMapper
{
    @Select("SELECT COUNT(*) FROM contest.vote WHERE submission_id = #{submissionID} AND contest_id = #{contestID}")
    Long getTotalVotes(@Param("submissionID") long submissionID, @Param("contestID") long contestID);

    @Select("SELECT COUNT(*) FROM contest.vote WHERE voter_id = #{voterID} AND contest_id = #{contestID}")
    Long getVoteCount(@Param("voterID") long voterID, @Param("contestID") long contestID);

    @Select("SELECT COUNT(*) FROM contest.vote WHERE " +
            "voter_id = #{voterID} AND submission_id = #{submissionID} AND contest_id = #{contestID}")
    Long getSubmissionVoteCount(@Param("voterID") long voterID, @Param("submissionID")long submissionID, @Param("contestID") long contestID);

    @Insert("INSERT INTO contest.vote (id, voter_id, submission_id, contest_id) VALUES " +
            "(#{messageID}, #{voterID}, #{submissionID}, #{contestID}")
    void addVote(@Param("messageID") long messageID, @Param("voterID") long voterID, @Param("submissionID")long submissionID, @Param("contestID") long contestID);

    @Delete("DELETE FROM contest.vote WHERE voter_id = #{voterID} AND submission_id = #{submissionID}")
    void removeVote(@Param("voterID") long voterID, @Param("submissionID")long submissionID);
}
