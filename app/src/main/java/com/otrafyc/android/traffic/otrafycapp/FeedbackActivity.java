package com.otrafyc.android.traffic.otrafycapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
public class FeedbackActivity extends AppCompatActivity {

    TextView sendText;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_feedback);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        final TextInputEditText toFeedback = (TextInputEditText) findViewById(R.id.toEditText);
        final TextInputEditText subjectFeedback = (TextInputEditText) findViewById(R.id.subjectEditText);
        final TextInputEditText messageFeedback = (TextInputEditText) findViewById(R.id.messageEditText);
        ImageView sendFeedbackButton = (ImageView) findViewById(R.id.send_feedback_button);


       // subjectFeedback.requestFocus();



        toFeedback.setText(R.string.onimtechnologies_gmail_com);
        toFeedback.endBatchEdit();


        sendText = findViewById(R.id.sendText);

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri feedbackUri = Uri.parse("mailto:" +toFeedback.getText().toString().trim());
                Intent sendFeedbackIntent = new Intent(Intent.ACTION_VIEW, feedbackUri);
                sendFeedbackIntent.putExtra(Intent.EXTRA_SUBJECT, subjectFeedback.getText().toString());
                sendFeedbackIntent.putExtra(Intent.EXTRA_TEXT, messageFeedback.getText().toString());
                startActivity(Intent.createChooser(sendFeedbackIntent, "Send feedback via..."));



            }
        });


        sendFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri feedbackUri = Uri.parse("mailto:" +toFeedback.getText().toString().trim());
                Intent sendFeedbackIntent = new Intent(Intent.ACTION_VIEW, feedbackUri);
                sendFeedbackIntent.putExtra(Intent.EXTRA_SUBJECT, subjectFeedback.getText().toString());
                sendFeedbackIntent.putExtra(Intent.EXTRA_TEXT, messageFeedback.getText().toString());
                startActivity(Intent.createChooser(sendFeedbackIntent, "Send feedback via..."));

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onBackButtonPressed(View view) {
        onBackPressed();
    }
}