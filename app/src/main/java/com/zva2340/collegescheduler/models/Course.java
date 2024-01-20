package com.zva2340.collegescheduler.models;

import com.zva2340.collegescheduler.utils.Weekday;

import java.util.List;

public class Course {

    // name, time, instructor
    // list of assignments ?
    // list of exams ?

    private String name;
    private List<Weekday> weekdays;
    private String instructor;

    public Course(String name, List<Weekday> weekdays, String instructor) {
        this.name = name;
        this.weekdays = weekdays;
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Weekday> getWeekday() {
        return weekdays;
    }

    public void setWeekdays(List<Weekday> weekdays) {
        this.weekdays = weekdays;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


}
