package com.example.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskAddActivity extends AppCompatActivity {

    private Button saveTaskButton;
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private EditText taskDueDateEditText;
    private Button navigateBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_task);
        taskTitleEditText = findViewById(R.id.addtitleEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        taskDueDateEditText = findViewById(R.id.dueDateEditText);

        saveTaskButton = findViewById(R.id.saveButton);
        navigateBackButton = findViewById(R.id.backButton);

        saveTaskButton.setOnClickListener(v -> saveNewTask());
        navigateBackButton.setOnClickListener(v -> returnToMain());
    }

    private void saveNewTask() {
        String title = taskTitleEditText.getText().toString();
        String description = taskDescriptionEditText.getText().toString();
        String dueDate = taskDueDateEditText.getText().toString();

        if (!DateValidation.isInputValid(title, description, dueDate)) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!DateValidation.isDateValid(dueDate)) {
            Toast.makeText(this, "Please enter a valid date (dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
            return;
        }

        Task newTask = new Task(title, description, dueDate);
        TaskDbHelper dbHelper = new TaskDbHelper(this);
        long taskId = dbHelper.createTask(newTask);
        newTask.setId((int) taskId);

        Toast.makeText(this, "Task successfully saved!", Toast.LENGTH_SHORT).show();
        returnToMain();
        finish();
    }

    private void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
