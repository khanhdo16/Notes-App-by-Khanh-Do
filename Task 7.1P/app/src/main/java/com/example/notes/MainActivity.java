package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.notes.data.DatabaseHelper;
import com.example.notes.fragment.CreateFragment;
import com.example.notes.fragment.ShowAllFragment;
import com.example.notes.model.Note;
import com.example.notes.util.Util;

import java.io.File;
import java.util.List;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout);
    }

    public void showFragment(View view) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.newNoteButton:
                fragment = new CreateFragment();
                fragmentTransaction
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
                frameLayout.setVisibility(VISIBLE);
                break;
            case R.id.showAllButton:
                fragment = new ShowAllFragment();
                fragmentTransaction
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
                frameLayout.setVisibility(VISIBLE);
                break;
            default:
                throw new IllegalArgumentException("Invalid parameter received!");
        }

    }
}
