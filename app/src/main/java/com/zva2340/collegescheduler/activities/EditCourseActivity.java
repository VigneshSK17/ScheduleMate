package com.zva2340.collegescheduler.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.core.view.MenuProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;
import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.databinding.ActivityEditCourseBinding;
import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.models.TodoItem;
import com.zva2340.collegescheduler.utils.FragmentHelpers;
import com.zva2340.collegescheduler.utils.StartEndTime;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class EditCourseActivity extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {

    private ActivityEditCourseBinding binding;
    private Course course;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMMM d yyyy", Locale.US);
    private final FragmentHelpers<TodoItem> fragmentHelpers = new FragmentHelpers<>();
    private Gson gson;
    private boolean isLectureTimes = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gson = fragmentHelpers.gsonSetup();
        Intent intent = getIntent();

        binding.toolbar.setTitle(intent.getExtras().getString("TITLE"));
        setSupportActionBar(binding.toolbar);
        createMenu();

        if (intent.hasExtra("COURSE")) {
            fillFields(intent.getExtras());
        }

        setUpDateTimePickers();
    }

    private void createMenu() {
        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.edit_course_toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (course == null) {
                    course = new Course();
                }

                String[] lectureDays = binding.textViewLectureDays.getText().toString().split(", ");
                String[] lectureTimes = binding.textViewLectureTimes.getText().toString().split(" - ");
                Set<StartEndTime> lectureSET = new HashSet<>();

                for (String day : lectureDays) {
                    lectureSET.add(new StartEndTime(
                            LocalTime.parse(lectureTimes[0], timeFormatter),
                            LocalTime.parse(lectureTimes[1], timeFormatter),
                            DayOfWeek.valueOf(day.toUpperCase())
                    ));
                }
                String[] labDays = binding.textViewLabDays.getText().toString().split(", ");
                String[] labTimes = binding.textViewLabTimes.getText().toString().split(" - ");
                Set<StartEndTime> labSET = new HashSet<>();

                for (String day1 : labDays) {
                    labSET.add(new StartEndTime(
                            LocalTime.parse(labTimes[0], timeFormatter),
                            LocalTime.parse(labTimes[1], timeFormatter),
                            DayOfWeek.valueOf(day1.toUpperCase())
                    ));
                }

                course.setName(binding.editTextName.getText().toString());
                course.setInstructor(binding.editTextInstructor.getText().toString());
                course.setLocation(binding.editTextLocation.getText().toString());
                course.setRoom(binding.editTextRoom.getText().toString());
                course.setLectureTimes(new ArrayList<>(lectureSET));
                course.setLabTimes(new ArrayList<>(labSET));



                Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                resultIntent.putExtra("COURSE", course);
                resultIntent.putExtra("POSITION", getIntent().getIntExtra("POSITION", -1));
                setResult(RESULT_OK, resultIntent);

                finish();

                return true;
            }
        });


        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener((view) -> finish());
    }

    private void fillFields(Bundle extras) {
        course = (Course) extras.get("COURSE");

        binding.editTextName.setText(course.getName());
        binding.editTextInstructor.setText(course.getInstructor());
        binding.editTextLocation.setText(course.getLocation());
        binding.editTextRoom.setText(course.getRoom());
        binding.textViewLectureDays.setText(getDaysStr(course.getLectureTimes()));
        binding.textViewLectureTimes.setText(getTimeRangeStr(course.getLectureTimes()));
        if (!course.getLabTimes().isEmpty()) {
            binding.textViewLabDays.setText(getDaysStr(course.getLabTimes()));
            binding.textViewLabTimes.setText(getTimeRangeStr(course.getLabTimes()));
        }
    }

    private String getDaysStr(List<StartEndTime> times) {
        StringBuilder days = new StringBuilder();
        Set<String> daysSet = new HashSet<>();
        for (int i = 0; i < times.size(); i++) {
            String dayStr = times.get(i).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
            if (!daysSet.contains(dayStr)) {
                days.append(dayStr).append(", ");
                daysSet.add(dayStr);
            }
        }
        if (days.toString().charAt(days.length() - 2) == ',') {
            days.delete(days.length() - 2, days.length());
        }
        return days.toString();
    }

    private String getDaysFromDaysStr(DayOfWeek day1, DayOfWeek day2) {
        return day1.getDisplayName(TextStyle.FULL, Locale.US) + ", " + day2.getDisplayName(TextStyle.FULL, Locale.US);
    }

    private String getTimeRangeStr(List<StartEndTime> times) {
        StartEndTime set = times.get(0);
        return set.getStartTime().format(timeFormatter) + " - " + set.getEndTime().format(timeFormatter);
    }

    private void setUpDateTimePickers() {
        binding.buttonLectureDays.setOnClickListener(v -> {
            MaterialDatePicker picker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select Lecture Days")
                    .build();

            picker.show(getSupportFragmentManager(), "LECTURE_DAYS_PICKER");

            // TODO: Fix date issue
            picker.addOnPositiveButtonClickListener(selected -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM d yyyy", Locale.US);
                DayOfWeek startDay = LocalDate.parse(sdf.format(((Pair<Long, Long>) selected).first), dateFormatter).getDayOfWeek();
                DayOfWeek endDay = LocalDate.parse(sdf.format(((Pair<Long, Long>) selected).second), dateFormatter).getDayOfWeek();
                binding.textViewLectureDays.setText(getDaysFromDaysStr(startDay, endDay));
            });

        });

        binding.buttonLectureTimes.setOnClickListener(v -> {
            isLectureTimes = true;
            RangeTimePickerDialog dialog = new RangeTimePickerDialog();
            dialog.newInstance();
            dialog.setRadiusDialog(20);
            dialog.setIs24HourView(false);
            dialog.show(getFragmentManager(), "LECTURE_TIMES_PICKER");
        });

        binding.buttonLabDays.setOnClickListener(v -> {
            MaterialDatePicker picker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select Lab Days")
                    .build();

            picker.show(getSupportFragmentManager(), "LAB_DAYS_PICKER");

            // TODO: Fix date issue
            picker.addOnPositiveButtonClickListener(selected -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM d yyyy", Locale.US);
                DayOfWeek startDay = LocalDate.parse(sdf.format(((Pair<Long, Long>) selected).first), dateFormatter).getDayOfWeek();
                DayOfWeek endDay = LocalDate.parse(sdf.format(((Pair<Long, Long>) selected).second), dateFormatter).getDayOfWeek();
                binding.textViewLabDays.setText(getDaysFromDaysStr(startDay, endDay));
            });

        });

        binding.buttonLabTimes.setOnClickListener(v -> {
            isLectureTimes = false;
            RangeTimePickerDialog dialog = new RangeTimePickerDialog();
            dialog.newInstance();
            dialog.setRadiusDialog(20);
            dialog.setIs24HourView(false);
            dialog.show(getFragmentManager(), "LAB_TIMES_PICKER");
        });
    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        String startTime = LocalTime.of(hourStart, minuteStart).format(timeFormatter);
        String endTime = LocalTime.of(hourEnd, minuteEnd).format(timeFormatter);
        String times = startTime + " - " + endTime;

        if (isLectureTimes) {
            binding.textViewLectureTimes.setText(times);
        } else {
            binding.textViewLabTimes.setText(times);
        }
    }



}