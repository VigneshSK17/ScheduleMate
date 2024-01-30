package com.zva2340.collegescheduler.models;

import com.zva2340.collegescheduler.utils.TodoItem;

import java.time.LocalDateTime;

/**
 * Represents an assignment for a course
 * @author Vignesh Suresh Kumar
 */
public class Assignment extends TodoItem {

    /** Name of assignment */
    public String name;

    /**
     * Create an assignment with all properties
     * @param name name of assignment
     * @param isCompleted is the assignment completed
     * @param dueDate due date of assignment
     * @param course course this assignment is associated to
     */
    public Assignment(String name, boolean isCompleted, LocalDateTime dueDate, Course course) {
        super(isCompleted, dueDate, course);
        this.name = name;
    }

}
