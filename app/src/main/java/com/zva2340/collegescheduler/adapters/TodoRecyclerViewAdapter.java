package com.zva2340.collegescheduler.adapters;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.activities.EditTodoActivity;
import com.zva2340.collegescheduler.models.TodoItem;
import com.zva2340.collegescheduler.utils.TodoItemSorts;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.TodoViewHolder> {

    private Context context;
    private List<TodoItem> todoItems;
    private Spinner spinner;

    private ActivityResultLauncher<Intent> fragmentLauncher;

    public TodoRecyclerViewAdapter(Context context, List<TodoItem> todoItems, Spinner spinner, ActivityResultLauncher<Intent> fragmentLauncher) {
        this.context = context;
        this.todoItems = todoItems;
        this.spinner = spinner;
        this.fragmentLauncher = fragmentLauncher;
    }


    @NonNull
    @Override
    public TodoRecyclerViewAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_todo_card, parent, false);

        TodoViewHolder holder = new TodoViewHolder(view);
        setupSpinner();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoRecyclerViewAdapter.TodoViewHolder holder, int position) {
        TodoItem todo = todoItems.get(position);

        holder.todoTitle.setText(todo.getTitle());
        holder.todoDueDate.setText(genTodoDescStr(todo));

        holder.checkboxCompletion.setOnClickListener(l -> completeTask(holder, position));
        holder.imageButtonDelete.setOnClickListener(l -> delete(position));

        holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(context, todo.isAssignment() ? R.color.assignmentColor : R.color.examColor));

        setupCompleted(holder, todo);

        holder.cardView.setOnClickListener((view) -> onClick(holder, todo, position));
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }


    /**
     * A helper method to generate the string containing todo timings
     * @param  todo the todo item to be used
     * @return string containing todo item due date
     */
    private String genTodoDescStr(TodoItem todo) {
        String todoDesc = todo.getCourse().getName();
        Log.d("TODO_RECYCLER_VIEW", todo.getCourse().getName());
        if (todo.getDueDate() != null) {
            String dueDateStr = todo.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
            todoDesc +=  " | " + dueDateStr;
        }
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

    private void setupSpinner() {
        SortingArrayAdapter adapter = new SortingArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.getResources().getStringArray(R.array.todos_sort_options)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TodoItemSorts todoItemSorts = new TodoItemSorts();

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                todoItemSorts.sortByIndex(i, todoItems);
                notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onClick(TodoViewHolder holder, TodoItem todo, int i) {
        Intent intent = new Intent(context, EditTodoActivity.class);
        intent.putExtra("TODO", todo);
        intent.putExtra("POSITION", i);
        fragmentLauncher.launch(intent);
    }

    public void update(TodoItem todo, int position) {
        if (position == -1) {
            todoItems.add(todo);
            notifyItemChanged(todoItems.size() - 1, todo);
        } else {
            todoItems.set(position, todo);
            notifyItemChanged(position, todo);
        }
    }

    private void delete(int position) {
        todoItems.remove(position);
        notifyItemRemoved(position);
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView todoTitle, todoDueDate;
        private CheckBox checkboxCompletion;
        private ImageButton imageButtonDelete;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_todo);
            todoTitle = itemView.findViewById(R.id.textview_todotitle);
            todoDueDate = itemView.findViewById(R.id.textview_tododuedate);
            checkboxCompletion = itemView.findViewById(R.id.checkbox_completion);
            imageButtonDelete = itemView.findViewById(R.id.imagebutton_delete);

        }
    }

}
