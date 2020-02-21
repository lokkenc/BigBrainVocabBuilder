package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CategoriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.easyButton:
                PlayActivity.difficultyFlag = 1;
                TestActivity.difficultyFlag = 1;
                break;
            case R.id.mediumButton:
                PlayActivity.difficultyFlag = 2;
                TestActivity.difficultyFlag = 2;
                break;
            case R.id.hardButton:
                PlayActivity.difficultyFlag = 3;
                TestActivity.difficultyFlag = 3;
                break;
            case R.id.difficultButton:
                PlayActivity.difficultyFlag = 4;
                TestActivity.difficultyFlag = 4;
                break;
            case R.id.extremeButton:
                PlayActivity.difficultyFlag = 5;
                TestActivity.difficultyFlag = 5;
                break;
        }



        Intent myIntent;
        if (MainActivity.screenVar == 0)
        {
            myIntent = new Intent(this, PlayActivity.class);
        } else {
            myIntent = new Intent(this, TestActivity.class);
        }
        this.startActivity(myIntent);
    }

}
