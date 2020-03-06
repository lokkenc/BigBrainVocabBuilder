package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayActivity extends AppCompatActivity {

    public static int difficultyFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }


    public void wordSearch(View v) {
        Intent myIntent = new Intent(this, WordSearchActivity.class);
        this.startActivity(myIntent);
    }

    public void LoadCatchGame(View v) {
        Intent catchGameIntent = new Intent(this, CatchGameActivity.class);
        this.startActivity(catchGameIntent);
    }
}
