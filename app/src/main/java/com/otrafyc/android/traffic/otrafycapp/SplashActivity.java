package com.otrafyc.android.traffic.otrafycapp;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SplashActivity extends AppCompatActivity {

    //call views
    TextView OTrafyc, slogan, welcome;
    ImageView appIcon;

    //call animations
    Animation topAnim, bottomAnim, welcomeAnim, IconAppearAnim;
    //LottieAnimationView trafficLottie;
    ConstraintLayout splashConstraintLayout;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Typographica-Blp5.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());*/

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Typographica-Blp5.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
                .build());



            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //hiding status bar in activity

          setContentView(R.layout.activity_splash);

        // lock screen orientation to portrait ...works when placed after setContentView
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        //splashConstraintLayout = findViewById(R.id.splashConstraintLayout);
        splashConstraintLayout = findViewById(R.id.splashConstraintLayout);


        OTrafyc = (TextView) findViewById(R.id.trafycView);
       // slogan = (TextView) findViewById(R.id.sloganView);
       // welcome = (TextView) findViewById(R.id.welcomeView);
        appIcon = findViewById(R.id.splash_icon);
//
       // trafficLottie = (LottieAnimationView) findViewById(R.id.animation_view);





        //init animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        welcomeAnim = AnimationUtils.loadAnimation(this, R.anim.welcome_animation);
        IconAppearAnim = AnimationUtils.loadAnimation(this, R.anim.splashtraffic_appear_animation);


        //set animations to views
        OTrafyc.setAnimation(topAnim);
        //slogan.setAnimation(bottomAnim);
       // welcome.setAnimation(welcomeAnim);
        //trafficLottie.setAnimation(bottomAnim);
        appIcon.setAnimation(bottomAnim);

        //set delay of activity before launching next activity with intent
        //this is a global variable and can be converted to a local variable by using just define it as
        // int SPLASH_TIME = 7000; before the handler
        int SPLASH_TIME = 4000;
        new  Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                    Intent intent = new Intent(SplashActivity.this, MapsActivity.class);

                    Pair[] pairs1 = new Pair[1];
                    pairs1[0] = new Pair<View, String>(OTrafyc, "splash_screen_to_maps_transition");


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs1);


                        startActivity(intent, options.toBundle());
                        //means you dnt come back to this activity wen u press back at the subsequent activity
                        finish();

                    } else {

                        startActivity(intent);
                        finish();

                    }


                finish();


            }
        }, SPLASH_TIME);



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
