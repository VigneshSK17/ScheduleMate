package com.zva2340.collegescheduler.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.adapters.CourseRecyclerViewAdapter;
import com.zva2340.collegescheduler.adapters.TodoRecyclerViewAdapter;
import com.zva2340.collegescheduler.databinding.FragmentTodosBinding;
import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.models.TodoItem;
import com.zva2340.collegescheduler.utils.StartEndTime;
import com.zva2340.collegescheduler.utils.TodoItemSorts;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple fragment holding the RecyclerView of assignments
 * @author Vignesh Suresh Kumar
 */
public class TodosFragment extends Fragment {


    private FragmentTodosBinding binding;

    List<TodoItem> todoModels;
    SharedPreferences pref;
    Gson gson = new Gson();
    TodoItemSorts todoItemSorts = new TodoItemSorts();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTodosBinding.inflate(inflater, container, false);

        pref = getActivity().getApplicationContext().getSharedPreferences("ScheduleMatePref", 0);
        setRecyclerView();

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = binding.todosSortSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.todos_sort_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    // TODO: Save todos to shared preferences
    @Override
    public void onPause() {
        super.onPause();
    }

    // TODO: Save todos to shared preferences
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    /**
     * Sets up the recycler view for the todos
      */
    private void setRecyclerView() {
        RecyclerView recyclerView = binding.recyclerviewTodos;
        setUpTodos();

        TodoRecyclerViewAdapter adapter = new TodoRecyclerViewAdapter(getContext(), todoModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    /**
     * Pulls the courses from shared preferences for local device persistance
     */
    private void setUpTodos() {
        Set<String> coursesJson = pref.getStringSet("courses", null);
        todoModels = getCoursesFromGson(coursesJson);
    }


    /**
     * Converts a set of todos stored as JSON int a list of Todos
     *
     * @param todosJson set of courses in JSON format
     * @return list of courses
     */
    private List<TodoItem> getCoursesFromGson(Set<String> todosJson) {
        // List<TodoItem> todos = todosJson.stream().map(json -> gson.fromJson(json, TodoItem.class)).collect(Collectors.toList());
        List<TodoItem> todos = new ArrayList<>();

        List<StartEndTime> courseTimes = new ArrayList<>();
        courseTimes.add(new StartEndTime(LocalTime.now(), LocalTime.now().plusHours(2), DayOfWeek.MONDAY));
        courseTimes.add(new StartEndTime(LocalTime.now(), LocalTime.now().plusHours(2), DayOfWeek.WEDNESDAY));
        Course course = new Course(
                "Objects and Design AAAAAAAA",
                courseTimes,
                "Pedro"
        );

        todos.add(new TodoItem(
                "Hello",
                true,
                LocalDateTime.now().plusHours(2),
                course,
                true
        ));

        todos.add(new TodoItem(
                "Hi",
                false,
                LocalDateTime.now().minusHours(2),
                course,
                false
        ));

        todoItemSorts.sortByCompletion(todos);

        return todos;

//        todos.stream().map(json -> gson.fromJson(json, TodoItem.class)).collect(Collectors.toList());
    }
}