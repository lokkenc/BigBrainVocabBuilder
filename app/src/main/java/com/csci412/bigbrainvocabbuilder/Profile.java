package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import androidx.preference.PreferenceManager;

import java.io.File;

public class Profile {
    private static final String PREFERENCE_FIRST_NAME = "firstName";
    private static final String PREFERENCE_LAST_NAME = "lastName";
    private static final String PREFERENCE_IMAGE_PATH = "imagePath";
    private static final String PREFERENCE_STATS_WORDS_LEARNED = "wordsLearned";
    private static final String PREFERENCE_STATS_WORDS_LEFT = "wordsLeft";
    private static final String PREFERENCE_STATS_TESTS_TAKEN = "testsTaken";
    private static final String PREFERENCE_STATS_GAMES_COMPLETED = "gamesCompleted";


    private String firstName;
    private String lastName;
    private String imagePath;
    private Statistics stats;

    public Profile(int wordCount, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        setFirstName(pref.getString(PREFERENCE_FIRST_NAME, ""));
        setLastName(pref.getString(PREFERENCE_LAST_NAME, ""));
        setImagePath(pref.getString(PREFERENCE_IMAGE_PATH, ""));
        int wordsLearned = pref.getInt(PREFERENCE_STATS_WORDS_LEARNED, 0);
        int wordsLeft = pref.getInt(PREFERENCE_STATS_WORDS_LEFT, wordCount);
        int testsTaken = pref.getInt(PREFERENCE_STATS_TESTS_TAKEN, 0);
        int gamesCompleted = pref.getInt(PREFERENCE_STATS_GAMES_COMPLETED, 0);

        stats = new Statistics(wordsLearned, wordsLeft, testsTaken, gamesCompleted);
    }

    public void setPreferences(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(PREFERENCE_FIRST_NAME, this.firstName);
        editor.putString(PREFERENCE_LAST_NAME, this.lastName);
        editor.putString(PREFERENCE_IMAGE_PATH, this.imagePath);
        editor.putInt(PREFERENCE_STATS_WORDS_LEARNED, stats.getWordsLearned());
        editor.putInt(PREFERENCE_STATS_WORDS_LEFT, stats.getWordsLeft());
        editor.putInt(PREFERENCE_STATS_TESTS_TAKEN, stats.getTestsTaken());
        editor.putInt(PREFERENCE_STATS_GAMES_COMPLETED, stats.getGamesCompleted());
        editor.commit();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Bitmap getProfilePicture() {
        if (imagePath == "") {
            return null;
        }
        File imageFile = new File(imagePath);
        Bitmap profilePic = null;
        if (imageFile.exists()) {
            profilePic = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }
        return profilePic;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }
}
