package com.example.base.base.helper;

import com.base.Models.Media;

/**
 * Created by Devam on 21-Nov-17.
 */

public class Helper {

    public static String resolveUrl(Media media,String type)
    {
        if(media == null)
        {
            return null;
        }
        return media.getUrl(type);
    }
}
