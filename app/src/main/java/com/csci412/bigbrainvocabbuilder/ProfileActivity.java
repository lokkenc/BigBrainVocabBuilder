package com.csci412.bigbrainvocabbuilder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.profile = MainActivity.profile;

    }

    public void onStart() {
        super.onStart();
        updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.edit_profile:
                Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
                this.startActivity(editProfileIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateView() {
        Statistics stats = profile.getStats();
        ImageView profilePicture = findViewById(R.id.profile_picture1);
        TextView firstName = findViewById(R.id.first_name);
        TextView lastName = findViewById(R.id.last_name);
        TextView wordsLearned = findViewById(R.id.words_learned_amount);
        TextView wordsLeft = findViewById(R.id.words_left_amount);
        TextView testsTaken = findViewById(R.id.tests_taken);
        TextView gamesCompleted = findViewById(R.id.games_completed);

        profilePicture.setImageDrawable(profile.getProfilePicture());
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        wordsLearned.setText("" + stats.getWordsLearned());
        wordsLeft.setText("" + stats.getWordsLeft());
        testsTaken.setText("" + stats.getTestsTaken());
        gamesCompleted.setText("" + stats.getGamesCompleted());

    }

}
