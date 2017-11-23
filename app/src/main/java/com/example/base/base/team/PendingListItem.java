package com.example.base.base.team;

/**
 * Created by Devam on 22-Nov-17.
 */

public class PendingListItem {

    private String pendingListEmail;

    public PendingListItem(String pendingListEmail){
        this.pendingListEmail = pendingListEmail;
    }

    public String getPendingListEmail() {
        return pendingListEmail;
    }

    public void setPendingListEmail(String pendingListEmail) {
        this.pendingListEmail = pendingListEmail;
    }
}
