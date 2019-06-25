package me.spider.contestbot.sql;

import org.apache.ibatis.session.SqlSession;

public class Session implements AutoCloseable
{
    private SqlSession session;

    Session(SqlSession session)
    {
        this.session = session;
    }

    public <T> T getMapper(Class<T> classMap)
    {
        return session.getMapper(classMap);
    }

    @Override
    public void close()
    {
        session.commit();
        session.close();
    }
}