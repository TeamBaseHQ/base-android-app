package com.example.base.base.chat;

import android.util.Log;

import com.base.Models.Message;
import com.base.Models.User;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Devam on 18-Nov-17.
 */

public class ChatMessage implements IMessage {

    private final Message message;

    public ChatMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getId() {
        return String.valueOf(this.message.getId());
    }

    @Override
    public String getText() {
        return this.message.getContent();
    }

    @Override
    public ChatUser getUser() {

        //Static
        User user = new User();
        user.setId(1);
        user.setName("Devam");
        return new ChatUser(user);
    }

    @Override
    public Date getCreatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("y-M-d H:m:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Date date = this.message.getCreated_at();
        try {
            Date newDate = sdf.parse(sdf.format(date));
            Log.d("Date: ", newDate.toString());
            return newDate;
        } catch (ParseException e) {
            Log.d("AAAAAA", e.getMessage());
        }

        return date;
    }
}
