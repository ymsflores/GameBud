package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList name_id, region_id, acc_id;

    public MyAdapter(Context context, ArrayList name_id, ArrayList region_id, ArrayList acc_id) {
        this.context = context;
        this.name_id = name_id;
        this.region_id = region_id;
        this.acc_id = acc_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtName.setText(String.valueOf(name_id.get(position)));
        holder.txtRegion.setText(String.valueOf(region_id.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Onclick", "Does this work?");
                int accID = (int) acc_id.get(holder.getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putInt("accID", accID);

                OtherProfileFragment fragment = new OtherProfileFragment();
                fragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRegion;
        ImageButton btnViewUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.cardTxtName);
            txtRegion = itemView.findViewById(R.id.cardTxtRegion);
            btnViewUser = itemView.findViewById(R.id.btnViewUser);
        }

    }

    public void filterList(ArrayList<String> filteredNames, ArrayList<String> filteredRegions) {
        name_id = filteredNames;
        region_id = filteredRegions;
        notifyDataSetChanged();
    }
}
