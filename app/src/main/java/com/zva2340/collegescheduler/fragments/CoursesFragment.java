package com.zva2340.collegescheduler.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.zva2340.collegescheduler.adapters.CourseRecyclerViewAdapter;
import com.zva2340.collegescheduler.databinding.FragmentCoursesBinding;
import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.utils.FragmentHelpers;
import com.zva2340.collegescheduler.utils.StartEndTime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CoursesFragment extends Fragment {

    private FragmentCoursesBinding binding;

    List<Course> courseModels;
    SharedPreferences pref;
    Gson gson = new Gson();
    FragmentHelpers<Course> fragmentHelpers = new FragmentHelpers<>();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCoursesBinding.inflate(inflater, container, false);

        pref = fragmentHelpers.setPreferences(getActivity());
        setRecyclerView();



        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Pulls the courses from shared preferences for local device persistance
     */
    private void setUpCourses() {
        Set<String> coursesJson = fragmentHelpers.getModelsFromPref(pref, "courses");
        courseModels = getCoursesFromGson(coursesJson);
    }


    /**
     * Converts a set of courses stored as JSON int a list of Courses
     *
     * @param coursesJson set of courses in JSON format
     * @return list of courses
     */
    private List<Course> getCoursesFromGson(Set<String> coursesJson) {
        // List<Course> courses = coursesJson.stream().map(json -> gson.fromJson(json, Course.class)).collect(Collectors.toList());
        List<Course> courses = new ArrayList<>();

        List<StartEndTime> courseTimes = new ArrayList<>();
        courseTimes.add(new StartEndTime(LocalTime.now(), LocalTime.now().plusHours(2), DayOfWeek.MONDAY));
        courseTimes.add(new StartEndTime(LocalTime.now(), LocalTime.now().plusHours(2), DayOfWeek.WEDNESDAY));
        courses.add(new Course(
                "Objects and Design",
                courseTimes,
                "Pedro"
        ));

        return courses;

        // return coursesJson.stream().map(json -> gson.fromJson(json, Course.class)).collect(Collectors.toList());
    }

    /**
     * Sets up the recycler view for the courses
     */
    private void setRecyclerView() {
        RecyclerView recyclerView = binding.recyclerviewClasses;
        setUpCourses();

        fragmentHelpers.setUpRecyclerView(recyclerView, getContext(), new CourseRecyclerViewAdapter(getContext(), courseModels));
    }

}