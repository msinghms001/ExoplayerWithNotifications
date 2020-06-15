package com.revis2e.exonotifs;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerActivity extends Activity implements Player.EventListener {
    SimpleExoPlayer player;
    ProgressiveMediaSource mediaSource;
    PlayerNotificationManager pnManager;
    PlayerView playerView;
    String playlink = "https://storage.googleapis.com/webfundamentals-assets/videos/chrome.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_main);
        playerView = findViewById(R.id.playerd_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel("MyNotes", "MyNotes", NotificationManager.IMPORTANCE_DEFAULT);
            getSystemService(NotificationManager.class).createNotificationChannel(ch);
        }
        pnManager = new PlayerNotificationManager(getApplicationContext(),
                "MyNotes",999,new DescriptionAdapter());


        player = new SimpleExoPlayer.Builder(getApplicationContext()).build();
        pnManager.setPlayer(player);
        playerView.setPlayer(player);
        DataSource.Factory dsf = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Project"));
        mediaSource = new ProgressiveMediaSource.Factory(dsf)
                .createMediaSource(Uri.parse(playlink));
        player.prepare(mediaSource);

        player.addListener(this);
        player.setPlayWhenReady(true);


    }


    private void releasePlayer() {
        if (player != null) {
            pnManager.setPlayer(null);
            player.release();
            player = null;
            mediaSource = null;
            //trackSelector = null;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }


    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        switch (playbackState) {
            case Player.STATE_BUFFERING:
                Toast.makeText(getApplicationContext(), "Buffering....", Toast.LENGTH_LONG).show();
            case Player.STATE_READY:
                Toast.makeText(getApplicationContext(), "Ready....", Toast.LENGTH_LONG).show();

            default:
                Toast.makeText(getApplicationContext(), "UnKnown.LINK=>" + playlink, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Toast.makeText(getApplicationContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
    }

}
