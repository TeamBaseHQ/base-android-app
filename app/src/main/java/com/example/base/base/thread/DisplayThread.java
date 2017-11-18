package com.example.base.base.thread;

/**
 * Created by Devam on 28-Oct-17.
 */

public class DisplayThread {
    private String threadName,threadTime,threadMessage,threadSlug,channelSlug;
    private int threadMemberPic;

    public DisplayThread(){}

    public DisplayThread(String channelSlug,String threadSlug,String threadName,String threadTime,String threadMessage,int threadmemberpic){

        this.channelSlug = channelSlug;
        this.threadSlug = threadSlug;
        this.threadName = threadName;
        this.threadTime = threadTime;
        this.threadMessage = threadMessage;
        this.threadMemberPic = threadmemberpic;

    }

    public void setThreadMemberPic(int threadmemberpic) {
        this.threadMemberPic = threadmemberpic;
    }

    public void setThreadMessage(String threadMessage) {
        this.threadMessage = threadMessage;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void setThreadTime(String threadTime) {
        this.threadTime = threadTime;
    }

    public void setThreadSlug(String threadSlug) {
        this.threadSlug = threadSlug;
    }

    public void setChannelSlug(String channelSlug) {
        this.channelSlug = channelSlug;
    }

    public int getThreadMemberPic() {
        return threadMemberPic;
    }

    public String getThreadMessage() {
        return threadMessage;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getThreadTime() {
        return threadTime;
    }

    public String getThreadSlug() {
        return threadSlug;
    }

    public String getChannelSlug() {
        return channelSlug;
    }
}
