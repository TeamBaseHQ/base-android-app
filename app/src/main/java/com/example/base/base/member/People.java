package com.example.base.base.member;

/**
 * Created by Devam on 23-Oct-17.
 */

public class People {

    private String peopleName,peopleStatus;
    private String peoplePic;
    private int peopleId;

    public People(){

    }

    public People(String name,String status,String pic,int peopleId){
        this.peopleName = name;
        this.peopleStatus = status;
        this.peoplePic = pic;
        this.peopleId = peopleId;
    }

    public String getPeoplePic() {
        return peoplePic;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public String getPeopleStatus() {
        return peopleStatus;
    }

    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public void setPeoplePic(String peoplePic) {
        this.peoplePic = peoplePic;
    }

    public void setPeopleStatus(String peopleStatus) {
        this.peopleStatus = peopleStatus;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }
}

