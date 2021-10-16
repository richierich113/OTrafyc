package com.otrafyc.android.traffic.otrafycapp;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.material.card.MaterialCardView;

public class ContactUsActivity extends AppCompatActivity {


    AdView mAdView;
    RelativeLayout feedbackLayout, suggestionsLayout;

    TextView feedbackSubText, feedbackHead, suggestionsSubText, suggestionsHead;
    ImageView feedbackImg, suggestionsImg;



    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/typographica_regular.otf")
        .setFontAttrId(R.attr.fontPath)
        .build());*/
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_contact_us);





        SharedPreferences sharedPreferences1 = getSharedPreferences("darkModePref", MODE_PRIVATE);
        final SharedPreferences.Editor editor1 = sharedPreferences1.edit();

        final boolean isDarkModeOn = sharedPreferences1.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


            //Toast.makeText(this, getResources().getString(R.string.YouSetNightMode), Toast.LENGTH_SHORT).show();


        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

          //  Toast.makeText(this, getResources().getString(R.string.YouSetDayMode), Toast.LENGTH_SHORT).show();


        }





        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        feedbackLayout = findViewById(R.id.feedback_rel);
        feedbackSubText = findViewById(R.id.feedback_subtext);
        feedbackHead = findViewById(R.id.feedback_head);
        feedbackImg = findViewById(R.id.feedback_img);


        suggestionsLayout = findViewById(R.id.suggestions_rel);
        suggestionsHead = findViewById(R.id.suggestions_head);
        suggestionsSubText = findViewById(R.id.suggestions_subtext);
        suggestionsImg = findViewById(R.id.suggestions_img);


    }

    public void openSuggestionsPage(View view) {
        Intent suggestionsIntent = new Intent(ContactUsActivity.this, SuggestionsActivity.class);


        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(suggestionsImg, "contactUs_to_suggestions_img_transition");
        pairs[1] = new Pair<View, String>(suggestionsHead, "contactUs_to_suggestions_title_transition");
        pairs[2] = new Pair<View, String>(suggestionsSubText, "contactUs_to_suggestions_form_transition");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ContactUsActivity.this, pairs);


            startActivity(suggestionsIntent, options.toBundle());
        } else {

            startActivity(suggestionsIntent);

        }


    }

    public void openFeedbackPage(View view) {
        Intent feedbackIntent = new Intent(ContactUsActivity.this, FeedbackActivity.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(feedbackImg, "contactUs_to_feedback_img_transition");
        pairs[1] = new Pair<View, String>(feedbackHead, "contactUs_to_feedback_title_transition");
        pairs[2] = new Pair<View, String>(feedbackSubText, "contactUs_to_feedback_form_transition");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ContactUsActivity.this, pairs);


            startActivity(feedbackIntent, options.toBundle());
        } else {

            startActivity(feedbackIntent);

        }


    }

    public void OnBackButtonClick(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openPlayStore(View view) {

        try {
            Intent rateAndReviewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())); // "com.android.chrome"
            startActivity(rateAndReviewIntent);

        } catch (ActivityNotFoundException e) {

            Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(playStore);

        }


    }

    public void openFacebook(View view) {



        try {
            Uri fbUri = Uri.parse("https://web.facebook.com/OnimTechnologies");
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, fbUri);
            facebookIntent.setPackage("com.facebook.katana");
            startActivity(facebookIntent);
              /*  if (TapNavigateWithWazeIntent.resolveActivity(getPackageManager()) != null) {

                    TapNavigateWithWazeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
            //}
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Facebook is not installed on your device... \nPlease Install Facebook to handle task", Toast.LENGTH_SHORT).show();
            Uri facebookIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.katana");
            Intent facebookIntent1 = new Intent(Intent.ACTION_VIEW, facebookIntentUri);

            facebookIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(facebookIntent1);


        }

    }
}