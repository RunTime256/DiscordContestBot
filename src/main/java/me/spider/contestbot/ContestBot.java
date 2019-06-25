package me.spider.contestbot;

import me.spider.contestbot.sql.Session;
import me.spider.contestbot.sql.SessionFactory;

import java.io.IOException;

public class ContestBot
{
    public static void main(String[] args)
    {
        try
        {
            SessionFactory.init("PostgreSQLConfig.xml");
        }
        catch (IOException e)
        {
            System.out.println("IOException on SQL init");
            e.printStackTrace();
        }
    }
}
