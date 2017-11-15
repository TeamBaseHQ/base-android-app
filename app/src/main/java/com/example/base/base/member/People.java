package com.example.base.base.member;

/**
 * Created by Devam on 23-Oct-17.
 */

public class People {

    private String peopleName,peopleStatus;
    private int peoplePic;

    public People(){

    }

    public People(String name,String status,int pic){
        this.peopleName = name;
        this.peopleStatus = status;
        this.peoplePic = pic;
    }

    public int getPeoplePic() {
        return peoplePic;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public String getPeopleStatus() {
        return peopleStatus;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public void setPeoplePic(int peoplePic) {
        this.peoplePic = peoplePic;
    }

    public void setPeopleStatus(String peopleStatus) {
        this.peopleStatus = peopleStatus;
    }
}

