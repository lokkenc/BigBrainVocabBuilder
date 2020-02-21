package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {
    
    public static int difficultyFlag;

    TestLogic tl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tl = new TestLogic(this);
        setTestQuestion();
    }

    void setTestQuestion() {
        String[] content = tl.createQuestion();
        TextView wordView = (TextView)findViewById(R.id.testVocabWord);
        RadioButton choice0 = (RadioButton)findViewById(R.id.testChoice0);
        RadioButton choice1 = (RadioButton)findViewById(R.id.testChoice1);
        RadioButton choice2 = (RadioButton)findViewById(R.id.testChoice2);

        wordView.setText(content[0]);
        choice0.setText(content[1]);
        choice1.setText(content[2]);
        choice2.setText(content[3]);
    }
}
