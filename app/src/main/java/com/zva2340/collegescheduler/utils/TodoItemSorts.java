package com.zva2340.collegescheduler.utils;

import com.zva2340.collegescheduler.models.TodoItem;

import java.util.List;

public class TodoItemSorts {

    public TodoItemSorts() {}

    public void sortByCompletion(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByCompletion);
    }

    public void sortByDueDate(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByDueDate);
    }

    public void sortByCourse(List<TodoItem> todos) {
        todos.sort(TodoItem::compareByCourse);
    }


}
