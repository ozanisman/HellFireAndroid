package com.wizardstudios.hellfire;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.madx.updatechecker.lib.UpdateRunnable;


public class HellFireMain extends Activity {

    private MediaPlayer music;
    private HellFireGame game;
    private HellFireMain main = this;
    private int STORAGE_PERMISSION_CODE = 1;
    private boolean promptGiven = false;
    private boolean answeredPrompt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (ContextCompat.checkSelfPermission(HellFireMain.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HellFireMain.this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
            } else {
                promptGiven = true;
                requestStoragePermission();
            }
        }

        music = MediaPlayer.create(HellFireMain.this, R.raw.backgroundmusic);
        restartMusic();
        music.setLooping(true);

        game = new HellFireGame(this, this);

        setContentView(game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
    }

    protected void onResume() {
        super.onResume();
        playMusic();
    }

    public void playMusic() {
        music.start();
    }

    public void stopMusic() {
        music.stop();
    }

    public void restartMusic() {
        music.seekTo(0);
        music.start();
    }

    public MediaPlayer getMusic() {
        return music;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels + getNavBarHeight(this);
    }

    public boolean checkPermission(String permission) {
        return (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Writing to external storage is used to save player progress.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HellFireMain.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            dialog.dismiss();
                        }
                    })
                    /*.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(main, "Permission Denied: Progress Will Be Lost Upon Exit", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }) */
                    .create().show();

        } else {
            //ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Writing to external storage is used to save player progress.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HellFireMain.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(main, "Permission Denied: Progress Will Be Lost Upon Exit", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Permission Denied: Progress Will Be Lost Upon Exit", Toast.LENGTH_LONG).show();
            }

        }
        answeredPrompt = true;
    }

    public int getNavBarHeight(Context c) {
        int result = 0;
        boolean hasMenuKey = ViewConfiguration.get(c).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if(!hasMenuKey && !hasBackKey) {
            //The device has a navigation bar
            Resources resources = c.getResources();

            int orientation = resources.getConfiguration().orientation;
            int resourceId;
            if (isTablet(c)){
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
            }  else {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
            }

            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }


    private boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void forceUpdateTest() {
            /* Use this if an update check is explicitly requested by a user action */
            new UpdateRunnable(this, new Handler()).force(true).start();
    }

    public boolean getAnsweredPrompt() {
        return answeredPrompt;
    }

    public boolean getPromptGiven() {
        return promptGiven;
    }


}
