package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "vocabDB";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_VOCAB = "vocab";
    private static final String ID = "id";
    private static final String WORD = "word";
    private static final String DEFINITION = "definition";
    private static final String LEARNED = "learned";

    private Context thisContext = null;

    public DatabaseManager(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        thisContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " + TABLE_VOCAB + "(" + ID;
        sqlCreate += " integer primary key autoincrement, " + WORD;
        sqlCreate += " text, " + DEFINITION + " text, " + LEARNED + " integer)";
        db.execSQL(sqlCreate);
        if (thisContext != null) {
            // Only load JSON on first creation of db
            loadJSONVocab(thisContext, db);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_VOCAB);
        onCreate(db);
    }

    public int getNumberOfWords() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT COUNT(*) FROM " + TABLE_VOCAB;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (!(cursor.moveToFirst())) {
            return 0;
        }

        int wordCount = cursor.getInt(0);
        db.close();
        return wordCount;
    }

    // Loads in the JSON dictionary vocab from assets folder to database
    private boolean loadJSONVocab(Context context, SQLiteDatabase db) {
        Log.d("DB", "Loading vocab json");
        String jsonString = null;
        try {
            InputStream inputStream = context.getAssets().open("vocab_words.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            JSONArray json = new JSONArray(jsonString);
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonWord = json.getJSONObject(i);
                String word = jsonWord.getString("word");
                String def = jsonWord.getString("definition");
                SQLiteStatement insert = db.compileStatement("insert into " + TABLE_VOCAB + " values(null, ?, ?, 0)");
                insert.bindString(1, word);
                insert.bindString(2, def);
                insert.executeInsert();

            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    // Retrieves a random word from the database
    // Returns a pair of strings containing (word, definition)
    public String[] getRandomWord(int learned) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT COUNT(*) FROM " + TABLE_VOCAB;
        if (learned == 1) {
            sqlQuery += " WHERE " + LEARNED + " = 1";
        } else if (learned == 0){
            sqlQuery += " WHERE " + LEARNED + " = 0";
        }
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (!(cursor.moveToFirst()))
            return new String[]{"OOF", "DED"};
        int wordCount = cursor.getInt(0);
        if (wordCount <= 0) {
            return new String[]{"EXTRAOOF", "MEGADED"};
        }
        Random rand = new Random();
        int randomWordIndex = rand.nextInt(wordCount-1);
        sqlQuery = "select * from " + TABLE_VOCAB;
        if (learned == 1) {
            sqlQuery += " WHERE " + LEARNED + " = 1";
        } else if (learned == 0){
            sqlQuery += " WHERE " + LEARNED + " = 0";
        }
        cursor = db.rawQuery(sqlQuery, null);
        if (!(cursor.moveToPosition(randomWordIndex)))
            return new String[]{"BIGOOF", "DEDDER"};
        String[] wordDef = new String[2];
        wordDef[0] = cursor.getString(1);
        wordDef[1] = cursor.getString(2);

        db.close();

        return wordDef;

    }

    public boolean setWordLearned(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateStatement = "UPDATE " + TABLE_VOCAB;
        updateStatement += " SET " + LEARNED + " = 1 ";
        updateStatement += "WHERE " + WORD + " = '" + word + "'" ;
        try {
            db.execSQL(updateStatement);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
