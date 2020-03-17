package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.Random;
import android.content.Intent;

public class TestActivity extends AppCompatActivity {

    public static int difficultyFlag;
    public static int totalQ;
    public static TestResult tr;
    RadioButton choice0;
    RadioButton choice1;
    RadioButton choice2;
    int correctChoice;
    private String testWord;

    private DatabaseManager dbManager;

    TestLogic tl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tl = new TestLogic(this);
        tr = new TestResult();
        totalQ = (difficultyFlag * 5) - 1;
        tr.setTestsTaken(totalQ + 1);
        setTestQuestion();
        dbManager = new DatabaseManager(this);
    }

    void setTestQuestion() {
        Random random = new Random();
        int ranInt = random.nextInt(3) + 1;
        int newInt1 = ranInt;
        int newInt2 = 0;
        if (ranInt == 1)
            correctChoice = 0;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.testGroup);
        radioGroup.clearCheck();

        String[] content = tl.createQuestion();
        TextView wordView = (TextView)findViewById(R.id.testVocabWord);
        choice0 = (RadioButton)findViewById(R.id.testChoice0);
        choice1 = (RadioButton)findViewById(R.id.testChoice1);
        choice2 = (RadioButton)findViewById(R.id.testChoice2);

        wordView.setText(content[0]);
        testWord = content[0];
        choice0.setText(content[ranInt]);
        while(ranInt == newInt1){
            ranInt = random.nextInt(3) + 1;
        }
        newInt2 = ranInt;
        choice1.setText(content[ranInt]);
        if (ranInt == 1)
            correctChoice = 1;
        while((ranInt == newInt1) || (ranInt == newInt2)){
            ranInt = random.nextInt(3) + 1;
        }
        choice2.setText(content[ranInt]);
        if (ranInt == 1)
            correctChoice = 2;
    }

    public void submitButton(View v) {
        boolean choiceIsCorrect = false;
        switch (correctChoice) {
            case 0:
                if (choice0.isChecked()) {
                    choiceIsCorrect = true;
                }
                break;
            case 1:
                if (choice1.isChecked()) {
                    choiceIsCorrect = true;
                }
                break;
            case 2:
                if (choice2.isChecked()) {
                    choiceIsCorrect = true;
                }
                break;
        }
        if (choiceIsCorrect) {
            tr.testIsCorrect();
            dbManager.setWordLearned(testWord);
        }
        if(totalQ == 0){
            Statistics stats = MainActivity.profile.getStats();
            stats.addTestsTaken(tr.getTestsTaken());
            stats.addWordsLearned(tr.getNumCorrect());
            MainActivity.profile.setPreferences(this);
            tr.setGrade();
            startActivity(new Intent(TestActivity.this, ResultsActivity.class));
            this.finish();
        } else {
            setTestQuestion();
            totalQ -= 1;
        }
    }
}
