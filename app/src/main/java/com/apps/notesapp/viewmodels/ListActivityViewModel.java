package com.apps.notesapp.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.apps.notesapp.database.AppRepository;
import com.apps.notesapp.database.NoteEntity;
import com.apps.notesapp.util.SampleData;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ListActivityViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>>  mNotes;
    private AppRepository mRepository;

    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotesList;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllDate() {
        mRepository.deleteAllData();
    }

    public void deleteOnSwipe(NoteEntity noteToSwipe) {
        mRepository.delNote(noteToSwipe);
    }
}
