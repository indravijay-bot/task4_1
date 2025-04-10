package com.example.taskapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Task> tasks;  // List of tasks to display
    private Context context;  // Context to start new activities
    private TaskDbHelper dbHelper;  // Database helper to manage task data

    // Constructor to initialize tasks list and context
    public RecyclerViewAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
        this.dbHelper = new TaskDbHelper(context);  // Initialize DB helper
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each task item
        View itemView = LayoutInflater.from(context).inflate(R.layout.delete_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        // Bind task data to the view components
        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());
        holder.dueDateTextView.setText(task.getDueDate());

        // Set up listener for the "Edit" button to open the edit task activity
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTaskActivity.class);
            intent.putExtra("TASK_ID", task.getId());
            intent.putExtra("TASK_TITLE", task.getTitle());
            intent.putExtra("TASK_DESCRIPTION", task.getDescription());
            intent.putExtra("TASK_DUE_DATE", task.getDueDate());
            context.startActivity(intent);
        });

        // Set up listener for the "Delete" button to remove the task
        holder.deleteButton.setOnClickListener(v -> {
            // Delete task from the database
            if (dbHelper.deleteTask(task)) {
                // Remove task from the list and notify the adapter
                tasks.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                // Show success message
                Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Show error message if the task couldn't be deleted
                Toast.makeText(context, "Failed to delete the task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();  // Return the number of tasks in the list
    }

    // ViewHolder class to represent each task item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;  // TextView to display task title
        TextView dueDateTextView;  // TextView to display task due date
        TextView descriptionTextView;  // TextView to display task description
        Button editButton;  // Button to edit the task
        Button deleteButton;  // Button to delete the task

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize view components
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
