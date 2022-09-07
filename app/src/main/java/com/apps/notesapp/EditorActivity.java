package com.apps.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.apps.notesapp.database.NoteEntity;
import com.apps.notesapp.viewmodels.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel eViewModel;

    boolean delIcon;
    private boolean orien;
    @BindView(R.id.edit_text)
    TextView mEditText;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
//        getActionBar().setHomeAsUpIndicator(R.drawable.ic_check);


        ButterKnife.bind(this);

        if(savedInstanceState != null){
            orien = savedInstanceState.getBoolean("isOrien");
        }

        initViewModel();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("isOrien",true);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {

        Observer<NoteEntity> noteEntityObserver =
                new Observer<NoteEntity>() {
                    @Override
                    public void onChanged(NoteEntity noteEntity) {
                        if(noteEntity != null && !orien){
                            mEditText.setText(noteEntity.getNote());
                        }

                    }
                };

        eViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(EditorViewModel.class);

        eViewModel.mEditData.observe(this,noteEntityObserver);

        Bundle bundle = getIntent().getExtras();

        if(bundle == null){
            setTitle("Add Note");
            delIcon = true;
        }else{
            setTitle("Edit Note");
            int noteId = bundle.getInt("IdKey");
            eViewModel.loadData(noteId);

            delIcon  = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        if(!delIcon){
            getMenuInflater().inflate(R.menu.editor_menu,menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.del_note){
            delNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void delNote() {

        eViewModel.delNote();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.saveBtn)
    void saveData(){
           eViewModel.saveData(mEditText.getText().toString());

          finish();
    };

}














