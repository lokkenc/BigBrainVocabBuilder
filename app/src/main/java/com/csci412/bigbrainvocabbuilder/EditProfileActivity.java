package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PHOTO_REQUEST = 1;
    private ImageView imageView;
    private Bitmap bitmap;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        updateView();
    }

    public void updateView() {
        ImageView profilePic = findViewById(R.id.profile_picture2);
        Bitmap map = MainActivity.profile.getProfilePicture();
        if (map == null) {
            profilePic.setImageResource(R.drawable.base_profile_pic);
        } else {
            profilePic.setImageBitmap(MainActivity.profile.getProfilePicture());
        }

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
            savePicture();
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

        if (file != null && file.exists()) {
            profile.setImagePath(file.getAbsolutePath());
        }


        profile.setPreferences(this);
    }

    public File writeToExternalStorage(Bitmap bitmap) throws IOException {
        String storageState = Environment.getExternalStorageState();
        File file = null;

        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File dir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            Date dateToday = new Date();
            long ms = SystemClock.elapsedRealtime();
            String filename = "/" + dateToday + "_" + ms + ".png";

            file = new File(dir + filename);
            long freeSpace = dir.getFreeSpace();
            int bytesNeeded = bitmap.getByteCount();
            if (bytesNeeded * 1.5 < freeSpace) {
                FileOutputStream fos = new FileOutputStream(file);

                boolean result = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                if (result)
                    return file;
                else
                    throw new IOException("Problem compressing the Bitmap to the output stream");

            }
        }
        return file;

    }

    public void savePicture() {
        try {
            file = writeToExternalStorage(bitmap);
            Toast.makeText(this, "Picture saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
