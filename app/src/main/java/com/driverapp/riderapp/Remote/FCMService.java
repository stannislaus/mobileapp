package com.driverapp.riderapp.Remote;

import com.driverapp.riderapp.Model.FCMResponse;
import com.driverapp.riderapp.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization: key=AAAAzIzATN4:APA91bGC3EAHQBacU2AFwYrVu1dKTP4jf6IsU_87CIlvvsiZLftZX4Nq3NEdlbDE3Uu6qt-xeR17iVIqQQTWq-SHtoBDgi68MV5eFpfVnoxSIGB3KrYzMbeBHTuLaSOSSOuImTDUml2X"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);
}
