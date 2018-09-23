package com.driverapp.riderapp.Common;

import com.driverapp.riderapp.Remote.FCMClient;
import com.driverapp.riderapp.Remote.FCMService;
import com.driverapp.riderapp.Remote.GoogleMapAPI;
import com.driverapp.riderapp.Remote.IGoogleAPI;

public class Common {
    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_request_tbl = "PickupRequest";


    public static final String token_tbl = "Tokens";

    private static final String fcmURL = "https://fcm.googleapis.com/";
    private static final String googleAPIUrl = "https://maps.googleapis.com/";


    private static double base_fare = 100.00;
    private static double time_rate = 4.00;
    private static double distance_rate = 40.00;

    public static double getPrice(double km, int min)
    {
        return base_fare+(time_rate*min)+(distance_rate*km);
    }


    public static FCMService getFCMService()
    {
        return FCMClient.getClient(fcmURL).create(FCMService.class);
    }

    public static IGoogleAPI getGoogleService()
    {
        return GoogleMapAPI.getClient(googleAPIUrl).create(IGoogleAPI.class);
    }
}
