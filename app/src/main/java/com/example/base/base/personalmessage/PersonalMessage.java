package com.example.base.base.personalmessage;

import android.icu.text.StringPrepParseException;

/**
 * Created by Devam on 03-Oct-17.
 */

public class PersonalMessage {
    private String memberName,memberMessage,memberTime;
    private int memberPic;

    public PersonalMessage(){}

    public PersonalMessage(int pic,String name,String message,String time){
        this.memberPic = pic;
        this.memberName = name;
        this.memberMessage = message;
        this.memberTime = time;
    }

    public int getMemberPic() {
        return memberPic;
    }

    public String getMemberMessage() {
        return memberMessage;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberTime() {
        return memberTime;
    }

    public void setMemberMessage(String memberMessage) {
        this.memberMessage = memberMessage;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberPic(int memberPic) {
        this.memberPic = memberPic;
    }

    public void setMemberTime(String memberTime) {
        this.memberTime = memberTime;
    }

}
