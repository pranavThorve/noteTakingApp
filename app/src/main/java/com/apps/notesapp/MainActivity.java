package com.apps.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.notesapp.models.NoteAdapter;
import com.apps.notesapp.database.NoteEntity;
import com.apps.notesapp.util.SampleData;
import com.apps.notesapp.viewmodels.ListActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public ListActivityViewModel mViewModel;
    NoteAdapter adapter;
    private List<NoteEntity> mList = new ArrayList<>();;

    @BindView(R.id.container)
    RecyclerView recyclerView;

    @OnClick(R.id.fabAdd)
    void onFabClicked(){
        Intent intent = new Intent(this,EditorActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();

        ButterKnife.bind(this);

        initRecycler();
    }

    private void initRecycler() {
        recyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteNote(adapter.getNoteToSwipe(viewHolder.getAdapterPosition()));
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteNote(NoteEntity noteToSwipe) {
         mViewModel.deleteOnSwipe(noteToSwipe);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViewModel() {
        Observer<List<NoteEntity>> notesObserver =
                new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(List<NoteEntity> noteEntities) {
                        mList.clear();
                        mList.addAll(noteEntities);

                        if (adapter == null) {
                            adapter = new NoteAdapter(MainActivity.this,mList);
                            recyclerView.setAdapter(adapter);
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ListActivityViewModel.class);

        mViewModel.mNotes.observe(MainActivity.this,notesObserver);

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.add_sample_data: {
                addSampleData();
                return true;
            }

            case R.id.delete_data: {
                deleteAllData();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteAllData() {
        mViewModel.deleteAllDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addSampleData() {
        mViewModel.addSampleData();
    }
}












