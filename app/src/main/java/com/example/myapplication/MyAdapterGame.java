package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterGame extends RecyclerView.Adapter<MyAdapterGame.MyViewHolder> {
    private Context context;
    private ArrayList name_id, time_id, acc_id;

    public MyAdapterGame(Context context, ArrayList name_id, ArrayList time_id, ArrayList acc_id) {
        this.context = context;
        this.name_id = name_id;
        this.time_id = time_id;
        this.acc_id = acc_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.gameentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtName.setText(String.valueOf(name_id.get(position)));
        holder.txtTime.setText(String.valueOf(time_id.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int accID = (int) acc_id.get(holder.getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putInt("accID", accID);

                GameDetailsFragment fragment = new GameDetailsFragment();
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
        TextView txtName, txtTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.cardTxtGameName);
            txtTime = itemView.findViewById(R.id.cardTxtTime);
        }
    }

}
