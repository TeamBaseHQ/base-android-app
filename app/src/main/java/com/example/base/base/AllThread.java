package com.example.base.base;

/**
 * Created by Devam on 28-Oct-17.
 */

public class AllThread {
    private String threadName,threadTime,threadChannelName,threadChannelColor;
    private int  threadMemberPic;

    public AllThread(){}

    public AllThread(String threadName, String threadTime, String threadChannelName, String threadChannelColor, int threadMemberPic){

        this.threadName = threadName;
        this.threadTime = threadTime;
        this.threadChannelName = threadChannelName;
        this.threadChannelColor = threadChannelColor;
        this.threadMemberPic = threadMemberPic;

    }

    public int getThreadMemberPic() {
        return threadMemberPic;
    }

    public String getThreadChannelColor() {
        return threadChannelColor;
    }

    public String getThreadChannelName() {
        return threadChannelName;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getThreadTime() {
        return threadTime;
    }

    public void setThreadChannelColor(String threadChannelColor) {
        this.threadChannelColor = threadChannelColor;
    }

    public void setThreadChannelName(String threadChannelName) {
        this.threadChannelName = threadChannelName;
    }

    public void setThreadMemberPic(int threadMemberPic) {
        this.threadMemberPic = threadMemberPic;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void setThreadTime(String threadTime) {
        this.threadTime = threadTime;
    }

}
