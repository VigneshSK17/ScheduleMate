package com.zva2340.collegescheduler.utils;

import com.zva2340.collegescheduler.models.TodoItem;

import java.util.List;

/**
 * Class to help sort todo items by different criteria
 */
public class TodoItemSorts {

    public TodoItemSorts() {}

    /**
     * Sort the list of todo items by the index
     * @param i the index to sort by
     * @param todos the list of todo items
     */
    public void sortByIndex(int i, List<TodoItem> todos) {
        if (i == 0) {
            sortByCompletion(todos);
        } else if (i == 1) {
            sortByDueDate(todos);
        } else if (i == 2) {
            sortByCourse(todos);
        } else if (i == 3) {
            sortByAssignments(todos);
        } else if (i == 4) {
            sortByExams(todos);
        }
    }

    /**
     * Sort the list of todo items by completion
     * @param todos the list of todo items
     */
    public void sortByCompletion(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByCompletion);
    }

    /**
     * Sort the list of todo items by due date
     * @param todos the list of todo items
     */
    public void sortByDueDate(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByDueDate);
    }

    /**
     * Sort the list of todo items by course
     * @param todos the list of todo items
     */
    public void sortByCourse(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByCourse);
    }

    /**
     * Sort the list of todo items by assignments
     * @param todos the list of todo items
     */
    public void sortByAssignments(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByAssignments);
    }

    /**
     * Sort the list of todo items by exams
     * @param todos the list of todo items
     */
    public void sortByExams(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByExams);
    }

}
