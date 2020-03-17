package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordSearchActivity extends AppCompatActivity {
    WordSearch wordSearch;
    DatabaseManager db;
    Button[][] buttons;
    TextView[] wordViews;
    GridLayout gridLayout;
    int wordCount;
    int rowCount;
    int columnCount;
    int endCoor1[];
    int endCoor2[];
    Boolean isButtonHighlighted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGuiByCode();
        setContent();
    }

    public void buildGuiByCode() {
        int w;
        //Getting width of screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        //Getting values based on difficulty
        switch (PlayActivity.difficultyFlag) {
            case 2:
                wordCount = 4;
                rowCount = columnCount = 8;
                break;
            case 3:
                wordCount = 4;
                rowCount = columnCount = 9;
                break;
            case 4:
                wordCount = 5;
                rowCount = columnCount = 9;
                break;
            case 5:
                wordCount = 5;
                rowCount = columnCount = 10;
                break;
            default:
                wordCount = 3;
                rowCount = columnCount = 8;
        }
        w = size.x / rowCount;

        //Create grid layout
        gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(columnCount);
        gridLayout.setRowCount(rowCount + wordCount + 1);

        //Creating buttons and adding to grid
        buttons = new Button[rowCount][columnCount];
        ButtonHandler bh = new ButtonHandler();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++){
                buttons[i][j] = new Button(this);
                buttons[i][j].setTextSize((int) (w * .1));
                buttons[i][j].setOnClickListener(bh);
                buttons[i][j].setBackgroundResource(android.R.drawable.btn_default);

                gridLayout.addView(buttons[i][j], w, w);
            }
        }

        //Setting word text views as last row
        wordViews = new TextView[wordCount];
        for (int i = 0; i < wordCount; i++) {
            wordViews[i] = new TextView(this);
            GridLayout.Spec rowSpec = GridLayout.spec(rowCount + i, 1);
            GridLayout.Spec columnSpec = GridLayout.spec(0, columnCount);
            GridLayout.LayoutParams lpStatus
                    = new GridLayout.LayoutParams(rowSpec, columnSpec);
            wordViews[i].setLayoutParams(lpStatus);

            wordViews[i].setWidth(columnCount * w);
            wordViews[i].setHeight(w);
            wordViews[i].setGravity(Gravity.CENTER);
            wordViews[i].setTextSize((int) (w * .15));

            gridLayout.addView(wordViews[i]);

        }


        //Set background color
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.gold));
        //Setting gridLayout as View
        setContentView(gridLayout);
    }

    public void updateFirstClick(int row, int column) {
        isButtonHighlighted = true;
        endCoor1 = new int[] {row, column};

        for (int i = 0; i < columnCount; i++) {
            buttons[row][i].getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        }

        for (int i = 0; i < rowCount; i++) {
            buttons[i][column].getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        }

        //Disable buttons
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (i != row && j != column) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    public void wordFound(String word) {
        String reverse = new StringBuilder(word).reverse().toString();
        for (int i = 0; i < wordCount; i++) {
            if (wordViews[i].getText().equals(word) || wordViews[i].getText().equals(reverse)) {
                wordViews[i].setBackgroundColor(Color.BLUE);
                return;
            }
        }

    }
    public void updateSecondClick(int row, int column) {
        isButtonHighlighted = false;
        endCoor2 = new int[] {row, column};
        String word = wordSearch.getWord(endCoor1[0], endCoor1[1], endCoor2[0], endCoor2[1]);
        Log.w("WordSearchActivity", "Word Guessed is: " + word);
        if (wordSearch.isWordInList(word)) {
            //rows are the same
            wordFound(word);
            Toast.makeText(this, "Nice Job!", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
        }

        clearButtonColor(row, column);
        //Enable all buttons
        enableButtons(true);
    }

    public void enableButtons(boolean enabled) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                buttons[i][j].setEnabled(enabled);
            }
        }
    }

    public void clearButtonColor(int row, int column) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                buttons[i][j].getBackground().clearColorFilter();
            }
        }
    }
    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    if (v == buttons[i][j]) {
                        if (isButtonHighlighted) {
                            updateSecondClick(i, j);
                            checkGameOver();
                        }
                        else {
                            updateFirstClick(i, j);
                        }
                    }
                }
            }
        }
    }

    public void checkGameOver() {
        if (wordSearch.checkGameOver()) {
            Statistics stats = MainActivity.profile.getStats();
            stats.addGamesCompleted();
            MainActivity.profile.setPreferences(this);
            setContentView(R.layout.activity_word_search);
        }
    }

    public void setContent() {
        db = new DatabaseManager(this);
        ArrayList<String> words = new ArrayList<String>();
        int count = 0;
        while (words.size() < wordCount && count < 1000) {
            String word = db.getRandomWord(-1)[0];
            if (word.length() < rowCount) {
                words.add(word);
            }
            count++;
        }
        for (int i = 0; i < wordCount; i++) {
            wordViews[i].setText(words.get(i));
        }
        wordSearch = new WordSearch(rowCount,columnCount, words);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String s = "" + wordSearch.getChar(i,j);
                buttons[i][j].setText(s);
            }
        }

    }

    public void goBack(View v) {
        this.finish();
    }
}
