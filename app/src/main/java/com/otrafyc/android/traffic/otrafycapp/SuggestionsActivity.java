package com.otrafyc.android.traffic.otrafycapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;

public class SuggestionsActivity extends AppCompatActivity {

    AdView mAdView;
    TextView sendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_suggestions);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        final TextInputEditText toSuggestion= (TextInputEditText) findViewById(R.id.toEditText);
        final TextInputEditText subjectSuggestion = (TextInputEditText) findViewById(R.id.subjectEditText);
        final TextInputEditText messageSuggestion = (TextInputEditText) findViewById(R.id.messageEditText);
        ImageView sendSuggestionButton = (ImageView) findViewById(R.id.send_suggestions_button);
        //ImageView inActiveSendSuggestionButton = (ImageView) findViewById(R.id.inactive_send_suggestions_button);


        //subjectSuggestion.requestFocus();

        sendText = findViewById(R.id.sendText);

       /* if (!(subjectSuggestion.length() >1) || messageSuggestion.length() >1) {
            inActiveSendSuggestionButton.setVisibility(View.VISIBLE);
            inActiveSendSuggestionButton.setClickable(false);
            sendText.setClickable(false);

        }else {
            if (inActiveSendSuggestionButton.getVisibility()==View.VISIBLE) {
                inActiveSendSuggestionButton.setVisibility(View.GONE);
            }
            sendSuggestionButton.setVisibility(View.VISIBLE);

            sendText.setClickable(true);
        }*/

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri feedbackUri = Uri.parse("mailto:" +toSuggestion.getText().toString().trim());
                Intent sendContributionIntent = new Intent(Intent.ACTION_VIEW, feedbackUri);
                sendContributionIntent.putExtra(Intent.EXTRA_SUBJECT, subjectSuggestion.getText().toString());
                sendContributionIntent.putExtra(Intent.EXTRA_TEXT, messageSuggestion.getText().toString());
                startActivity(Intent.createChooser(sendContributionIntent, "Send suggestion via..."));



            }
        });
        toSuggestion.setText(R.string.onimtechnologies_gmail_com);

        sendSuggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Uri feedbackUri = Uri.parse("mailto:" + toSuggestion.getText().toString().trim());
                    Intent sendContributionIntent = new Intent(Intent.ACTION_VIEW, feedbackUri);
                    sendContributionIntent.putExtra(Intent.EXTRA_SUBJECT, subjectSuggestion.getText().toString());
                    sendContributionIntent.putExtra(Intent.EXTRA_TEXT, messageSuggestion.getText().toString());
                    startActivity(Intent.createChooser(sendContributionIntent, "Send suggestion via..."));


            }
        });


    }

    public void onBackButtonPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}