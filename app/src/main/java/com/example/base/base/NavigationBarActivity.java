package com.example.base.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.base.base.async.user.GetUserAsync;
import com.example.base.base.channel.CreateChannelFragment;
import com.example.base.base.tabs.TabFragment;
import com.example.base.base.tabs.ThreadTabFragment;
import com.example.base.base.team.TeamListActivity;
import com.example.base.base.thread.AllThreadsFragment;

public class NavigationBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new GetUserAsync(NavigationBarActivity.this).execute();
        /*sharedPreferences = getSharedPreferences("BASE", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("BaseObject")) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("AccessTokenObject", "");
        AccessTokenSerializable accessTokenSerializable = gson.fromJson(json, AccessTokenSerializable.class);*/



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            Intent i = getIntent();
            if (i.getExtras().containsKey("teamName")) {
                Menu menu = navigationView.getMenu();
                MenuItem team = menu.findItem(R.id.nav_team);
                team.setTitle(i.getExtras().getString("teamName"));
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

        } else if (id == R.id.nav_create_channels) {

            fragment = new CreateChannelFragment();

        } else if (id == R.id.nav_design) {

            fragment = ThreadTabFragment.newInstance(3,0,"Design");
        } else if(id == R.id.nav_team) {

            Intent i = new Intent(NavigationBarActivity.this,TeamListActivity.class);
            startActivity(i);
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
        TextView userName = (TextView) findViewById(R.id.nav_txtProfileName);
        userName.setText(userName_value);
    }
}
