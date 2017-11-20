package com.example.base.base.channel;

/**
 * Created by Devam on 20-Nov-17.
 */

public class AddChannelMember {

    private String memberName;
    private int memberPic;
    private int memberId;

    public AddChannelMember(String memberName,int memberPic,int memberId)
    {
        this.memberName = memberName;
        this.memberPic = memberPic;
        this.memberId = memberId;
    }

    public int getMemberPic() {
        return memberPic;
    }

    public String getMemberName() {
        return memberName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberPic(int memberPic) {
        this.memberPic = memberPic;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
