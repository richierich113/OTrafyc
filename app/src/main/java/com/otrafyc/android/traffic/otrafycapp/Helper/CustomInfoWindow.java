package com.otrafyc.android.traffic.otrafycapp.Helper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.otrafyc.android.traffic.otrafycapp.R;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    View myView;

    //right click to select generate nd go to constructor after typing  View myView; above

    public CustomInfoWindow(Context context) {
        myView = LayoutInflater.from(context).inflate(R.layout.custom_marker_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView infoTitle = ((TextView)myView.findViewById(R.id.txtMarkerInfoWindowTitle));
        infoTitle.setText(marker.getTitle());


        TextView infoSnippet = ((TextView)myView.findViewById(R.id.txtInfoWindowSnippet));
        infoSnippet.setText(marker.getSnippet());



        return myView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
