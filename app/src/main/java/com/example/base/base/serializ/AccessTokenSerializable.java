package com.example.base.base.serializ;

import com.base.Auth.AccessToken;

import java.io.Serializable;

/**
 * Created by Devam on 13-Nov-17.
 */

public class AccessTokenSerializable implements Serializable {

    private AccessToken accessToken;

    public AccessToken getAccessToken()
    {
        return this.accessToken;
    }

    public void setAccessToken(AccessToken accessToken)
    {
        this.accessToken = accessToken;
    }


}
