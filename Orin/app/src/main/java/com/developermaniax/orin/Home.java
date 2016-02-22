package com.developermaniax.orin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.developermaniax.orin.adapters.NavigationListAdapter;
import com.developermaniax.orin.model.DrawerHeaderListModel;
import com.developermaniax.orin.model.DrawerListItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<DrawerHeaderListModel> mArrayHeader;
    HashMap<Integer,List<DrawerListItemModel>> mArrayChild;

    ExpandableListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        mDrawerList.setGroupIndicator(null);
        mDrawerList.setDividerHeight(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header_home, null);
        mDrawerList.addHeaderView(listHeaderView);
        // set List data to nav drawer
        setListData();

        NavigationListAdapter adapter = new NavigationListAdapter(Home.this, mArrayHeader,mArrayChild);
        mDrawerList.setAdapter(adapter);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setListData(){
        mArrayHeader = new ArrayList<>();
        mArrayHeader.add(new DrawerHeaderListModel("Home",R.drawable.headphone));
        mArrayHeader.add(new DrawerHeaderListModel("My PlayList",R.drawable.my_playlist));
        mArrayHeader.add(new DrawerHeaderListModel("My Downloads",R.drawable.my_download));

        mArrayHeader.add(new DrawerHeaderListModel("Recent",0));
        mArrayHeader.add(new DrawerHeaderListModel("Share With Friends",0));

        mArrayHeader.add(new DrawerHeaderListModel("Upload to Orin",R.drawable.upload));
        mArrayHeader.add(new DrawerHeaderListModel("Settings",R.drawable.setting));
        mArrayHeader.add(new DrawerHeaderListModel("About Orin",R.mipmap.ic_launcher));

        mArrayChild  = new HashMap<>();

        List<DrawerListItemModel> mModel = new ArrayList<>();
        mModel.add(new DrawerListItemModel("","Recent Song 1"));
        mModel.add(new DrawerListItemModel("","Recent Song 2"));
        mModel.add(new DrawerListItemModel("","Recent Song 3"));

        List<DrawerListItemModel> modelsShare = new ArrayList<>();
        modelsShare.add(new DrawerListItemModel("",""));
        mArrayChild.put(3, mModel);
        mArrayChild.put(4,modelsShare);

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
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionViewLayout) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doLogin() {
        final Dialog dialog = new Dialog(Home.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.content_login);
        // Set dialog title

        // set values for custom dialog components - text, image and button

        dialog.show();
    }


    private void switchWindow(int position){

        switch (position){

            case 0:

                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:

                break;
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = new MainTab();

        } else if (id == R.id.nav_gallery) {
            Log.e("DClicks", "Downlaods clicked");
            fragment = new Downloads();
        } else if (id == R.id.nav_slideshow) {
            Log.e("DClicks", "History clicked");
            doLogin();
            //fragment = new History();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Log.e("DClicks", "Share clicked");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        } else if (id == R.id.nav_send) {

        }
        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
