package me.spider.contestbot.contestcommand.structures;

import lombok.Data;
import lombok.Getter;

import java.util.Calendar;

public @Data class Period {
    private @Getter long startTime;
    private @Getter long endTime;

    public Period(long startTime, long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Period(long endTime){
        this.startTime = Calendar.getInstance().getTimeInMillis();
        this.endTime = endTime;
    }

    public boolean hasEnded(){
        return endTime <= Calendar.getInstance().getTimeInMillis();
    }

    public boolean isInPeriod(){
        long now = Calendar.getInstance().getTimeInMillis();
        return  now >= startTime && now <= endTime;
    }

    public boolean isInPeriod(long epoch){
        return  epoch >= startTime && epoch <= endTime;
    }
}
