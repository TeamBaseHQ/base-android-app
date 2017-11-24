package com.example.base.base.message_format;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.base.Models.Media;
import com.base.Models.Message;
import com.example.base.base.R;
import com.example.base.base.actions.AddMessageToList;
import com.example.base.base.actions.HandlesAction;
import com.example.base.base.async.message.ListMessagesAsync;
import com.example.base.base.async.message.SendFileAsync;
import com.example.base.base.async.message.SendMessageAsync;
import com.example.base.base.async.message.UploadFileAsync;
import com.example.base.base.chat.ChatMessage;
import com.example.base.base.listener.message.MessageWasReceived;
import com.google.gson.Gson;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment implements HandlesAction {

    private static final int PICKFILE_REQUEST_CODE = 101;
    MessagesList messagesList;
    MessageInput messageInput;
    MessagesListAdapter<ChatMessage> adapter;
    String threadSlug,channelSlug,teamSlug;
    SharedPreferences sharedPreferences;
    ImageView ivFile;
    String FilePath=null;
    public int filePath_int[]=null;
    private int page=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().registerReceiver(
                        new AddMessageToList(this),
                        new IntentFilter(MessageWasReceived.ACTION)
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
        ivFile = view.findViewById(R.id.ivMFile);
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

        ivFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

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
                        if(FilePath == null && filePath_int == null) {
                            new SendMessageAsync(teamSlug, channelSlug, threadSlug, input.toString().trim(), "text", getActivity()) {
                                @Override
                                protected void onPostExecute(String result) {
                                    if (result.contains("Error")) {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }.execute();
                            return true;
                        }
                        new SendFileAsync(teamSlug, channelSlug, threadSlug, input.toString().trim(), "file",filePath_int,getActivity()) {
                            @Override
                            protected void onPostExecute(String result) {
                                if (result.contains("Error")) {
                                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                }
                                filePath_int = null;
                                FilePath=null;
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
    }

    public void setList(List<Message> messages){

        List<ChatMessage> chatMessages = new ArrayList<>();
        Log.d("ImageUrlThumb","in");
        try {
            for (Message message : messages) {
                Log.d("ImageUrlThumb","bijo"+message.toString());
                ChatMessage chatMessage = new ChatMessage(message);
                chatMessages.add(chatMessage);
            }

            adapter.addToEnd(chatMessages, false);
        }catch (Exception e){
            e.printStackTrace();
            Log.d("ImageUrlThumb","errrrrrrrrrrrrrrrrrrrror");
        }
    }

    public void setMessage(Message message)
    {
        ChatMessage chatMessage = new ChatMessage(message);
        adapter.addToStart(chatMessage, true);
    }

    public void openFileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_OK) return;
        String path = "";
        if (requestCode == PICKFILE_REQUEST_CODE) {
            Uri uri = data.getData();
            FilePath = uri.getPath();
            uploadFile(FilePath);
        }
    }

    private void uploadFile(final String filePath) {
        File files[] = new File[1];
        files[0] = new File(filePath);
        new UploadFileAsync(teamSlug,channelSlug,files,getActivity()){

            @Override
            protected void onPostExecute(Media[] result) {
                if(result == null)
                {
                    return;
                }
                filePath_int = new int[result.length];
                int filePath_int_flag=0;
                for(Media media : result)
                {
                    filePath_int[filePath_int_flag++]=media.getId();
                }

            }

        }.execute();
    }

    @Override
    public void handle(String eventName, String channelName, String data) {
        Message message = (new Gson()).fromJson(data, Message.class);

        // Message belongs to current thread. Add to list
        if (message.getThread().getSlug().equals(this.threadSlug)) {
            setMessage(message);
            return;
        }
    }
}