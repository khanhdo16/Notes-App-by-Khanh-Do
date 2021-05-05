package com.example.notes.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.data.DatabaseHelper;
import com.example.notes.model.Note;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NOTE_CONTENT = "note_content";
    private static final String NOTE_TITLE = "note_title";

    // TODO: Rename and change types of parameters
    private String note_title;
    private String note_content;

    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param note_content Note content.
     * @param note_title Parameter 2.
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String note_title, String note_content) {
        CreateFragment fragment = new CreateFragment();
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
            note_content = getArguments().getString(NOTE_CONTENT);
            note_title = getArguments().getString(NOTE_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        DatabaseHelper db = new DatabaseHelper(this.getContext());
        FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayout);
        EditText noteTitleCreateEditText = (EditText) view.findViewById(R.id.noteTitleCreateEditText);
        EditText noteCreateEditText = (EditText) view.findViewById(R.id.noteCreateEditText);
        Button saveButton = (Button) view.findViewById(R.id.updateButton);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = db.createNote(new Note(noteTitleCreateEditText.getText().toString(), noteCreateEditText.getText().toString()));
                if (result > 0) {
                    getFragmentManager().beginTransaction().remove(CreateFragment.this).commit();
                    frameLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Note created!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Note creating failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayout);
        getFragmentManager().beginTransaction().remove(CreateFragment.this).commit();
        frameLayout.setVisibility(View.GONE);
    }
}
