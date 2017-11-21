package com.example.base.base.personalmessage;

import android.icu.text.StringPrepParseException;

/**
 * Created by Devam on 03-Oct-17.
 */

public class PersonalMessage {
    private String memberName,memberMessage,memberTime;
    private String memberPic;
    private int memberId;

    public PersonalMessage(){}

    public PersonalMessage(String pic,String name,String message,String time,int MemberId){
        this.memberPic = pic;
        this.memberName = name;
        this.memberMessage = message;
        this.memberTime = time;
        this.memberId = MemberId;
    }

    public String getMemberPic() {
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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberMessage(String memberMessage) {
        this.memberMessage = memberMessage;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberPic(String memberPic) {
        this.memberPic = memberPic;
    }

    public void setMemberTime(String memberTime) {
        this.memberTime = memberTime;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
