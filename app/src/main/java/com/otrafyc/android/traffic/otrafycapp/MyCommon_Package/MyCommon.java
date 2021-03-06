package com.otrafyc.android.traffic.otrafycapp.MyCommon_Package;

import com.otrafyc.android.traffic.otrafycapp.Remote_Package.IGoogleAPI;
import com.otrafyc.android.traffic.otrafycapp.Remote_Package.RetrofitClient;

public class MyCommon {

    public static final String baseURL = "https://maps.googleapis.com";
    public static IGoogleAPI getGoogleAPI(){
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);

    }
}
