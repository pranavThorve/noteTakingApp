package com.apps.notesapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity noteEntity);

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertAll(List<NoteEntity> noteEntities);

   @Delete
    void delete(NoteEntity noteEntity);

   @Query("SELECT * FROM notes WHERE id= :id")
    NoteEntity getNote(int id);

   @Query("SELECT * FROM NOTES ORDER BY date DESC")
    LiveData<List<NoteEntity>> getAllNotes();

   @Query("DELETE FROM notes")
    int deleteAll();

   @Query("SELECT COUNT(*) FROM notes")
    int getCount();
}
