package com.otrafyc.android.traffic.otrafycapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.otrafyc.android.traffic.otrafycapp.MyCommon_Package.SliderAdapter;

public class OnBoardingActivity extends AppCompatActivity {

    LinearLayout dotsLayout;
    Button SignInButton, SignUpButton, skipButton;
    MaterialCardView nextCardView;
    ViewPager viewPager;

    View viewBlack, viewWhite;

    SliderAdapter sliderAdapter;

    TextView[] dots;
    int currentPosition;
    Animation slideRightAnimation, slideLeftAnimation, scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.onboarding_activity_layout);

        dotsLayout = findViewById(R.id.dots_layout);
        SignInButton = findViewById(R.id.sign_in);
        SignUpButton = findViewById(R.id.sign_up);
        skipButton = findViewById(R.id.skip);
        nextCardView = findViewById(R.id.next_cardView);
        viewBlack = findViewById(R.id.view_black);
        viewWhite = findViewById(R.id.view_white);




        sliderAdapter = new SliderAdapter(this);
        viewPager = findViewById(R.id.view_pager);


        viewPager.setAdapter(sliderAdapter);


        addDots(0);


        viewPager.addOnPageChangeListener(changeListener);


    }


    private void addDots(int position) {

        dots = new TextView[4];


        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226; "));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(R.color.darkBlueBlackColor));


            dotsLayout.addView(dots[i]);

        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.blue1));
        }
    }


    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            currentPosition = position;

            if (position == 0) {
                skipButton.setVisibility(View.VISIBLE);
                SignInButton.setVisibility(View.INVISIBLE);
                SignUpButton.setVisibility(View.INVISIBLE);
                viewBlack.setVisibility(View.INVISIBLE);
                viewWhite.setVisibility(View.INVISIBLE);

                nextCardView.setClickable(true);


            }else if (position == 1) {
                skipButton.setVisibility(View.VISIBLE);
                SignInButton.setVisibility(View.INVISIBLE);
                SignUpButton.setVisibility(View.INVISIBLE);
                viewBlack.setVisibility(View.INVISIBLE);
                viewWhite.setVisibility(View.INVISIBLE);

                nextCardView.setClickable(true);

            }else  if (position == 2) {
                skipButton.setVisibility(View.VISIBLE);
                SignInButton.setVisibility(View.INVISIBLE);
                SignUpButton.setVisibility(View.INVISIBLE);
                viewBlack.setVisibility(View.INVISIBLE);
                viewWhite.setVisibility(View.INVISIBLE);

                nextCardView.setClickable(true);

            }else {

                skipButton.setVisibility(View.INVISIBLE);

                slideLeftAnimation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_from_left);
                slideRightAnimation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_right_anim);
                scaleAnimation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.dialog_show_anim);

                SignInButton.setAnimation(slideRightAnimation);
                SignUpButton.setAnimation(slideLeftAnimation);
                viewBlack.setAnimation(slideRightAnimation);
                viewWhite.setAnimation(slideLeftAnimation);

                SignInButton.setVisibility(View.VISIBLE);
                SignUpButton.setVisibility(View.VISIBLE);
                viewBlack.setVisibility(View.VISIBLE);
                viewWhite.setVisibility(View.VISIBLE);

                nextCardView.setClickable(false);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void skip(View view) {

        if (currentPosition == 0) {

            viewPager.setCurrentItem(currentPosition + 3);
        }else  if (currentPosition == 1) {
            viewPager.setCurrentItem(currentPosition + 2);
        }else  if (currentPosition == 2) {
            viewPager.setCurrentItem(currentPosition + 1);
        }else {

        }


        /*startActivity(new Intent(OnBoardingActivity.this, MapsActivity.class));
        finish();*/
    }

    public void next(View view) {

        viewPager.setCurrentItem(currentPosition + 1);
    }
}