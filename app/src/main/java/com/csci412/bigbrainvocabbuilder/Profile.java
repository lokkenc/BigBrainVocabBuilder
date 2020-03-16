package com.csci412.bigbrainvocabbuilder;

import android.graphics.drawable.Drawable;

public class Profile {
    private String firstName;
    private String lastName;
    private Drawable profilePicture;

    private Statistics stats;

    public Profile(int wordCount, Drawable image) {
        firstName = "";
        lastName = "";
        profilePicture = image;
        stats = new Statistics(wordCount);
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
