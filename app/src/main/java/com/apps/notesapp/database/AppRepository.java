package com.apps.notesapp.database;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.apps.notesapp.util.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AppRepository {

    private static  AppRepository instance;
    private AppDatabase mDatabase;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public LiveData<List<NoteEntity>> mNotesList;

    public static AppRepository getInstance(Context context) {
        return instance = new AppRepository(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private AppRepository(Context context){
        mDatabase = AppDatabase.getInstance(context);
        mNotesList = getAllNotes();
    }



    public void addSampleData() {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insertAll(SampleData.getSampleData());
            }
        });
    }

    public LiveData<List<NoteEntity>> getAllNotes(){
        return  mDatabase.notesDao().getAllNotes();
    }

    public void deleteAllData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().deleteAll();
            }
        });
    }

    public NoteEntity loadNote(int noteId) {

        return mDatabase.notesDao().getNote(noteId);
    }

    public void upateData(NoteEntity noteEntity) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insert(noteEntity);
            }
        });
    }

    public void delNote(NoteEntity note) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().delete(note);
            }
        });
    }
}
