package com.example.base.base.team;

/**
 * Created by Devam on 13-Nov-17.
 */

public class TeamList  {

    String teamName,teamMessage,teamSlug,teamPic;

    public TeamList(String teamName,String teamSlug,String teamMessage,String teamPic)
    {
        this.teamMessage = teamMessage;
        this.teamName = teamName;
        this.teamPic = teamPic;
        this.teamSlug = teamSlug;
    }

    public String getTeamPic() {
        return teamPic;
    }

    public String getTeamMessage() {
        return teamMessage;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamSlug() {
        return teamSlug;
    }

    public void setTeamMessage(String teamMessage) {
        this.teamMessage = teamMessage;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamPic(String teamPic) {
        this.teamPic = teamPic;
    }

    public void setTeamSlug(String teamSlug) {
        this.teamSlug = teamSlug;
    }
}
