package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import androidx.preference.PreferenceManager;

public class Profile {
    private static final String PREFERENCE_FIRST_NAME = "firstName";
    private static final String PREFERENCE_LAST_NAME = "lastName";
    private static final String PREFERENCE_STATS_WORDS_LEARNED = "wordsLearned";
    private static final String PREFERENCE_STATS_WORDS_LEFT = "wordsLeft";
    private static final String PREFERENCE_STATS_TESTS_TAKEN = "testsTaken";
    private static final String PREFERENCE_STATS_GAMES_COMPLETED = "gamesCompleted";


    private String firstName;
    private String lastName;
    private Drawable profilePicture;
    private Statistics stats;

    public Profile(int wordCount, Drawable image, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        setFirstName(pref.getString(PREFERENCE_FIRST_NAME, ""));
        setLastName(pref.getString(PREFERENCE_LAST_NAME, ""));
        int wordsLearned = pref.getInt(PREFERENCE_STATS_WORDS_LEARNED, 0);
        int wordsLeft = pref.getInt(PREFERENCE_STATS_WORDS_LEFT, wordCount);
        int testsTaken = pref.getInt(PREFERENCE_STATS_TESTS_TAKEN, 0);
        int gamesCompleted = pref.getInt(PREFERENCE_STATS_GAMES_COMPLETED, 0);

        firstName = "";
        lastName = "";
        profilePicture = image;
        stats = new Statistics(wordsLearned, wordsLeft, testsTaken, gamesCompleted);
    }

    public void setPreferences(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(PREFERENCE_FIRST_NAME, this.firstName);
        editor.putString(PREFERENCE_LAST_NAME, this.lastName);
        editor.putInt(PREFERENCE_STATS_WORDS_LEARNED, stats.getWordsLearned());
        editor.putInt(PREFERENCE_STATS_WORDS_LEFT, stats.getWordsLeft());
        editor.putInt(PREFERENCE_STATS_TESTS_TAKEN, stats.getTestsTaken());
        editor.putInt(PREFERENCE_STATS_GAMES_COMPLETED, stats.getGamesCompleted());
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

    public Drawable getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Drawable profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }
}
