package com.driverapp.riderapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.driverapp.riderapp.Model.Services;

import java.util.ArrayList;

public class ClassificationAdapter extends RecyclerView.Adapter<ClassificationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Classification> classifications;

    public ClassificationAdapter(Context context, ArrayList<Classification> classifications) {
        this.context = context;
        this.classifications = classifications;
    }

    @Override
    public ClassificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.classification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassificationAdapter.ViewHolder holder, int position) {
        Classification classification = classifications.get(position);

        holder.name.setText(classification.getName());
        holder.spec.setText(classification.getSpec());
        holder.contact.setText(classification.getContacts());
        holder.location.setText(classification.getLocation());

    }

    @Override
    public int getItemCount() {
        return classifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, spec, contact, location;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            spec = itemView.findViewById(R.id.spec);
            location = itemView.findViewById(R.id.location);
            contact = itemView.findViewById(R.id.contact);
        }

    }

}
