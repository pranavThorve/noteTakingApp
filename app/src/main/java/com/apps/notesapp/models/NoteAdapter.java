package com.apps.notesapp.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.notesapp.EditorActivity;
import com.apps.notesapp.R;
import com.apps.notesapp.database.NoteEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.viewHolder> {

    Context context;
    List<NoteEntity> noteList;

    public NoteAdapter(Context context, List<NoteEntity> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.template,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
           NoteEntity list = noteList.get(position);
           holder.textView.setText(list.getNote());

           holder.textView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, EditorActivity.class);
                   intent.putExtra("IdKey",list.getId());
                   context.startActivity(intent);
               }
           });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public NoteEntity getNoteToSwipe(int id){
        return noteList.get(id);
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tempText)
        TextView textView;

//        @BindView(R.id.tempFab)
//        FloatingActionButton fab;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
