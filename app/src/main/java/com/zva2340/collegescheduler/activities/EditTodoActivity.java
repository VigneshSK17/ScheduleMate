package com.zva2340.collegescheduler.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.databinding.ActivityEditTodoBinding;
import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.models.TodoItem;
import com.zva2340.collegescheduler.utils.FragmentHelpers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class EditTodoActivity extends AppCompatActivity {

    private ActivityEditTodoBinding binding;
    private TodoItem todo;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMMM d yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    private final FragmentHelpers<TodoItem> fragmentHelpers = new FragmentHelpers<>();
    private Gson gson;
    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        binding = ActivityEditTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gson = fragmentHelpers.gsonSetup();

        setSupportActionBar(binding.toolbar);
        createMenu();

        setSpinners();
        Intent intent = getIntent();
        if (intent.hasExtra("TODO")) {
            fillFields(intent.getExtras());
        }


        setUpDateTimePickers();
    }

    private void createMenu() {

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.edit_todo_toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (todo == null) {
                    todo = new TodoItem();
                }
                todo.setTitle(binding.editTextTitle.getText().toString());
                todo.setDueDate(LocalTime.parse(binding.textViewTimeDue.getText().toString(), timeFormatter).atDate(LocalDate.parse(binding.textViewDateDue.getText().toString(), dateFormatter)));
                todo.setAssignment(binding.spinnerTodoType.getSelectedItemPosition() == 0);
                todo.setCourse(courses.get(binding.spinnerTodoCourse.getSelectedItemPosition()));

                Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                resultIntent.putExtra("TODO", todo);
                resultIntent.putExtra("POSITION", getIntent().getIntExtra("POSITION", -1));
                setResult(Activity.RESULT_OK, resultIntent);

                finish();

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
        binding.spinnerTodoCourse.setSelection(courses.indexOf(todo.getCourse()));

    }

    private void setSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.edit_todo_types)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTodoType.setAdapter(adapter);

        SharedPreferences pref = getSharedPreferences("ScheduleMatePref", 0);
        Set<String> coursesJson = pref.getStringSet("courses", null);
        courses = coursesJson.stream().map(json -> gson.fromJson(json, Course.class)).collect(Collectors.toList());
        List<String> courseNames = courses.stream().map(Course::getName).collect(Collectors.toList());
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                courseNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTodoCourse.setAdapter(courseAdapter);

    }

    private void setUpDateTimePickers() {

        long selection = MaterialDatePicker.todayInUtcMilliseconds();
        if (todo != null && todo.getDueDate() != null) {
            selection = todo.getDueDate().toInstant(ZoneOffset.UTC).toEpochMilli();
        }
        long finalSelection = selection;

        // TODO: Fix date issue
        binding.buttonDateDue.setOnClickListener((view) -> {
            MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Due Date")
                    .setSelection(finalSelection)
                    .build();

            picker.show(getSupportFragmentManager(), "DATE_PICKER");

            picker.addOnPositiveButtonClickListener((selected) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM d yyyy", Locale.US);
                binding.textViewDateDue.setText(sdf.format(selected));
            });
        });

        int oldHour = LocalTime.now().getHour();
        if (todo != null && todo.getDueDate() != null) {
            oldHour = todo.getDueDate().getHour();
        }
        int finalOldHour = oldHour;

        int oldMinute = LocalTime.now().getMinute();
        if (todo != null && todo.getDueDate() != null) {
            oldMinute = todo.getDueDate().getMinute();
        }
        int finalOldMinute = oldMinute;

        binding.buttonTimeDue.setOnClickListener((view) -> {
            MaterialTimePicker picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(finalOldHour)
                    .setMinute(finalOldMinute)
                    .setTitleText("Select Time Due")
                    .build();

            picker.show(getSupportFragmentManager(), "DATE_PICKER");

            picker.addOnPositiveButtonClickListener((selected) -> {
                int hour = picker.getHour();
                int minute = picker.getMinute();

                binding.textViewTimeDue.setText(LocalTime.of(hour, minute).format(timeFormatter));
            });

        });
    }
}