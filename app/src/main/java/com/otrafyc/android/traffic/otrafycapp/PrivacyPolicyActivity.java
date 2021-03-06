package com.otrafyc.android.traffic.otrafycapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.sufficientlysecure.htmltextview.OnClickATagListener;

public class PrivacyPolicyActivity extends AppCompatActivity {

    TextView contactUsText;
    HtmlTextView GooglePlayServicesLink, GoogleAnalyticsFirebaseLink, FirebaseCrashlyticsLink;

   /* @Override
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
        setContentView(R.layout.activity_privacy_policy);


        contactUsText = findViewById(R.id.contactUsText);

        GooglePlayServicesLink = findViewById(R.id.google_play_services);
        GoogleAnalyticsFirebaseLink = findViewById(R.id.google_analytics_for_firebase);
        FirebaseCrashlyticsLink = findViewById(R.id.firebase_crashlytics);


        GooglePlayServicesLink.setHtml("<font color='blue'><a href='https://policies.google.com/privacy'>Google Play Services</a></font>");

        GooglePlayServicesLink.setOnClickATagListener(new OnClickATagListener() {
            @Override
            public boolean onClick(View widget, String spannedText, @Nullable String href) {
                return false;
            }
        });

        GoogleAnalyticsFirebaseLink.setHtml("<font color='blue'><a href='https://firebase.google.com/policies/analytics'>Google Analytics for Firebase</a></font>");

        GoogleAnalyticsFirebaseLink.setOnClickATagListener(new OnClickATagListener() {
            @Override
            public boolean onClick(View widget, String spannedText, @Nullable String href) {
                return false;
            }
        });

        FirebaseCrashlyticsLink.setHtml("<font color='blue'><a href='https://firebase.google.com/support/privacy/'>Firebase Crashlytics</a></font>");

        FirebaseCrashlyticsLink.setOnClickATagListener(new OnClickATagListener() {
            @Override
            public boolean onClick(View widget, String spannedText, @Nullable String href) {
                return false;
            }
        });
    }

    public void OnBackButtonClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void openContactUsActivity(View view) {
        Intent contactUsIntent = new Intent(PrivacyPolicyActivity.this, ContactUsActivity.class);

        Pair[] pairs1 = new Pair[1];
        pairs1[0] = new Pair<View, String>(contactUsText, "privacy_and_terms_to_Activities_transition");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PrivacyPolicyActivity.this, pairs1);


            startActivity(contactUsIntent, options.toBundle());
        } else {

            startActivity(contactUsIntent);

        }

    }

    /*public void openGooglePrivacy(View view) {
       try {
           Intent googlePrivacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy"));
           startActivity(googlePrivacyIntent);
       }catch (ActivityNotFoundException e) {

           Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
           startActivity(playStore);
       }

    }
*/
   /* public void openGoogleAnalyticsPrivacy(View view) {
        try {
            Intent googleAnalyticsPrivacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebase.google.com/policies/analytics"));
            startActivity(googleAnalyticsPrivacyIntent);
        }catch (ActivityNotFoundException e) {

            Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(playStore);
        }
    }*/

    /*public void openGoogleCrashlyticsPrivacy(View view) {

        try {
            Intent googleCrashlyticsPrivacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebase.google.com/support/privacy/"));
            startActivity(googleCrashlyticsPrivacyIntent);
        }catch (ActivityNotFoundException e) {

            Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(playStore);
        }
    }*/

}