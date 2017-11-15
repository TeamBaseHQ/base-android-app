package com.example.base.base.thread;

/**
 * Created by Devam on 28-Oct-17.
 */

public class DisplayThread {
    private String threadName,threadTime,threadMessage;
    private int threadMemberPic;

    public DisplayThread(){}

    public DisplayThread(String threadName,String threadTime,String threadMessage,int threadmemberpic){

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

}
