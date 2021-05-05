package com.example.notes.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.data.DatabaseHelper;
import com.example.notes.model.Note;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NOTE_TITLE = "note_title";
    private static final String NOTE_CONTENT = "note_content";

    // TODO: Rename and change types of parameters
    private String note_title;
    private String note_content;
    DatabaseHelper db;
    EditText noteViewEditText;
    EditText noteTitleViewEditText;

    public ViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param note_title Note Title.
     * @param note_content Note Content.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewFragment newInstance(String note_title, String note_content) {
        ViewFragment fragment = new ViewFragment();
        Bundle args = new Bundle();
        args.putString(NOTE_TITLE, note_title);
        args.putString(NOTE_CONTENT, note_content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note_title = getArguments().getString(NOTE_TITLE);
            note_content = getArguments().getString(NOTE_CONTENT);
        }
        db = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        noteViewEditText = (EditText) view.findViewById(R.id.noteViewEditText);
        noteTitleViewEditText = (EditText) view.findViewById(R.id.noteTitleViewEditText);
        Button updateButton = (Button) view.findViewById(R.id.updateButton);
        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);

        noteTitleViewEditText.setText(note_title);
        noteViewEditText.setText(note_content);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionFragment(v);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionFragment(v);
            }
        });
        return view;
    }

    public void actionFragment(View view) {
        Note note;
        int result;
        switch (view.getId()) {
            case R.id.updateButton:
                note = new Note (noteTitleViewEditText.getText().toString(), noteViewEditText.getText().toString());
                result = db.updateNote(note);
                if (result > 0) {
                    Toast.makeText(getContext(), "Note updated!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Note updating failed!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.deleteButton:
                result = db.deleteNote(note_title);
                if (result > 0) {
                    Toast.makeText(getContext(), "Note deleted!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Note deleting failed!", Toast.LENGTH_SHORT).show();
                }
                getActivity().onBackPressed();
                break;
            default:
                throw new IllegalArgumentException("Invalid parameter received!");
        }
    }
}
