package com.otrafyc.android.traffic.otrafycapp;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.sufficientlysecure.htmltextview.OnClickATagListener;

public class TermsActivity extends AppCompatActivity {


    AdView mAdView;
    TextView contactUsText;
    HtmlTextView GooglePlayServicesLink, GoogleAnalyticsFirebaseLink, FirebaseCrashlyticsLink,GoogleAdmobLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_terms);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        contactUsText = findViewById(R.id.contactUsText);

        GooglePlayServicesLink = findViewById(R.id.google_play_services);
        GoogleAnalyticsFirebaseLink = findViewById(R.id.google_analytics_for_firebase);
        FirebaseCrashlyticsLink = findViewById(R.id.firebase_crashlytics);
        GoogleAdmobLink = findViewById(R.id.google_admob);



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




        GoogleAdmobLink.setHtml("<font color='blue'><a href='https://policies.google.com/privacy?hl=en&gl=US'>Google Admob </a></font>");

        GoogleAdmobLink.setOnClickATagListener(new OnClickATagListener() {
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
        Intent contactUsIntent = new Intent(TermsActivity.this, ContactUsActivity.class);

        Pair[] pairs1 = new Pair[1];
        pairs1[0] = new Pair<View, String>(contactUsText, "privacy_and_terms_to_Activities_transition");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TermsActivity.this, pairs1);


            startActivity(contactUsIntent, options.toBundle());
        } else {

            startActivity(contactUsIntent);

        }
    }

    public void openGooglePrivacy(View view) {
        try {
            Intent googlePrivacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy"));
            startActivity(googlePrivacyIntent);
        } catch (ActivityNotFoundException e) {

            Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(playStore);
        }

    }

    public void openGoogleAnalyticsPrivacy(View view) {
        try {
            Intent googleAnalyticsPrivacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebase.google.com/policies/analytics"));
            startActivity(googleAnalyticsPrivacyIntent);
        } catch (ActivityNotFoundException e) {

            Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(playStore);
        }
    }

    public void openGoogleCrashlyticsPrivacy(View view) {

        try {
            Intent googleCrashlyticsPrivacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebase.google.com/support/privacy/"));
            startActivity(googleCrashlyticsPrivacyIntent);
        } catch (ActivityNotFoundException e) {

            Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(playStore);
        }
    }

}