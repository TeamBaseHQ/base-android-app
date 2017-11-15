package com.example.base.base.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.example.base.base.R;
import com.example.base.base.async.channel.CreateChannelAsync;
import com.example.base.base.tabs.TabFragment;


public class CreateChannelFragment extends Fragment {

    private CheckBox public_cb,private_cb;
    private TextView cancel,color_pick;
    private Button createChannel;
    private EditText channelName;
    private String selected_color = "00BBD5";
    SharedPreferences sharedPreferences;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_create_channel, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Create Channel");

        public_cb = view.findViewById(R.id.cbCcPublic);
        private_cb = view.findViewById(R.id.cbCcPrivate);
        cancel = view.findViewById(R.id.tvCcCancel);
        color_pick = view.findViewById(R.id.tvCcColor);
        createChannel = view.findViewById(R.id.btnCcCreate);
        channelName = view.findViewById(R.id.etCcChannelName);

        //default color pick
        color_pick.setBackgroundResource(R.drawable.rounded_corner);
        GradientDrawable drawable = (GradientDrawable) color_pick.getBackground();
        drawable.setColor(getResources().getColor(R.color.cyan));

        //select color
        color_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int []color_int = {
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.pink),
                        getResources().getColor(R.color.purple),
                        getResources().getColor(R.color.deep_purple),
                        getResources().getColor(R.color.indigo),
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.light_blue),
                        getResources().getColor(R.color.cyan),
                        getResources().getColor(R.color.teal),
                        getResources().getColor(R.color.green),
                        getResources().getColor(R.color.light_green),
                        getResources().getColor(R.color.lime),
                        getResources().getColor(R.color.yellow),
                        getResources().getColor(R.color.amber),
                        getResources().getColor(R.color.orange),
                        getResources().getColor(R.color.deep_orange),
                        getResources().getColor(R.color.brown),
                        getResources().getColor(R.color.grey),
                        getResources().getColor(R.color.blue_grey),
                        getResources().getColor(R.color.black)};

                ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
                colorPickerDialog.initialize(R.string.title, color_int, getResources().getColor(R.color.cyan), 4, 20);
                colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {

                        color_pick.setBackgroundResource(R.drawable.rounded_corner);
                        GradientDrawable drawable = (GradientDrawable) color_pick.getBackground();
                        drawable.setColor(color);

                        //Code to convert int into hash
                        selected_color = String.format("%06X", 0xFFFFFF & color);

                    }
                });
                colorPickerDialog.show(getActivity().getFragmentManager(), "Select your Color :- ");
            }
        });

        createChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String channelName_value = channelName.getText().toString();
                Boolean is_private;
                if(public_cb.isChecked())
                {
                    is_private = false;
                }
                else
                {
                    is_private = true;
                }

                if(channelName_value.length()!=0&& (public_cb.isChecked() || private_cb.isChecked())) {
                    sharedPreferences = getActivity().getSharedPreferences("BASE", Context.MODE_PRIVATE);
                    if (sharedPreferences.contains("teamSlug")) {
                        new CreateChannelAsync(channelName_value,"",selected_color,is_private,getActivity()).execute();
                    } else {
                        Toast.makeText(getActivity(), "Select Team", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(channelName_value.length()==0)
                    {
                        channelName.setError("Field is mandatory");
                    }
                    if(!(public_cb.isChecked() || private_cb.isChecked()))
                    {
                        Toast.makeText(getActivity(), "Select Private or Public", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = TabFragment.newInstance(0);
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FlContentNavigation, fragment,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }

            }
        });
        public_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(private_cb.isChecked())
                {
                    private_cb.setChecked(false);
                }
            }
        });

        private_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(public_cb.isChecked())
                {
                    public_cb.setChecked(false);
                }
            }
        });

    }
}
