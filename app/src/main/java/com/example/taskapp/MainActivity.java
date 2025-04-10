package com.example.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private TaskDbHelper dbHelper;
    private Button addTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and set up LayoutManager
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database helper and retrieve the list of tasks
        dbHelper = new TaskDbHelper(this);
        List<Task> tasks = dbHelper.getAllTasks();

        // Sort tasks by due date
        Collections.sort(tasks, new Comparator<Task>() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(Task task1, Task task2) {
                try {
                    // Compare due dates of tasks
                    return sdf.parse(task1.getDueDate()).compareTo(sdf.parse(task2.getDueDate()));
                } catch (ParseException e) {
                    // Handle parse exceptions if dates are invalid
                    throw new IllegalArgumentException(e);
                }
            }
        });

        // Set up the adapter to display tasks in the RecyclerView
        adapter = new RecyclerViewAdapter(tasks, this);
        recyclerView.setAdapter(adapter);

        // Initialize "Add Task" button and set click listener to navigate to task adding activity
        addTaskBtn = findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskAddActivity.class);
            startActivity(intent);
        });
    }
}
