package com.revis2e.exonotifs;

import android.app.PendingIntent;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

public class DescriptionAdapter implements
        PlayerNotificationManager.MediaDescriptionAdapter {

    @Override
    public String getCurrentContentTitle(Player player) {
        int window = player.getCurrentWindowIndex();
        return "Song";
    }

    @Nullable
    @Override
    public String getCurrentContentText(Player player) {
        int window = player.getCurrentWindowIndex();
        return "Description";
    }

    @Nullable
    @Override
    public Bitmap getCurrentLargeIcon(Player player,
                                      PlayerNotificationManager.BitmapCallback callback) {
        int window = player.getCurrentWindowIndex();
//        Bitmap largeIcon = getLargeIcon(window);
//        if (largeIcon == null && getLargeIconUri(window) != null) {
//            // load bitmap async
//            loadBitmap(getLargeIconUri(window), callback);
//            return getPlaceholderBitmap();
//        }
        //return largeIcon;
        return null;
    }

    @Nullable
    @Override
    public PendingIntent createCurrentContentIntent(Player player) {
        int window = player.getCurrentWindowIndex();
        //return createPendingIntent(window);
        return null;
    }
}