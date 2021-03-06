package com.otrafyc.android.traffic.otrafycapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout termsRel, privacyRel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_settings);

        privacyRel = findViewById(R.id.privacy_rel);
        termsRel = findViewById(R.id.terms_rel);



    }

    public void OnBackButtonClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void locationAccuracy(View view) {

        Toast.makeText(SettingsActivity.this, "Your location setting is already optimized", Toast.LENGTH_SHORT).show();
    }

    public void openPrivacyPolicyPage(View view) {

        Intent PrivacyIntent = new Intent(SettingsActivity.this, PrivacyPolicyActivity.class);

        // startActivity(new Intent(MapsActivity.this, ContactUsActivity.class));

        Pair[] pairs1 = new Pair[1];
        pairs1[0] = new Pair<View, String>(privacyRel, "menu_to_Activities_transition");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SettingsActivity.this, pairs1);


            startActivity(PrivacyIntent, options.toBundle());
        } else {

            startActivity(PrivacyIntent);

        }

    }

    public void openTermsPage(View view) {

        Intent TermsIntent = new Intent(SettingsActivity.this, TermsActivity.class);

        // startActivity(new Intent(MapsActivity.this, ContactUsActivity.class));

        Pair[] pairs2 = new Pair[1];
        pairs2[0] = new Pair<View, String>(termsRel, "menu_to_Activities_transition");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SettingsActivity.this, pairs2);


            startActivity(TermsIntent, options.toBundle());
        } else {

            startActivity(TermsIntent);

        }


    }

    public void openLocationAccessSetting(View view) {
        Intent openLocationSettingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(openLocationSettingsIntent);
    }
}