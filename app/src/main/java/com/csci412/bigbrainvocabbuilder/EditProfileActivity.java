package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PHOTO_REQUEST = 1;
    private ImageView imageView;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void goBack(View v) {
        updateProfile();
        this.finish();
    }

    public void takePicture(View v) {
        imageView = (ImageView) findViewById(R.id.profile_picture2);

        PackageManager manager = this.getPackageManager();
        if (manager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent takePicture
                    = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, PHOTO_REQUEST);
        } else {
            Toast.makeText(this, "Your device does not have a camera", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    public void updateProfile() {
        Profile profile = MainActivity.profile;
        EditText firstNameET = findViewById(R.id.first_name_input);
        EditText lastNameET = findViewById(R.id.last_name_input);
        ImageView profilePicture = findViewById(R.id.profile_picture2);

        if (!(firstNameET.getText().toString().equals(""))) {
            profile.setFirstName(firstNameET.getText().toString());
        }

        if (!(lastNameET.getText().toString().equals(""))) {
            profile.setLastName(lastNameET.getText().toString());
        }

        profile.setProfilePicture(profilePicture.getDrawable());

        profile.setPreferences(this);
    }
}
