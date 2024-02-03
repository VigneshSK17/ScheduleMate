package com.zva2340.collegescheduler.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.databinding.ActivityEditTodoBinding;
import com.zva2340.collegescheduler.models.TodoItem;

import java.time.format.DateTimeFormatter;

public class EditTodoActivity extends AppCompatActivity {

    private ActivityEditTodoBinding binding;
    private TodoItem todo;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMMM d");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        binding = ActivityEditTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        createMenu();

        setSpinners();
        Intent intent = getIntent();
        if (intent.hasExtra("TODO")) {
            fillFields(intent.getExtras());
        }
    }

    private void createMenu() {

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.edit_todo_toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // TODO: Add save functionality here
                return true;
            }
        });

        setSupportActionBar(binding.toolbar);

        binding.toolbar.setNavigationOnClickListener((view) -> finish());
    }

    private void fillFields(Bundle extras) {
        todo = (TodoItem) extras.get("TODO");

        binding.editTextTitle.setText(todo.getTitle());
        binding.textViewDateDue.setText(todo.getDueDate().format(dateFormatter));
        binding.textViewTimeDue.setText(todo.getDueDate().format(timeFormatter));

        binding.spinnerTodoType.setSelection(todo.isAssignment() ? 0 : 1);
    }

    private void setSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.edit_todo_types)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTodoType.setAdapter(adapter);
    }
}