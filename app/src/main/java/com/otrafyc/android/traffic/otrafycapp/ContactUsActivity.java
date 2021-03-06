package com.otrafyc.android.traffic.otrafycapp;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {


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
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         setContentView(R.layout.activity_contact_us);



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
        pairs[0] = new Pair<View, String>(suggestionsImg,"contactUs_to_suggestions_img_transition");
        pairs[1] = new Pair<View, String>(suggestionsHead,"contactUs_to_suggestions_title_transition");
        pairs[2] = new Pair<View, String>(suggestionsSubText,"contactUs_to_suggestions_form_transition");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ContactUsActivity.this,pairs);


            startActivity(suggestionsIntent, options.toBundle());
        }else {

            startActivity(suggestionsIntent);

        }






    }

    public void openFeedbackPage(View view) {
        Intent feedbackIntent = new Intent(ContactUsActivity.this, FeedbackActivity.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(feedbackImg,"contactUs_to_feedback_img_transition");
        pairs[1] = new Pair<View, String>(feedbackHead,"contactUs_to_feedback_title_transition");
        pairs[2] = new Pair<View, String>(feedbackSubText,"contactUs_to_feedback_form_transition");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ContactUsActivity.this,pairs);


            startActivity(feedbackIntent, options.toBundle());
        }else {

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
}