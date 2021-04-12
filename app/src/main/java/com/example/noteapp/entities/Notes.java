package com.example.noteapp.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Notes")
public class Notes implements Serializable {
    @PrimaryKey @Nullable
    Integer id = null;

    @ColumnInfo(name = "title") @Nullable
    String noteTitle = null;


    @ColumnInfo(name = "date_time") @Nullable
    String dateTime = null;

    @ColumnInfo(name = "tag") @Nullable
    String tag = null;


    @ColumnInfo(name = "content") @Nullable
    String content = null;


    @ColumnInfo(name = "color") @Nullable
    String color = null;

    @NonNull
    @Override
    public String toString() {
        return noteTitle + dateTime;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setTag(@Nullable String tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getContent() {
        return content;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    @Nullable
    public String getTag() {
        return tag;
    }

    @Nullable
    public String getColor() {
        return color;
    }

    public void setColor(@Nullable String color) {
        this.color = color;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }
}
