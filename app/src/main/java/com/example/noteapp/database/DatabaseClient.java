package com.example.noteapp.database;

import android.content.Context;

import androidx.room.Room;



public class DatabaseClient {
    private Context context;
    private static DatabaseClient dbInstance;
    private NotesDatabase database;

    private DatabaseClient(Context context) {
        this.context = context;

        database = Room.databaseBuilder(context, NotesDatabase.class, "notes.db").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if(dbInstance == null) {
            dbInstance = new DatabaseClient(context);
        }
        return dbInstance;
    }

    public NotesDatabase getDatabase() {
        return database;
    }
}
