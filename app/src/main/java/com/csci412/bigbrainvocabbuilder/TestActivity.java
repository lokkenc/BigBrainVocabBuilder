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

    TestLogic tl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tl = new TestLogic(this);
        totalQ = (difficultyFlag * 5) - 1;
        setTestQuestion();
    }

    void setTestQuestion() {
        Random random = new Random();
        int ranInt = random.nextInt(3) + 1;
        int newInt1 = ranInt;
        int newInt2 = 0;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.testGroup);
        radioGroup.clearCheck();

        String[] content = tl.createQuestion();
        TextView wordView = (TextView)findViewById(R.id.testVocabWord);
        RadioButton choice0 = (RadioButton)findViewById(R.id.testChoice0);
        RadioButton choice1 = (RadioButton)findViewById(R.id.testChoice1);
        RadioButton choice2 = (RadioButton)findViewById(R.id.testChoice2);

        wordView.setText(content[0]);
        choice0.setText(content[ranInt]);
        while(ranInt == newInt1){
            ranInt = random.nextInt(3) + 1;
        }
        newInt2 = ranInt;
        choice1.setText(content[ranInt]);
        while((ranInt == newInt1) || (ranInt == newInt2)){
            ranInt = random.nextInt(3) + 1;
        }
        choice2.setText(content[ranInt]);
    }

    public void submitButton(View v) {
        if(totalQ == 0){
            startActivity(new Intent(TestActivity.this, ResultsActivity.class));
        } else{
            setTestQuestion();
            totalQ -= 1;
        }
    }
}
