package com.ProjectITI.tripsproject.ui.Trips_History.Notes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ProjectITI.tripsproject.R;

import java.util.ArrayList;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    ArrayList<String>NotestList;

    public NotesAdapter(ArrayList<String> NotestList, Context context) {
        this.NotestList = NotestList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row,parent,false);
        NotesViewHolder viewHolder=new NotesViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Log.i("tag","data before : "+position +": "+NotestList.get(position));

        if(NotestList.get(position) != null) {
            Log.i("tag","data : "+NotestList.get(position).toString());
            holder.text.setText(NotestList.get(position));
        }else{
            holder.text.setText("NOTES  : ");

        }
    }

    @Override
    public int getItemCount() {
        Log.i("tag","Notes size : "+NotestList.size());
        return NotestList.size();
    }


    public static class NotesViewHolder extends RecyclerView.ViewHolder{

        protected TextView text;

        public NotesViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.note_text);
        }
    }
}
