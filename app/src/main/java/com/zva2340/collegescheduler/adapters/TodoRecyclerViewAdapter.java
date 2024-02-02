package com.zva2340.collegescheduler.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.models.TodoItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.TodoViewHolder> {

    private Context context;
    private List<TodoItem> todoItems;

    public TodoRecyclerViewAdapter(Context context, List<TodoItem> todoItems) {
        this.context = context;
        this.todoItems = todoItems;
    }


    @NonNull
    @Override
    public TodoRecyclerViewAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_todo_card, parent, false);

        return new TodoViewHolder(view);
    }

    // TODO: Do checkbox later
    @Override
    public void onBindViewHolder(@NonNull TodoRecyclerViewAdapter.TodoViewHolder holder, int position) {
        TodoItem todo = todoItems.get(position);

        holder.todoTitle.setText(todo.getTitle());
        holder.todoDueDate.setText(genTodoDescStr(todo));

        holder.checkboxCompletion.setOnClickListener(l -> completeTask(holder, position));

        setupCompleted(holder, todo);

        Log.d("ON_BIND_VIEW_HOLDER", holder.todoTitle.getText().toString());
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }


    // TODO: Figure out how to display this
    /**
     * A helper method to generate the string containing todo timings
     * @param  todo the todo item to be used
     * @return string containing todo item due date
     */
    private String genTodoDescStr(TodoItem todo) {
        LocalDateTime dueDate = todo.getDueDate();
        String dueDateStr = dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        String todoDesc = String.format("%s | %s", todo.getCourse().getName(), dueDateStr);
        Log.d("COURSE_INFO", todoDesc);
        return todoDesc;
    }

    /**
     * A helper method to update once a task is checked complete
     * @param holder the view holder
     * @param position the position of the item in the list
     */
    private void completeTask(@NonNull TodoRecyclerViewAdapter.TodoViewHolder holder, int position) {
        TodoItem todo = todoItems.get(position);
        todo.setCompleted(!todo.isCompleted());
        todoItems.set(position, todo);
        notifyItemChanged(position, todo);
    }


    /**
     * A helper method to update the view if a task is checked complete
     * @param holder the view holder
     * @param todo the todo item
     */
    private void setupCompleted(@NonNull TodoRecyclerViewAdapter.TodoViewHolder holder, TodoItem todo) {
        holder.checkboxCompletion.setChecked(todo.isCompleted());
        holder.todoTitle.setPaintFlags(todo.isCompleted() ?
                holder.todoTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG :
                holder.todoTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG
        );
        holder.todoDueDate.setPaintFlags(todo.isCompleted() ?
                holder.todoTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG :
                holder.todoTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG
        );
    }


    public static class TodoViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView todoTitle, todoDueDate;
        private CheckBox checkboxCompletion;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_todo);
            todoTitle = itemView.findViewById(R.id.textview_todotitle);
            todoDueDate = itemView.findViewById(R.id.textview_tododuedate);
            checkboxCompletion = itemView.findViewById(R.id.checkbox_completion);

        }
    }

}
