package com.driverapp.riderapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.driverapp.riderapp.Model.Services;

import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        recyclerView = findViewById(R.id.list);

        ArrayList<Services> services = new ArrayList<>();
        Services plumber = new Services("Plumber", "plumber");
        Services elec = new Services("Electrician", "electrician");
        Services painter = new Services("Painter", "painter_palette");
        Services mech = new Services("Mechanic", "painter_palette");

        services.add(plumber);
        services.add(elec);
        services.add(painter);
        services.add(mech);

        ServicesAdapter servicesAdapter = new ServicesAdapter(this, services);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(servicesAdapter);

    }
}
