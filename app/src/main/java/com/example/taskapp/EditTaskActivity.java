package com.example.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskapp.TaskDbHelper;

public class EditTaskActivity extends AppCompatActivity {

    private Button updateButton;
    private EditText addtitleEditText;
    private EditText descriptionEditText;
    private EditText dueDateEditText;
    private Button backButton;
    private TaskDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);
        dbHelper = new TaskDbHelper(this);

        // Initialize UI components
        addtitleEditText = findViewById(R.id.addtitleEditText);
        descriptionEditText = findViewById(R.id.taskDescriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.backButton);

        // Set click listeners for update and back buttons
        updateButton.setOnClickListener(v -> updateTask());
        backButton.setOnClickListener(v -> backHome());

        // Retrieve task details from the intent and prepopulate the fields
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TASK_ID")) {
            int taskId = intent.getIntExtra("TASK_ID", -1);
            String taskTitle = intent.getStringExtra("TASK_TITLE");
            String taskDescription = intent.getStringExtra("TASK_DESCRIPTION");
            String taskDueDate = intent.getStringExtra("TASK_DUE_DATE");

            addtitleEditText.setText(taskTitle);
            descriptionEditText.setText(taskDescription);
            dueDateEditText.setText(taskDueDate);
        }
    }

    // Method to update the task with the new details
    public void updateTask() {
        String title = addtitleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String dueDate = dueDateEditText.getText().toString();

        // Check if any field is empty
        if (!DateValidation.isInputValid(title, description, dueDate)) {
            Toast.makeText(this, "All fields are required. Please fill them out.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the entered date
        if (!DateValidation.isDateValid(dueDate)) {
            Toast.makeText(this, "Invalid date. Please enter a valid date in the format dd/MM/yyyy (and not today).", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the task ID from the intent to identify the task to update
        Intent intent = getIntent();
        int taskId = intent.getIntExtra("TASK_ID", -1);
        if (intent != null && intent.hasExtra("TASK_ID")) {

            // Ensure the task ID is valid
            if (taskId == -1) {
                Toast.makeText(this, "Task ID is invalid. Unable to update task.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new task object and set the task ID
            Task task = new Task(title, description, dueDate);
            task.setId(taskId);

            // Update the task in the database and provide feedback
            if (dbHelper.updateTask(task)) {
                Toast.makeText(this, "Task successfully updated.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update task. Please try again.", Toast.LENGTH_SHORT).show();
            }

            // Return to the main activity and finish this activity
            backHome();
            finish();
        }
    }

    // Method to navigate back to the main screen
    private void backHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
