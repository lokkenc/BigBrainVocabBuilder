package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class WordActivity extends AppCompatActivity {

    public static int difficultyFlag;

    WordLogic Ll = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        Ll = new WordLogic(this);
        setLearnWord();
    }
    void setLearnWord() {
        String[] content = Ll.createLearnDef();
        TextView wordView = (TextView)findViewById(R.id.learnWord);
        TextView def = (TextView)findViewById(R.id.learnDef);
        wordView.setText(content[0]);
        def.setText(content[1]);
    }
    public void nextWordButton(View v) {
        setLearnWord();
    }
}
