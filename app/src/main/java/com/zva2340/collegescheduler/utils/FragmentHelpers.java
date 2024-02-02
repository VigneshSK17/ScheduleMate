package com.zva2340.collegescheduler.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Set;

/**
 * Method to help share code between fragments
 * @author Vignesh Suresh Kumar
 * @param <M> model type
 */
public class FragmentHelpers<M> {

    public FragmentHelpers() {}

    /**
     * Set up the recycler view
     * @param activity the fragment activity
     */
    public SharedPreferences setPreferences(FragmentActivity activity) {
        return activity.getApplicationContext().getSharedPreferences("ScheduleMatePref", 0);
    }

    /**
     * Get the models from the preferences
     * @param pref the preferences
     * @param key the key
     * @return the set of models
     */
    public Set<String> getModelsFromPref(SharedPreferences pref, String key) {
        return pref.getStringSet(key, null);
    }

    /**
     * Set up the recycler view
     * @param recyclerView the recycler view
     * @param context the context of recycler view
     * @param adapter the adapter of recycler view
     */
    public void setUpRecyclerView(RecyclerView recyclerView, Context context, RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }


}
