package com.example.base.base.message_format;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.base.Models.Message;
import com.example.base.base.R;
import com.example.base.base.actions.AddMessageToList;
import com.example.base.base.async.message.ListMessagesAsync;
import com.example.base.base.async.message.SendMessageAsync;
import com.example.base.base.chat.ChatMessage;
import com.example.base.base.chat.ChatUser;
import com.example.base.base.handler.ChannelMessageHandler;
import com.example.base.base.service.BackgroundMessageService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment {

    MessagesList messagesList;
    MessageInput messageInput;
    MessagesListAdapter<ChatMessage> adapter;
    String threadSlug,channelSlug,teamSlug;
    SharedPreferences sharedPreferences;
    private int page=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().registerReceiver(
                        new AddMessageToList(this),
                        new IntentFilter(BackgroundMessageService.BROADCAST_ACTION)
                );
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            if (getActivity().getIntent().hasExtra("MessageTitleName")) {
                Intent i = getActivity().getIntent();
                this.channelSlug = i.getExtras().getString("channelSlug");
                this.threadSlug = i.getExtras().getString("threadSlug");
                String memberName = i.getExtras().getString("MessageTitleName");
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(memberName);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        messagesList = view.findViewById(R.id.messagesList);
        messageInput = view.findViewById(R.id.input);
        adapter = new MessagesListAdapter<>("", null);
        adapter.setLoadMoreListener(new MessagesListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                    new ListMessagesAsync(teamSlug, channelSlug, threadSlug, page + 1, getActivity()) {
                        @Override
                        protected void onPostExecute(List<Message> result) {
                            setList(result);
                        }
                    }.execute();
            }
        });
        messagesList.setAdapter(adapter);

        sharedPreferences = getActivity().getSharedPreferences("BASE",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("teamSlug")) {
            this.teamSlug = sharedPreferences.getString("teamSlug","");
            messageInput.setInputListener(new MessageInput.InputListener() {
                @Override
                public boolean onSubmit(CharSequence input) {
                    //validate and send message
                    if (input.toString().trim().length() != 0) {
                        Message message = new Message();
                        message.setContent(input.toString().trim());
                        message.setCreated_at("00:00");
                        setMessage(message);
                        /*ChatMessage chatMessage = new ChatMessage(message);
                        adapter.addToStart(chatMessage, true);*/

                        new SendMessageAsync(teamSlug, channelSlug, threadSlug, input.toString().trim(), "text", getActivity()) {
                            @Override
                            protected void onPostExecute(String result) {
                                if (result.contains("Error")) {
                                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute();
                        return true;
                    }else
                    {
                        return false;
                    }
                }
            });


            new ListMessagesAsync(this.teamSlug,this.channelSlug,this.threadSlug,this.page,getActivity()){
                @Override
                protected void onPostExecute(List<Message> result) {
                    setList(result);
                }
            }.execute();
        }

        //pusher code
        /*PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("0629736944f65f07a707", options);

        com.pusher.client.channel.Channel channel = pusher.subscribe("my-channel");
        channel.bind("my-event", new ChannelMessageHandler(getActivity()){
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String input_value = jsonObject.getString("message");
                            Message message = new Message();
                            message.setContent(input_value);
                            message.setCreated_at("00:00");
                            setMessage(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        pusher.connect();*/
    }

    public void setList(List<Message> messages){

        List<ChatMessage> chatMessages = new ArrayList<>();

        try {
            for (Message message : messages) {
                ChatMessage chatMessage = new ChatMessage(message);
                chatMessages.add(chatMessage);
            }

            adapter.addToEnd(chatMessages, false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMessage(Message message)
    {
        ChatMessage chatMessage = new ChatMessage(message);
        adapter.addToStart(chatMessage, true);
    }

    public void handleIncomingMessage(Message message) {
        // Message belongs to current thread. Add to list
        if (message.getThread().getSlug().equals(this.threadSlug)) {
            setMessage(message);
            return;
        } //samjha? yup gajab gajab. abb dekh, aisayooooooooooooooooe listeners(actions) create karke
        // fragments/activities mai use kar. Ek, ChannelList frag/act. mai ayega.
        // Which will highligh the channel jispe message aaya hai. by checking the
        // slug. samjha? ha smajyo. let's check.
    }

}