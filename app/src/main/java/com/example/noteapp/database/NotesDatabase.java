package com.example.noteapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.noteapp.dao.NoteDao;
import com.example.noteapp.entities.Notes;

@Database(entities = Notes.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
