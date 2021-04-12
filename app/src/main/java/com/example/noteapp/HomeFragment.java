package com.example.noteapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.noteapp.adapter.NoteAdapter;
import com.example.noteapp.database.DatabaseClient;
import com.example.noteapp.entities.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    FloatingActionButton createNoteButton;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter = new NoteAdapter();
    SearchView searchBar;

    ArrayList<Notes> resNotes;

    Spinner searchOptionSpinner;

    String[] searchBarQueryHint = new String[]{"Search by title", "Search by tag", "Search by contents"};
    public HomeFragment() { }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createNoteButton = getView().findViewById(R.id.createNoteButton);

        searchBar = getView().findViewById(R.id.search_by_string);

        recyclerView = getView().findViewById(R.id.notes_list);

        searchOptionSpinner = getView().findViewById(R.id.search_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.search_option, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        searchOptionSpinner.setAdapter(spinnerAdapter);
        searchOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchBar.setQueryHint(searchBarQueryHint[position]);
                if(position == 0) {
                    searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            ArrayList<Notes> tempArr = new ArrayList<>();
                            for (Notes each: resNotes) {
                                if(each.getNoteTitle().toLowerCase(Locale.getDefault()).contains(newText)) {
                                    tempArr.add(each);
                                }
                            }
                            noteAdapter.setData(tempArr);
                            noteAdapter.notifyDataSetChanged();
                            return true;
                        }
                    });
                }
                else if(position == 1){
                    searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            ArrayList<Notes> tempArr = new ArrayList<>();
                            for (Notes each: resNotes) {
                                if(each.getTag().toLowerCase(Locale.getDefault()).contains(newText)) {
                                    tempArr.add(each);
                                }
                            }
                            noteAdapter.setData(tempArr);
                            noteAdapter.notifyDataSetChanged();
                            return true;
                        }
                    });
                }
                else {
                    searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            ArrayList<Notes> tempArr = new ArrayList<>();
                            for (Notes each: resNotes) {
                                if(each.getContent().toLowerCase(Locale.getDefault()).contains(newText)) {
                                    tempArr.add(each);
                                }
                            }
                            noteAdapter.setData(tempArr);
                            noteAdapter.notifyDataSetChanged();
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        class LoadNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient databaseClient = DatabaseClient.getInstance(getContext());
                List<Notes> notes = databaseClient.getDatabase().noteDao().loadNotes();
                noteAdapter.setData(notes);
                resNotes = (ArrayList<Notes>) notes;
                recyclerView.setAdapter(noteAdapter);
                return null;
            }
        }
        LoadNote loader = new LoadNote();
        loader.execute();

        noteAdapter.setOnClickListener(onClicked);


        createNoteButton.setOnClickListener(v -> {
            replaceFragment(CreateNoteFragment.newInstance(), false);
        });


        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Notes> tempArr = new ArrayList<>();
                for (Notes each: resNotes) {
                    if(each.getNoteTitle().toLowerCase(Locale.getDefault()).contains(newText) ||
                            each.getContent().toLowerCase(Locale.getDefault()).contains(newText)) {
                        tempArr.add(each);
                    }
                }
                noteAdapter.setData(tempArr);
                noteAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    private NoteAdapter.OnItemClickListener onClicked = noteItem -> {

        Fragment f;
        Bundle b = new Bundle();
        b.putInt("noteId", noteItem.getId());
        f = CreateNoteFragment.newInstance();
        f.setArguments(b);
        replaceFragment(f, false);
    };

    void replaceFragment(Fragment fragment, Boolean isTransition) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(isTransition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();

    }
}