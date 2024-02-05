package com.zva2340.collegescheduler.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zva2340.collegescheduler.adapters.TodoRecyclerViewAdapter;
import com.zva2340.collegescheduler.databinding.FragmentTodosBinding;
import com.zva2340.collegescheduler.models.TodoItem;
import com.zva2340.collegescheduler.utils.FragmentHelpers;
import com.zva2340.collegescheduler.utils.TodoItemSorts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple fragment holding the RecyclerView of assignments
 */
public class TodosFragment extends Fragment {


    private FragmentTodosBinding binding;

    List<TodoItem> todoModels;
    SharedPreferences pref;
    Gson gson;
    public TodoRecyclerViewAdapter adapter;
    FragmentHelpers<TodoItem> helpers = new FragmentHelpers<>();
    TodoItemSorts todoItemSorts = new TodoItemSorts();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTodosBinding.inflate(inflater, container, false);

        pref = helpers.setPreferences(getActivity());

        gson = helpers.gsonSetup();

        setRecyclerView();

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        saveTodos();
        super.onPause();
    }

    @Override
    public void onStop() {
        saveTodos();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveTodos();
        binding = null;
    }

    /**
     * Sets up the recycler view for the todos
      */
    private void setRecyclerView() {
        RecyclerView recyclerView = binding.recyclerviewTodos;
        setUpTodos();

        ActivityResultLauncher<Intent> arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    TodoItem todo = (TodoItem) data.getSerializableExtra("TODO");
                    int position = data.getIntExtra("POSITION", -1);
                    adapter.update(todo, position);
                }
            }
        });


        adapter = new TodoRecyclerViewAdapter(
                getContext(),
                todoModels,
                binding.todosSortSpinner,
                arl
        );
        helpers.setUpRecyclerView(recyclerView, getContext(), adapter);
    }

    /**
     * Pulls the courses from shared preferences for local device persistance
     */
    private void setUpTodos() {
        Set<String> todosJson = helpers.getModelsFromPref(pref, "todos");
        for (String s : todosJson) {
            Log.d("TodosFragmentSave", s);
        }
        todoModels = getTodosFromGson(todosJson);
    }


    /**
     * Converts a set of todos stored as JSON int a list of Todos
     *
     * @param todosJson set of courses in JSON format
     * @return list of courses
     */
    private List<TodoItem> getTodosFromGson(Set<String> todosJson) {
        List<TodoItem> todos = new ArrayList<>();
        if (!todosJson.isEmpty()) {
            todos = todosJson.stream().map(json -> gson.fromJson(json, TodoItem.class)).collect(Collectors.toList());
        }

        todoItemSorts.sortByCompletion(todos);
        return todos;
    }

    /**
     * Saves the todos to shared preferences
     */
    private void saveTodos() {
        pref.edit().putStringSet("todos", todoModels.stream().map(todoItem -> gson.toJson(todoItem)).collect(Collectors.toSet())).apply();
    }
}