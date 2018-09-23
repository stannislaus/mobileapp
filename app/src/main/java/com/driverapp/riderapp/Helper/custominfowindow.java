package com.driverapp.riderapp.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.driverapp.riderapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class custominfowindow implements  GoogleMap.InfoWindowAdapter {

    View myView;

    public custominfowindow(Context context){
        myView = LayoutInflater.from(context)
                .inflate(R.layout.custom_rider_info_window, null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView txtpickuptitle = ((TextView)myView.findViewById(R.id.txtPickupInfo));
        txtpickuptitle.setText(marker.getTitle());

        TextView txtpickupsnippet = ((TextView)myView.findViewById(R.id.txtPickupSnippet));
        txtpickupsnippet.setText(marker.getSnippet());


        return myView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
