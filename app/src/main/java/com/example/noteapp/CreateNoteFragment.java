package com.example.noteapp;
import com.example.noteapp.database.DatabaseClient;
import com.example.noteapp.database.NotesDatabase;
import com.example.noteapp.entities.Notes;
import com.example.noteapp.util.BottomSheetFragment;
import com.google.android.material.textfield.TextInputEditText;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreateNoteFragment extends Fragment {
    TextView dateTime;
    @Nullable
    String currentDate = null;

    ImageButton saveNoteButton, backButton, colorButton, tabButton, deleteButton, leftAlignmentButton, centerAlignmentButton, rightAlignmentButton;
    EditText noteTitle;
    TextInputEditText noteContent, noteTag;

    ConstraintLayout constraintLayout;

    String selectedColor;

    private Integer id = -1;

    public CreateNoteFragment() {
        // Required empty public constructor
    }

    public static CreateNoteFragment newInstance() {
        CreateNoteFragment fragment = new CreateNoteFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            id = requireArguments().getInt("noteId", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(id != -1) {
            class ShowNote extends AsyncTask<Void, Void, Void> {
                Notes specificNote;
                @Override
                protected Void doInBackground(Void... voids) {
                    specificNote = DatabaseClient.getInstance(getContext())
                            .getDatabase()
                            .noteDao()
                            .showSpecificNote(id);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    constraintLayout.setBackgroundColor(Color.parseColor("#" + specificNote.getColor()));
                    noteTitle.setText(specificNote.getNoteTitle());
                    noteContent.setText(specificNote.getContent());
                    noteTag.setText(specificNote.getTag());
                }
            }
            ShowNote sn = new ShowNote();
            sn.execute();
        }
        selectedColor = Integer.toHexString(ContextCompat.getColor(getContext(), R.color.light_blue));

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                broadcastReceiver, new IntentFilter("bottom_sheet_action")
        );
        constraintLayout = getView().findViewById(R.id.createNoteLayout);
        dateTime = getView().findViewById(R.id.dateTime);
        backButton = getView().findViewById(R.id.backButton);
        saveNoteButton = getView().findViewById(R.id.saveNoteButton);
        deleteButton = getView().findViewById(R.id.deleteButton);
        if(id != -1) {
            deleteButton.setVisibility(View.VISIBLE);
        }
        tabButton = getView().findViewById(R.id.tabButton);
        colorButton = getView().findViewById(R.id.colorButton);
        leftAlignmentButton = getView().findViewById(R.id.leftAlignmentButton);
        centerAlignmentButton = getView().findViewById(R.id.centerAlignmentButton);
        rightAlignmentButton = getView().findViewById(R.id.rightAlignmentButton);
        noteTitle = getView().findViewById(R.id.noteTitle);
        noteContent = getView().findViewById(R.id.noteContent);
        noteTag = getView().findViewById(R.id.noteTag);
        DateFormat dateFormat = DateFormat.getDateInstance();
        currentDate = dateFormat.format(new Date());
        dateTime.setText(currentDate);
        saveNoteButton.setOnClickListener(v -> {
            if(id != -1) {
                updateNote();
            }
            else {
                saveNote();
            }
        });

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        deleteButton.setOnClickListener(v -> {
                deleteNote();
        });

        tabButton.setOnClickListener(v -> {
            String tab = "    ";
            if(noteTitle.hasFocus()) {
                int iStart = noteTitle.getSelectionStart();
                int iEnd = noteTitle.getSelectionEnd();
                Editable editable = noteTitle.getText();
                if (iStart == iEnd) {
                    editable.insert(iStart, tab);
                } else {
                    editable.replace(iStart, iEnd, tab);
                    noteTitle.setSelection(noteTitle.getSelectionEnd());
                }
            }
            else if(noteTag.hasFocus()) {
                int iStart = noteTag.getSelectionStart();
                int iEnd = noteTag.getSelectionEnd();
                Editable editable = noteTag.getText();
                if (iStart == iEnd) {
                    editable.insert(iStart, tab);
                } else {
                    editable.replace(iStart, iEnd, tab);
                    noteContent.setSelection(noteContent.getSelectionEnd());
                }
            }
            else if(noteContent.hasFocus()) {
                int iStart = noteContent.getSelectionStart();
                int iEnd = noteContent.getSelectionEnd();
                Editable editable = noteContent.getText();
                if (iStart == iEnd) {
                    editable.insert(iStart, tab);
                } else {
                    editable.replace(iStart, iEnd, tab);
                    noteContent.setSelection(noteContent.getSelectionEnd());
                }
            }
        });

        leftAlignmentButton.setOnClickListener(v -> {
            if(noteTitle.hasFocus()) {
                noteTitle.setGravity(Gravity.LEFT);
            }
            else if(noteTag.hasFocus()) {
                noteTag.setGravity(Gravity.LEFT);
            }
            else if(noteContent.hasFocus()) {
                noteContent.setGravity(Gravity.LEFT);
            }
        });

        rightAlignmentButton.setOnClickListener(v -> {
            if(noteTitle.hasFocus()) {
                noteTitle.setGravity(Gravity.RIGHT);
            }
            else if(noteTag.hasFocus()) {
                noteTag.setGravity(Gravity.RIGHT);
            }
            else if(noteContent.hasFocus()) {
                noteContent.setGravity(Gravity.RIGHT);
            }
        });

        centerAlignmentButton.setOnClickListener(v -> {
            if(noteTitle.hasFocus()) {
                noteTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            else if(noteTag.hasFocus()) {
                noteTag.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            else if(noteContent.hasFocus()) {
                noteContent.setGravity(Gravity.CENTER_HORIZONTAL);
            }
        });

        colorButton.setOnClickListener(v -> {
            BottomSheetFragment bsf = BottomSheetFragment.newInstance();
            bsf.show(requireActivity().getSupportFragmentManager(), "Bottom sheet fragment");
        });
    }

    private void updateNote() {
        String title = noteTitle.getText().toString();
        String content = noteContent.getText().toString();
        String tag = noteTag.getText().toString();
        Boolean isNotNull = true;
        if (isNullOrEmpty(title)) {
            Toast.makeText(getContext(), "Note's title is missing", Toast.LENGTH_SHORT).show();
            isNotNull = false;
        }

        if (isNullOrEmpty(content)) {
            Toast.makeText(getContext(), "Note's content is missing", Toast.LENGTH_SHORT).show();
            isNotNull = false;
        }

        class UpdateNote extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase database = DatabaseClient.getInstance(getContext())
                        .getDatabase();
                Notes note = database.noteDao().showSpecificNote(id);
                note.setNoteTitle(title);
                note.setDateTime(currentDate);
                note.setContent(content);
                note.setColor(selectedColor);
                note.setTag(tag);

                database.noteDao()
                        .updateNote(note);
                noteTitle.setText("");
                noteContent.setText("");
                noteTag.setText("");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Note saved", Toast.LENGTH_LONG).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        }
        if(isNotNull == true) {
            UpdateNote un = new UpdateNote();
            un.execute();
        }
    }

    private void saveNote() {
        String title = noteTitle.getText().toString();
        String content = noteContent.getText().toString();
        String tag = noteTag.getText().toString();
        Boolean isNotNull = true;
        if (isNullOrEmpty(title)) {
            Toast.makeText(getContext(), "Note's title is missing", Toast.LENGTH_SHORT).show();
            isNotNull = false;
        }

        if (isNullOrEmpty(content)) {
            Toast.makeText(getContext(), "Note's content is missing", Toast.LENGTH_SHORT).show();
            isNotNull = false;
        }

        class SaveNote extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase database = DatabaseClient.getInstance(getContext())
                        .getDatabase();
                Notes note = new Notes();
                note.setNoteTitle(title);
                note.setDateTime(currentDate);
                note.setContent(content);
                note.setColor(selectedColor);
                note.setTag(tag);

                database.noteDao()
                        .insertNote(note);
                noteTitle.setText("");
                noteContent.setText("");
                noteTag.setText("");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Note saved", Toast.LENGTH_LONG).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        }
        if(isNotNull == true) {
            SaveNote sn = new SaveNote();
            sn.execute();
        }
    }

    private void deleteNote() {
        class DeleteNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Context context = CreateNoteFragment.this.getContext();
                if(context != null) {
                    DatabaseClient.getInstance(context).getDatabase().noteDao().deleteNote(id);
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
                return null;
            }
        }
        DeleteNote dn = new DeleteNote();
        dn.execute();
    }


    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            selectedColor = intent.getStringExtra("selectedColor");
            constraintLayout.setBackgroundColor(Color.parseColor("#" + selectedColor));
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}