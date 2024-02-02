package com.zva2340.collegescheduler.models;

import java.time.LocalDateTime;

/**
 * Represents a todo item for a course, foundation of assignment and exam
 * @author Vignesh Suresh Kumar
 */
/**
 * Represents a todo item.
 */
public class TodoItem {

    /** The title of the todo item */
    private String title;
    /** Is the todo item completed */
    private boolean isCompleted;
    /** The due date of the todo item */
    private LocalDateTime dueDate;
    /** The course this TodoItem is associated to */
    private Course course;
    /** Whether it is an assignment or exam */
    private boolean isAssignment;

    /**
     * Create a TodoItem with all properties
     * @param title title of the todo item
     * @param isCompleted is the todo item completed
     * @param dueDate due date of the todo item
     * @param course course this TodoItem is associated to
     * @param isAssignment whether it is an assignment or exam
     */
    public TodoItem(String title, boolean isCompleted, LocalDateTime dueDate, Course course, boolean isAssignment) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.course = course;
        this.isAssignment = isAssignment;
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

    /**
     * Checks if the todo item is completed.
     * @return true if the todo item is completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Sets the completion status of the todo item.
     * @param completed the completion status to set
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Gets the due date of the todo item.
     * @return the due date of the todo item
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the todo item.
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the course associated with the todo item.
     * @return the course associated with the todo item
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course associated with the todo item.
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Checks if the todo item is an assignment.
     * @return true if the todo item is an assignment, false otherwise
     */
    public boolean isAssignment() {
        return isAssignment;
    }

    /**
     * Sets whether the todo item is an assignment or exam.
     * @param assignment true if the todo item is an assignment, false if it is an exam
     */
    public void setAssignment(boolean assignment) {
        isAssignment = assignment;
    }

    /**
     * Gets the title of the todo item.
     * @return the title of the todo item
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the todo item.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
