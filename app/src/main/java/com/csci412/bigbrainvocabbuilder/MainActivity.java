package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.csci412.bigbrainvocabbuilder.ui.login.LoginActivity;


public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager = null;
    public static Profile profile;
    public static int screenVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DatabaseManager(this);
        SQLiteDatabase db = dbManager.getWritableDatabase();
        profile = new Profile(dbManager.getNumberOfWords(), this.getResources().getDrawable(R.drawable.base_profile_pic));
        db.close();
    }

    protected void changeColors(){
        //find all other buttons on same page as clicked button
        // reset all their colors
        // change color of clicked button
    }

    public void play(View v) {
        screenVar = 0;
        Intent myIntent = new Intent(this, CategoriesActivity.class);
        this.startActivity(myIntent);
    }

    public void learn(View v) {
        Intent myIntent = new Intent(this, LearnActivity.class);
        this.startActivity(myIntent);
    }
    public void test(View v) {
        screenVar = 1;
        Intent myIntent = new Intent(this, CategoriesActivity.class);
        this.startActivity(myIntent);
    }
    public void stats(View v) {
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }
    public void settings(View v) {
        Intent myIntent = new Intent(this, SettingsActivity.class);
        this.startActivity(myIntent);
    }
    public void login(View v) {
        Intent myIntent = new Intent(this, LoginActivity.class);
        this.startActivity(myIntent);
    }
}
