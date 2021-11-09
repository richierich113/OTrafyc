package com.otrafyc.android.traffic.otrafycapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.maps.model.MapStyleOptions;

public class SettingsActivity extends AppCompatActivity {


    AdView mAdView;
    RelativeLayout termsRel, privacyRel;
    Button themeButton;
    TextView theme_mode_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_settings);

        themeButton = findViewById(R.id.theme_button);
        theme_mode_txt = findViewById(R.id.theme_mode_txt);


        SharedPreferences sharedPreferences1 = getSharedPreferences("darkModePref", MODE_PRIVATE);
        final SharedPreferences.Editor editor1 = sharedPreferences1.edit();

        final boolean isDarkModeOn = sharedPreferences1.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            themeButton.setText(getResources().getString(R.string.DayMode));
            theme_mode_txt.setText(getResources().getString(R.string.YouSetNightMode));



            //Toast.makeText(this, getResources().getString(R.string.YouSetNightMode), Toast.LENGTH_SHORT).show();


        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            themeButton.setText(getResources().getString(R.string.NightMode));


            //Toast.makeText(this, getResources().getString(R.string.YouSetDayMode), Toast.LENGTH_SHORT).show();


        }


        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isDarkModeOn) {


                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor1.putBoolean("isDarkModeOn", false);
                    editor1.apply();
                    editor1.commit();


                    themeButton.setText(getResources().getString(R.string.NightMode));
                    theme_mode_txt.setText(getResources().getString(R.string.YouSetDayMode));


                    //Toast.makeText(SettingsActivity.this, getResources().getString(R.string.YouSetDayMode), Toast.LENGTH_SHORT).show();


                } else {


                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor1.putBoolean("isDarkModeOn", true);
                    editor1.apply();
                    editor1.commit();

                    themeButton.setText(getResources().getString(R.string.DayMode));
                    theme_mode_txt.setText(getResources().getString(R.string.YouSetNightMode));



                    //Toast.makeText(SettingsActivity.this, getResources().getString(R.string.YouSetNightMode), Toast.LENGTH_SHORT).show();


                }
            }
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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