package com.otrafyc.android.traffic.otrafycapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.otrafyc.android.traffic.otrafycapp.R;

import java.util.zip.Inflater;

public class ProgressDialogClass {


    private final Activity activity;
    private AlertDialog alertDialog;


    ProgressDialogClass(Activity myActivity) {
        activity = myActivity;

    }


    void startLoadingProgress() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_layout, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }

    void dismissProgress() {

        alertDialog.dismiss();
    }


}
