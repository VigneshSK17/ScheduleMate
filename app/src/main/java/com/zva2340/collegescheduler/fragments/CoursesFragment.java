package com.zva2340.collegescheduler.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.zva2340.collegescheduler.adapters.CourseRecyclerViewAdapter;
import com.zva2340.collegescheduler.databinding.FragmentCoursesBinding;
import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.utils.FragmentHelpers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Fragment for the courses tab
 */
public class CoursesFragment extends Fragment {

    private FragmentCoursesBinding binding;

    List<Course> courseModels;
    SharedPreferences pref;
    Gson gson;
    FragmentHelpers<Course> fragmentHelpers = new FragmentHelpers<>();
    public CourseRecyclerViewAdapter adapter;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCoursesBinding.inflate(inflater, container, false);

        pref = fragmentHelpers.setPreferences(getActivity());

        gson = fragmentHelpers.gsonSetup();

        setRecyclerView();



        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPause() {
        saveCourses();
        super.onPause();
    }

    @Override
    public void onDetach() {
        saveCourses();
        super.onDetach();
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
         List<Course> courses = coursesJson.stream().map(json -> gson.fromJson(json, Course.class)).collect(Collectors.toList());
        return courses;
    }

    /**
     * Sets up the recycler view for the courses
     */
    private void setRecyclerView() {
        RecyclerView recyclerView = binding.recyclerviewClasses;
        setUpCourses();

        Log.d("CourseRecycler", Integer.toString(courseModels.size()));

        ActivityResultLauncher<Intent> arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Course course = (Course) data.getSerializableExtra("COURSE");
                    int position = data.getIntExtra("POSITION", -1);
                    adapter.update(course, position);
                }
            }
        });

        adapter = new CourseRecyclerViewAdapter(getContext(), courseModels, arl);
        fragmentHelpers.setUpRecyclerView(recyclerView, getContext(), adapter);
    }

    /**
     * Saves the courses to shared preferences
     */
    private void saveCourses() {
        pref.edit().putStringSet("courses", courseModels.stream().map(course -> gson.toJson(course)).collect(Collectors.toSet())).commit();
    }

}