package com.zva2340.collegescheduler.models;

import com.zva2340.collegescheduler.utils.Weekday;

public class Course {

    // name, time, instructor
    // list of assignments ?
    // list of exams ?

    private String name;
    private Weekday weekday;
    private String instructor;

    public Course(String name, Weekday weekday, String instructor) {
        this.name = name;
        this.weekday = weekday;
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


}
