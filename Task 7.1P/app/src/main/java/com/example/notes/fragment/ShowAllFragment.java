package com.example.notes.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.notes.R;
import com.example.notes.data.DatabaseHelper;
import com.example.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowAllFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowAllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowAllFragment newInstance(String param1, String param2) {
        ShowAllFragment fragment = new ShowAllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_all, container, false);
        ListView notesListView = (ListView) view.findViewById(R.id.notesListView);
        ArrayList<String> notesArrayList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(getContext());

        List<Note> noteList = db.fetchAllNotes();
        if (noteList.size() > 0) {
            for (Note note : noteList) {
                notesArrayList.add(note.getNoteTitle());
            }

            notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String note_title = (String) parent.getItemAtPosition(position);
                    Note note = db.fetchNote(note_title);
                    Fragment fragment;
                    FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayout);

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = ViewFragment.newInstance(note.getNoteTitle(), note.getNoteContent());
                    fragmentTransaction
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.frameLayout, fragment)
                        .addToBackStack(null)
                        .commit();
                    frameLayout.setVisibility(VISIBLE);
                }
            });
        }
        else {
            notesArrayList.add("No notes found, please create one!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.listview_item,notesArrayList);
        notesListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayout);
        getFragmentManager().beginTransaction().remove(ShowAllFragment.this).commit();
        frameLayout.setVisibility(View.GONE);
    }
}
