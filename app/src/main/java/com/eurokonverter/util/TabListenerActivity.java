package com.eurokonverter.util;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

/**
 *
 */
public abstract class TabListenerActivity
        extends    AppCompatActivity
        implements TabLayout.OnTabSelectedListener {

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // do nothing
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        // do nothing
    }
}
