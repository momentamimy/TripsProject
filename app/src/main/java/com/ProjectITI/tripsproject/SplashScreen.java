package com.ProjectITI.tripsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appolica.flubber.Flubber;

public class SplashScreen extends AppCompatActivity {

    RelativeLayout splashRelativeLayout;
    ImageView logoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoImageView = findViewById(R.id.SplashLogo);
        splashRelativeLayout = findViewById(R.id.SplashLayout);

        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_UP)
                .repeatCount(0)
                .duration(1800)
                .createFor(logoImageView)
                .start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), LoginSignup_Activity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this
                            ,Pair.create((View)splashRelativeLayout,splashRelativeLayout.getTransitionName())
                            ,Pair.create((View)logoImageView,logoImageView.getTransitionName()));

                    startActivity(intent,optionsCompat.toBundle());
                    finish();
                }
                else
                {
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
