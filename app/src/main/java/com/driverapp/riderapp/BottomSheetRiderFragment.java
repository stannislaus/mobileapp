package com.driverapp.riderapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.driverapp.riderapp.Common.Common;
import com.driverapp.riderapp.Remote.IGoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetRiderFragment extends BottomSheetDialogFragment{
    String mlocation, mdestination;

    IGoogleAPI mService;

    TextView txtFare;


    public static BottomSheetRiderFragment newInstance(String location, String destination){
        BottomSheetRiderFragment b = new BottomSheetRiderFragment();
        Bundle args = new Bundle();
        args.putString("location", location);
        args.putString("destination", destination);
        b.setArguments(args);
        return b;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlocation = getArguments().getString("location");
        mdestination = getArguments().getString("destination");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.bottom_sheet_rider,container,false);
        TextView txtLocation = (TextView)view.findViewById(R.id.txtLocation);
        TextView txtDestination = (TextView)view.findViewById(R.id.txtDestination);
        txtFare = (TextView)view.findViewById(R.id.txtRate);


        mService = Common.getGoogleService();
        getPrice(mlocation,mdestination);

        //Get Data
        txtLocation.setText(mlocation);
        txtDestination.setText(mdestination);


        return view;
    }

    private void getPrice(String mlocation, String mdestination) {
        String requestUrl = null;
        try {
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?"+
                    "mode=driving&"+
                    "transit_routing_preference=less_driving&"
                    +"origin="+mlocation+"&"
                    +"destination="+mdestination+"&"
                    +"key="+getResources().getString(R.string.Google_direction_Api);
            Log.d("LINK", requestUrl);
            mService.getPath(requestUrl).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //Get Object
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray routes = jsonObject.getJSONArray("routes");


                        JSONObject object = routes.getJSONObject(0);
                        JSONArray legs = object.getJSONArray("legs");

                        JSONObject legsObject = legs.getJSONObject(0);


                        //Get distance
                        JSONObject distance = legsObject.getJSONObject("distance");
                        String distance_text = distance.getString("text");
                        //Use regex to extract double from string
                        //This regex will remove all texts that is not in digits
                        Double distance_value = Double.parseDouble(distance_text.replaceAll("[^0-9\\\\.]+", ""));

                        // Get Time
                        JSONObject time = legsObject.getJSONObject("duration");
                        String time_text = time.getString("text");
                        Integer time_value = Integer.parseInt(time_text.replaceAll("\\D+", ""));

                        String final_calculate = String.format("%s + %s = KSH %.2f", distance_text, time_text,
                                                Common.getPrice(distance_value, time_value));

                        txtFare.setText(final_calculate);


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());

                }
            });
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
