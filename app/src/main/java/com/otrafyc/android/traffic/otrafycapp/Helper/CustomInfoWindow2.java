package com.otrafyc.android.traffic.otrafycapp.Helper;

/*

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.otrafyc.android.traffic.otrafycapp.MapsActivity;
import com.otrafyc.android.traffic.otrafycapp.R;

public class CustomInfoWindow2 extends MapsActivity implements GoogleMap.InfoWindowAdapter {
    View myView;

    //right click to select generate nd go to constructor after typing  View myView; above

    public CustomInfoWindow2(Context context) {
        myView = LayoutInflater.from(context).inflate(R.layout.custom_marker_info_window_2, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView infoTitle = ((TextView)myView.findViewById(R.id.txtMarkerInfoWindowTitle));
        infoTitle.setText(marker.getTitle());


        TextView infoSnippet = ((TextView)myView.findViewById(R.id.txtInfoWindowSnippet));
        infoSnippet.setText(marker.getSnippet());


        Button goButton = ((Button)myView.findViewById(R.id.go_dest_butt));
        Button shareDestButt = ((Button)myView.findViewById(R.id.share_dest_butt));

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"i clicked go button", Toast.LENGTH_SHORT).show();

            }
        });

        shareDestButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"i clicked go button", Toast.LENGTH_SHORT).show();

            }
        });

        return myView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
*/
