package com.fhflensburg.todocpp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class TodoActivity extends AppCompatActivity {

    /* declare class variables */
    private final ThreadLocal<SectionsPagerAdapter> mSectionsPagerAdapter = new ThreadLocal<>();

    /* create activity */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* set layout */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        /* set toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* set adapter */
        mSectionsPagerAdapter.set(new SectionsPagerAdapter(getSupportFragmentManager()));

        /* set viewpager and add adapter */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter.get());

        /* create tabs */
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /* options menu shown */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    /* super actions for menu */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
