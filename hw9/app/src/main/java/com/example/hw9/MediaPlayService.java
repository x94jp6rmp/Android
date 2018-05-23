package com.example.hw9;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;

public class MediaPlayService extends Service {

    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri uri = Uri.fromFile(new File(Environment.getDataDirectory().getPath() + "/song.mp3"));
        mediaPlayer = MediaPlayer.create(this,uri);
        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }
}
