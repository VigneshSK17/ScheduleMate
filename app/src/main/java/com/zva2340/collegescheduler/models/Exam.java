package com.zva2340.collegescheduler.models;

import com.zva2340.collegescheduler.utils.TodoItem;

import java.time.LocalDateTime;

/**
 * Represents an exam for a course
 * @author Vignesh Suresh Kumar
 */
public class Exam extends TodoItem {

    /** Name of exam */
    public String name;

    /**
     * Create an exam with all properties
     * @param name name of exam
     * @param isCompleted is the exam completed
     * @param dueDate due date of exam
     * @param course course this exam is associated to
     */
    public Exam(String name, boolean isCompleted, LocalDateTime dueDate, Course course) {
        super(isCompleted, dueDate, course);
        this.name = name;
    }

}
