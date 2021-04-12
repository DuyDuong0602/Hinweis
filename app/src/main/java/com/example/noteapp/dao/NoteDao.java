package com.example.noteapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapp.entities.Notes;

import java.util.List;


@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> loadNotes();

    @Query("SELECT * FROM notes WHERE id =:id")
    Notes showSpecificNote(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Notes note);

    @Query("DELETE FROM notes WHERE id =:id")
    void deleteNote(Integer id);

    @Update
    void updateNote(Notes note);
}
