package com.example.mahantesh.avatarupload;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    public static final String GAME_PREFERENCES_AVATAR = "Avatar";
    static final int TAKE_AVATAR_CAMERA_REQUEST = 1;
    public static final int TAKE_AVATAR_GALLERY_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri path = Uri.parse( "android.resource://com.androidbook.mahantesh.avatarupload/drawable/avatar");
        ImageButton avatarButton = (ImageButton) findViewById(R.id.imagebuttn);
        avatarButton.setImageURI(path);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // TODO: Launch the Camera and Save the Photo as the Avatar
                Intent pictureIntent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(pictureIntent, TAKE_AVATAR_CAMERA_REQUEST);
            }
        });
        avatarButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { // TODO: Launch Image Picker and Save Image as Avatar return false;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK);
                pickPhoto.setType("image/*");
                startActivityForResult(pickPhoto, TAKE_AVATAR_GALLERY_REQUEST);
                return false;
            }
        });



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case TAKE_AVATAR_CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_CANCELED) {
                    // Avatar camera mode was canceled.
                } else if (resultCode == Activity.RESULT_OK) {
                    // TODO: HANDLE PHOTO TAKEN
                    Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
                    setAvatar(cameraPic);
                }
                break;
            case TAKE_AVATAR_GALLERY_REQUEST:
                if (resultCode == Activity.RESULT_CANCELED) {
                    // Avatar gallery request mode was canceled.
                }
                else {
                if (resultCode == Activity.RESULT_OK) {
                    //  TODO: HANDLE IMAGE CHOSEN

                    Uri photoUri = data.getData();
                    Bitmap galleryPic;
                    try{
                        galleryPic = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                        setAvatar(galleryPic);
                    } catch(IOException ioEx) {
                        ioEx.printStackTrace(); // or what ever you want to do with it
                    }

                }
                }break;
        } }

private void setAvatar(Bitmap avatar){
    String strAvatarFilename = "avatar.png";
    try {
        avatar.compress(Bitmap.CompressFormat.JPEG, 100,openFileOutput(strAvatarFilename, MODE_PRIVATE));
    } catch (FileNotFoundException e) {
        e.printStackTrace();

    }
    Uri imageUri = Uri.fromFile(new File(getFilesDir(), strAvatarFilename));
    ImageButton avatarButton = (ImageButton) findViewById(R.id.imagebuttn);
    avatarButton.setImageURI(imageUri);

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
