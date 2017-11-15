package com.example.base.base.channel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.colorpicker.ColorPickerDialog;
import com.example.base.base.R;
import com.example.base.base.tabs.TabFragment;


public class CreateChannelFragment extends Fragment {

    private CheckBox public_cb,private_cb;
    private TextView cancel;

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fragment = null;
                fragment = TabFragment.newInstance(0);
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FlContentNavigation, fragment,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }*/
                int []color_int = {R.color.red,R.color.pink,R.color.purple,R.color.deep_purple,R.color.indigo,R.color.blue,R.color.light_blue,R.color.cyan,R.color.teal,R.color.green,R.color.light_green,R.color.lime,R.color.yellow,R.color.amber,R.color.orange,R.color.deep_orange,R.color.brown,R.color.grey,R.color.blue_grey};
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
                colorPickerDialog.initialize(R.string.title, color_int, R.color.cyan, 4, 19);
                colorPickerDialog.show(getActivity().getFragmentManager(), "Select your Color :- ");

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
