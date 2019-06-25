package me.spider.contestbot.contestcommand.submissions;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SubmissionMapper
{
    @Select("SELECT COUNT(*) FROM contest.submission WHERE contest_id = #{contestID}")
    Long getTotalSubmissionCount(@Param("contestID") long contestID);

    @Select("SELECT COUNT(*) FROM contest.submission WHERE submitter_id = #{submitterID} AND contest_id = #{contestID}")
    Long getSubmissionCount(@Param("submitterID") long submitterID, @Param("contestID") long contestID);

    @Insert("INSERT INTO contest.submission (id, submitter_id, submission_content, contest_id) VALUES " +
            "(#{messageID}, #{submitterID}, #{content}, #{contestID}")
    void addSubmission(@Param("messageID") long messageID, @Param("submitterID") long submitterID, @Param("content") String content, @Param("contestID") long contestID);

    @Delete("DELETE FROM contest.submission WHERE submitter_id = #{submitterID} AND submission_id = #{submissionID}")
    void removeSubmission(@Param("submitterID") long submitterID, @Param("submissionID")long submissionID);
}
