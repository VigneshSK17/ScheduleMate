package com.zva2340.collegescheduler.utils;

import com.zva2340.collegescheduler.models.Course;

import java.time.LocalDateTime;

/**
 * Represents a todo item for a course, foundation of assignment and exam
 * @author Vignesh Suresh Kumar
 */
public class TodoItem {

    /** Is the todo item completed */
    private boolean isCompleted;
    /** The due date of the todo item */
    private LocalDateTime dueDate;
    /** The course this TodoItem is associated to */
    private Course course;

    /**
     * Create a TodoItem with all properties
     * @param isCompleted is the todo item completed
     * @param dueDate due date of the todo item
     * @param course course this TodoItem is associated to
     */
    public TodoItem(boolean isCompleted, LocalDateTime dueDate, Course course) {
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.course = course;
    }


    /**
     * Compare TodoItems by completion
     * @param todoItem TodoItem to compare to
     * @return 0 if equal, 1 if this is greater, -1 if this is less
     */
    public int compareByCompletion(TodoItem todoItem) {
        return Boolean.compare(isCompleted, todoItem.isCompleted);
    }

    /**
     * Compare TodoItems by due date
     * @param todoItem TodoItem to compare to
     * @return 0 if equal, 1 if this is greater, -1 if this is less
     */
    public int compareByDueDate(TodoItem todoItem) {
        return dueDate.compareTo(todoItem.dueDate);
    }

    /**
     * Compare TodoItems by course
     * @param todoItem TodoItem to compare to
     * @return 0 if equal, 1 if this is greater, -1 if this is less
     */
    public int compareByCourse(TodoItem todoItem) {
        return course.compareTo(todoItem.course);
    }

}
