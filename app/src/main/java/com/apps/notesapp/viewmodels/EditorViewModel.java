package com.apps.notesapp.viewmodels;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.notesapp.database.AppRepository;
import com.apps.notesapp.database.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mEditData = new MutableLiveData<NoteEntity>();

    private Executor mExecutor = Executors.newSingleThreadExecutor();
    private AppRepository mRepository;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public EditorViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void loadData(final int noteId) {
        mExecutor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                NoteEntity noteEntity = mRepository.loadNote(noteId);
                mEditData.postValue(noteEntity);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveData(String data) {
        NoteEntity noteEntity = mEditData.getValue();

        if (noteEntity == null) {
             if(TextUtils.isEmpty(data.trim())){
                 return;
             }else{
                 noteEntity = new NoteEntity(data.trim(), new Date());
             }
        }else{
            noteEntity.setNote(data.trim());
        }
        mRepository.upateData(noteEntity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void delNote() {
        mRepository.delNote(mEditData.getValue());
    }
}
