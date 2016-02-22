package com.developermaniax.orin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developermaniax.orin.adapters.ViewPagerAdapter;
import com.developermaniax.orin.fragmentTabs.MusicFragment;
import com.developermaniax.orin.fragmentTabs.MusicList;
import com.developermaniax.orin.fragmentTabs.SpeakerFragment;
import com.developermaniax.orin.fragmentTabs.TVShowbizFragment;
import com.developermaniax.orin.fragmentTabs.TshirtFragment;
import com.developermaniax.orin.model.PagerSwitcher;

import java.util.ArrayList;
import java.util.List;

public class MainTab extends Fragment {

    private TabLayout tabLayout;

    private int[] tabIcons = {
            R.drawable.headphone,
            R.drawable.my_playlist
    };
    ViewPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main_tab, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager );
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return view;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new MusicFragment(), "");
        adapter.addFrag(new TVShowbizFragment(), "");
        viewPager.setAdapter(adapter);
    }


}
