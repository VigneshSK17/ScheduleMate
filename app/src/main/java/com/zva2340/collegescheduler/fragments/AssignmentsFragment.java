package com.zva2340.collegescheduler.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zva2340.collegescheduler.databinding.FragmentAssignmentsBinding;

/**
 * A simple fragment holding the RecyclerView of assignments
 * @author Vignesh Suresh Kumar
 */
public class AssignmentsFragment extends Fragment {


    private FragmentAssignmentsBinding binding;

//    List<Course> courseModels;
    SharedPreferences pref;
//    Gson gson = new Gson();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);

        pref = getActivity().getApplicationContext().getSharedPreferences("ScheduleMatePref", 0);

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

}