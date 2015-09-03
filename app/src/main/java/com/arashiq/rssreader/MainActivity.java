package com.arashiq.rssreader;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arashiq.rssreader.adapter.TabFragmentAdapter;
import com.arashiq.rssreader.util.RssChannel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabLayout();
    }

    private void initTabLayout() {

        List<String> tabList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(RssChannel rc : RssChannel.getAll()){
            tabList.add(rc.getTitle());
            Fragment f = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("channelUrl", rc.getUrl());
            f.setArguments(bundle);
            fragmentList.add(f);
        }

        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, tabList);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        return true;
    }
}
