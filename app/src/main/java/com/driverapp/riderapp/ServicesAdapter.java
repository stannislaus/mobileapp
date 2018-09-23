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

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Services> services;

    public ServicesAdapter(Context context, ArrayList<Services> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.service_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServicesAdapter.ViewHolder holder, int position) {
        Services service = services.get(position);
        holder.textView.setText(service.getName());

        int res = context.getResources().getIdentifier(service.getIcon(), "drawable", context.getPackageName());

        Drawable drawable = ContextCompat.getDrawable(context, res);
        holder.imageView.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.name);
        }

    }

}
