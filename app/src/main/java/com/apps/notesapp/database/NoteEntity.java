package com.apps.notesapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "notes")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    int id;

    String note;
    Date date;

    @Ignore
    public NoteEntity() {
    }
    @Ignore
    public NoteEntity(String note, Date date) {
        this.note = note;
        this.date = date;
    }

    public NoteEntity(int id, String note, Date date) {
        this.id = id;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
