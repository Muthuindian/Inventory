package com.tech42.mari.inventorymanagement;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SlidingDrawer;

import com.tech42.mari.inventorymanagement.Management.Activity_Inventory;
import com.tech42.mari.inventorymanagement.Management.Activity_Issue;
import com.tech42.mari.inventorymanagement.Management.Activity_Opname;
import com.tech42.mari.inventorymanagement.Management.Activity_Receipt;
import com.tech42.mari.inventorymanagement.Reports.Activity_Movement;
import com.tech42.mari.inventorymanagement.Reports.Activity_Summary;
import com.tech42.mari.inventorymanagement.Settings.Activity_Settings;

public class MainActivity extends ActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    android.support.v4.app.Fragment fragment=null;
    android.support.v4.app.FragmentManager manager;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_export) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inventory) {
            fragment = new Activity_Inventory();
            fab.setVisibility(View.GONE);

        } else if (id == R.id.nav_receipt) {
            fragment = new Activity_Receipt();
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_issue) {
            fragment = new Activity_Issue();
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_opname) {
            fragment = new Activity_Opname();
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_summary) {
            fragment = new Activity_Summary();
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_movement) {
            fragment = new Activity_Movement();
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_settings) {
            fragment = new Activity_Settings();
            fab.setVisibility(View.GONE);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame , fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
