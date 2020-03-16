package com.ProjectITI.tripsproject;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.appolica.flubber.Flubber;

public class ShowAlertDialog extends AppCompatActivity {

    Ringtone r;
    MediaPlayer r2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ring(this);
        MyCustomDialog(this);
    }

    Dialog tripDialog;

    public void MyCustomDialog(final Context context) {

        tripDialog = new Dialog(context);
        tripDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        tripDialog.setContentView(R.layout.alert_dialog);
        Window window = tripDialog.getWindow();
        window.setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        tripDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button cancelButton, snoozeButton, startButton;
        ImageView alarmImage = tripDialog.findViewById(R.id.AlarmImage);
        TextView tripName = tripDialog.findViewById(R.id.TripNameText);
        if (getIntent()!=null)
        {
            tripName.setText(getIntent().getStringExtra("name"));
        }

        cancelButton = tripDialog.findViewById(R.id.CancelButton);
        snoozeButton = tripDialog.findViewById(R.id.SnoozeButton);
        startButton = tripDialog.findViewById(R.id.StartButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.O) {
                    if (r2 != null) {
                        r2.stop();
                    }
                } else {
                    if (r != null) {
                        r.stop();
                    }
                }
            }
        });

        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationHelper notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                notificationHelper.getManager().notify(1, nb.build());
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Animator animator = Flubber.with()
                .animation(Flubber.AnimationPreset.MORPH)
                .repeatCount(0)
                .duration(1000)
                .createFor(alarmImage);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();


        tripDialog.show();
    }

    public void Ring(Context context) {
        final int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.O) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r2 = MediaPlayer.create(context, notification);
            r2.setLooping(true);
            r2.start();

        } else {
            Uri notification2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (notification2 == null) {
                // alert is null, using backup
                notification2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                // I can't see this ever being null (as always have a default notification)
                // but just incase
                if (notification2 == null) {
                    // alert backup is null, using 2nd backup
                    notification2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                }
            }
            r = RingtoneManager.getRingtone(context, notification2);
            r.play();
        }
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

}
