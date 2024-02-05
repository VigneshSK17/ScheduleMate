package com.zva2340.collegescheduler.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * Method to help share code between fragments
 * @author Vignesh Suresh Kumar
 * @param <M> model type
 */
public class FragmentHelpers<M> {

    /**
     * Empty constructor to simply initialize FragmentHelpers
     */
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

    /**
     * Set up the gson with type adapters for LocalDateTime and LocalTime to allow them to be stored effectively
     * @return the gson
     */
    public Gson gsonSetup() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, MMMM d yyyy hh:mm a");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> {
            String dateStr = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(dateStr, dateTimeFormatter);
        }).registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (date, typeOfSrc, context) -> {
                    return new JsonPrimitive(date.format(dateTimeFormatter));
                }).registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (time, typeOfSrc, context) -> {
                    return new JsonPrimitive(time.format(timeFormatter));
        }).registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> {
            String timeStr = json.getAsJsonPrimitive().getAsString();
            return LocalTime.parse(timeStr, timeFormatter);
        }).create();

    }

}
