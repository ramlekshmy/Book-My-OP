package com.carehack.ashikvarma.patientportal.Activities;


import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carehack.ashikvarma.patientportal.R;
import com.google.firebase.auth.FirebaseAuth;

import static com.carehack.ashikvarma.patientportal.Utilities.Constants.SPLASH_SCREEN_DELAY;


public class SplashScreenActivity extends BaseActivity {

    FirebaseAuth mAuth;
    Handler mHandler = new Handler();
    Runnable mSplashScreenRunnable = new Runnable() {
        @Override
        public void run() {
                if (mAuth.getCurrentUser() != null) {
                    //that means user is already logged in
                    //so close this activity
                    finish();
                    //and open profile activity
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                }else {
                    finish();
                    startActivity(new Intent(getApplicationContext(), PhoneAuthenticationActivity.class));
                }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        mAuth = FirebaseAuth.getInstance();
        StartAnimations();
        mHandler.postDelayed(mSplashScreenRunnable,SPLASH_SCREEN_DELAY);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.clearAnimation();
        layout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView logo = (ImageView) findViewById(R.id.splash);
        logo.clearAnimation();
        logo.startAnimation(anim);
        TextView title = (TextView) findViewById(R.id.titletext);
        title.clearAnimation();
        title.startAnimation(anim);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
}