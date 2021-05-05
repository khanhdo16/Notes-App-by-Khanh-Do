package com.example.notes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notes.model.Note;
import com.example.notes.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATABASE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.NOTE_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + Util.NOTE_TITLE + " TEXT, " + Util.NOTE_CONTENT + " TEXT)";

        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME;

        db.execSQL(DROP_TABLE, null);

        onCreate(db);
    }

    public long createNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NOTE_TITLE, note.getNoteTitle());
        contentValues.put(Util.NOTE_CONTENT, note.getNoteContent());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();

        return newRowId;
    }

    public List<Note> fetchAllNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(cursor.getInt(0));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));

                noteList.add(note);

            }while(cursor.moveToNext());
        }

        return noteList;
    }

    public Note fetchNote(String note_title) {
        Note note;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, null, Util.NOTE_TITLE + "=?",
            new String[] {note_title}, null, null, null);

        if(cursor.moveToFirst());
        {
            note = new Note(cursor.getString(1), cursor.getString(2));
        }

        return note;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NOTE_CONTENT, note.getNoteContent());

        return db.update (Util.TABLE_NAME, contentValues, Util.NOTE_TITLE + "=?",
            new String[] {note.getNoteTitle()});
    }

    public int deleteNote(String note_title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Util.TABLE_NAME, Util.NOTE_TITLE + "=?",
            new String[] {note_title});
    }
}
