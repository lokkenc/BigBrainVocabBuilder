package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    Button button;
    TestResult tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setResults();
    }

    public void setResults() {
        tr = TestActivity.tr;
        TextView grade = findViewById(R.id.words_learned_amount);
        TextView score = findViewById(R.id.score_amount);
        TextView testsTaken = findViewById(R.id.tests_taken);
        grade.setText("" + tr.getGrade());
        score.setText("" + tr.getNumCorrect());
        testsTaken.setText("" + tr.getTestsTaken());
    }

    public void goBack(View v) {
        this.finish();
    }
}
