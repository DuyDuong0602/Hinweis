package com.example.noteapp.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.entities.Notes;


import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Notes> listNotes;

    @Nullable
    OnItemClickListener itemClickListener = null;

    public void setData(List<Notes> listNotes) {
        this.listNotes = listNotes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        TextView title = holder.itemView.findViewById(R.id.specificNoteTitle);
        TextView date = holder.itemView.findViewById(R.id.specificNoteDate);
        TextView content = holder.itemView.findViewById(R.id.specificNoteContent);
        TextView tag = holder.itemView.findViewById(R.id.specificNoteTag);
        CardView noteItem = holder.itemView.findViewById(R.id.noteItem);
        title.setText(listNotes.get(position).getNoteTitle());
        date.setText(listNotes.get(position).getDateTime());
        content.setText(listNotes.get(position).getContent());
        tag.setText(listNotes.get(position).getTag());


        if(listNotes.get(position).getColor() != null) {
            Log.d("Color format",listNotes.get(position).getColor());
            noteItem.setCardBackgroundColor(Color.parseColor("#" + listNotes.get(position).getColor()));
        }
        else {
            noteItem.setCardBackgroundColor(ContextCompat.getColor(noteItem.getContext(),R.color.purple));
        }
        noteItem.setOnClickListener(v -> {
            itemClickListener.onClicked(listNotes.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public void setOnClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



    public interface OnItemClickListener {
        void onClicked(Notes noteItem);
    }
}
