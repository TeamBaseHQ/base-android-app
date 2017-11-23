package com.example.base.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.Models.Channel;
import com.example.base.base.actions.AddMessageToList;
import com.example.base.base.async.channel.ListChannelAsync;
import com.example.base.base.async.user.GetUserAsync;
import com.example.base.base.channel.CreateChannelFragment;
import com.example.base.base.service.BackgroundMessageService;
import com.example.base.base.tabs.TabFragment;
import com.example.base.base.tabs.ThreadTabFragment;
import com.example.base.base.team.TeamListActivity;
import com.example.base.base.thread.AllThreadsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    Menu menu;
    HashMap<String,String> channelName_list = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(NavigationBarActivity.this, BackgroundMessageService.class));
        registerReceiver(new AddMessageToList(), new IntentFilter(BackgroundMessageService.BROADCAST_ACTION));
        setContentView(R.layout.activity_navigation_bar);
        sharedPreferences = getSharedPreferences("BASE", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(sharedPreferences.getString("teamName","Base"));
        setSupportActionBar(toolbar);

        new GetUserAsync(NavigationBarActivity.this).execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //Set team
        try {
            menu = navigationView.getMenu();
            if(sharedPreferences.contains("teamName")) {
                MenuItem team = menu.findItem(R.id.nav_team);
                MenuItem nav_create_channel = menu.findItem(R.id.nav_create_channels);
                MenuItem nav_channel = menu.findItem(R.id.nav_channels);
                nav_create_channel.setVisible(true);
                nav_channel.setVisible(true);
                team.setTitle(sharedPreferences.getString("teamName",""));
                //set Channel
                new ListChannelAsync(sharedPreferences.getString("teamSlug",""),getApplicationContext()){
                    @Override
                    protected void onPostExecute(List<Channel> result) {
                        super.onPostExecute(result);
                        try {
                            if (!result.isEmpty()) {
                                for (Channel channel : result) {
                                    Drawable icon = getResources().getDrawable(R.drawable.ic_stop_black_48dp).mutate();
                                    String colorString = "#".concat(channel.getColor());
                                    icon.setColorFilter(Color.parseColor(colorString), PorterDuff.Mode.SRC_IN);
                                    menu.add(channel.getName()).setIcon(icon);
                                    channelName_list.put(channel.getSlug(), channel.getName());
                                }
                            }
                        }catch(Exception e)
                        {
                            Toast.makeText(NavigationBarActivity.this, "Channel Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                }.execute();
            }
            else{
                MenuItem nav_create_channel = menu.findItem(R.id.nav_create_channels);
                MenuItem nav_channel = menu.findItem(R.id.nav_channels);
                nav_create_channel.setVisible(false);
                nav_channel.setVisible(false);
            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }catch (Exception exception)
        {
            exception.printStackTrace();
        }

        /*Menu menu = navigationView.getMenu();
        MenuItem item = menu.add(R.id.nav_channels, R.id.nav_design, 0 , "General");
        item.setIcon(R.drawable.ic_menu_share); // add icon with drawable resource*/

        displaySelectedScreen(R.id.nav_all_threads);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_all_threads) {
            // Handle the camera action
            fragment = new AllThreadsFragment();

        } else if (id == R.id.nav_create_channels) {

            fragment = TabFragment.newInstance(1);

        }else if(id == R.id.nav_team) {

            Intent i = new Intent(NavigationBarActivity.this,TeamListActivity.class);
            startActivity(i);
        }
        else if(id == R.id.nav_starred)
        {
            stopService(new Intent(NavigationBarActivity.this, BackgroundMessageService.class));
        }
        else
        {
            if(channelName_list.containsValue(item.getTitle())){
                for (HashMap.Entry<String,String> entry: channelName_list.entrySet()) {
                    if(entry.getValue()==item.getTitle()) {
                        fragment = ThreadTabFragment.newInstance(entry.getKey(),entry.getValue());
                    }
                }
                //
            }
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.FlContentNavigation, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_all_threads:
                fragment = TabFragment.newInstance(0);
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.FlContentNavigation, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void setUserName(String userName_value)
    {
        sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);

        TextView userName = (TextView) findViewById(R.id.nav_txtProfileName);
        ImageView userPic = (ImageView) findViewById(R.id.nav_ivProfilePicture);
        userName.setText(userName_value);
        String user_Image = sharedPreferences.getString("User_image","");
        if(user_Image!="") {
            Picasso.with(this)
                    .load(user_Image)
                    .into(userPic);
        }
    }
}
