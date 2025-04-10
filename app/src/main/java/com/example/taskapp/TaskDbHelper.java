package com.example.taskapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class TaskDbHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "TaskDb.db";
    private static final int DATABASE_VERSION = 1;

    // Inner class that defines the table structure
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";  // Table name
        public static final String COLUMN_TITLE = "title";  // Task title column
        public static final String COLUMN_DESCRIPTION = "description";  // Task description column
        public static final String COLUMN_DUE_DATE = "dueDate";  // Task due date column
    }

    // Constructor to initialize the database helper
    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the task table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " ("
                + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + TaskEntry.COLUMN_DESCRIPTION + " TEXT, "
                + TaskEntry.COLUMN_DUE_DATE + " TEXT);";
        db.execSQL(SQL_CREATE_TASKS_TABLE);  // Execute the SQL to create the table
    }

    // Upgrade the database (not used here but can be implemented for future changes)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No upgrade logic yet
    }

    // Create a new task in the database
    public long createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, task.getTitle());  // Add task title
        values.put(TaskEntry.COLUMN_DESCRIPTION, task.getDescription());  // Add task description
        values.put(TaskEntry.COLUMN_DUE_DATE, task.getDueDate());  // Add task due date
        long id = db.insert(TaskEntry.TABLE_NAME, null, values);  // Insert the new task into the database
        db.close();  // Close the database connection
        return id;  // Return the id of the newly created task
    }

    // Update an existing task in the database
    public boolean updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, task.getTitle());  // Update task title
        values.put(TaskEntry.COLUMN_DESCRIPTION, task.getDescription());  // Update task description
        values.put(TaskEntry.COLUMN_DUE_DATE, task.getDueDate());  // Update task due date
        int numUpdate = db.update(TaskEntry.TABLE_NAME, values, TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getId())});  // Perform the update query
        db.close();  // Close the database connection
        return numUpdate > 0;  // Return true if one or more rows were updated
    }

    // Delete a task from the database
    public boolean deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numDeleted = db.delete(TaskEntry.TABLE_NAME, TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getId())});  // Perform the delete query
        db.close();  // Close the database connection
        return numDeleted > 0;  // Return true if the task was deleted successfully
    }

    // Retrieve all tasks from the database
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TaskEntry.TABLE_NAME,
                new String[]{TaskEntry._ID, TaskEntry.COLUMN_TITLE,
                        TaskEntry.COLUMN_DESCRIPTION, TaskEntry.COLUMN_DUE_DATE},
                null, null, null, null, null);  // Query all tasks
        if (cursor.moveToFirst()) {
            do {
                // Create a Task object for each row in the database
                Task task = new Task(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                task.setId(cursor.getInt(0));  // Set the task ID
                tasks.add(task);  // Add the task to the list
            } while (cursor.moveToNext());
        }
        cursor.close();  // Close the cursor
        db.close();  // Close the database connection
        return tasks;  // Return the list of tasks
    }
}
