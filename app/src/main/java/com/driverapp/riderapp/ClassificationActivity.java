package com.driverapp.riderapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ClassificationActivity extends AppCompatActivity {

    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        list = findViewById(R.id.list);

        Classification classification = new Classification();
        classification.setContacts("0876678767");
        classification.setLocation("Nairobi Kenya");
        classification.setSpec("Mechanic");
        classification.setName("Simon Mutuku");

        ArrayList<Classification> classifications = new ArrayList<>();
        ClassificationAdapter classificationAdapter = new ClassificationAdapter(this, classifications);

        list.setAdapter(classificationAdapter);

    }
}
