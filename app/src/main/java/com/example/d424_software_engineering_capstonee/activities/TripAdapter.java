package com.example.d424_software_engineering_capstonee.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.entities.Trip;
import java.util.List;
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip>mTrips;
    private final Context context;
    private final LayoutInflater mInflater;

    public TripAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }
    public class TripViewHolder extends RecyclerView.ViewHolder{
        private final TextView vacationItemView;

        private TripViewHolder(@NonNull View itemView){
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= getAdapterPosition();
                    final Trip current = mTrips.get(position);
                    Intent intent = new Intent(context, TripDetails.class);
                    intent.putExtra("id", current.getTripID());
                    intent.putExtra("name", current.getTripName());
                    intent.putExtra("price",current.getPrice());
                    intent.putExtra("hotelName", current.getHotel());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public TripAdapter.TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView= mInflater.inflate(R.layout.trip_list_item, parent, false);
        return new TripViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull TripAdapter.TripViewHolder holder, int position){
        if(mTrips != null){
            Trip current = mTrips.get(position);
            String name = current.getTripName();
            holder.vacationItemView.setText(name);
        } else {
            holder.vacationItemView.setText("No vacation name!");
        }
    }
    @Override
    public int getItemCount(){
        if(mTrips != null) {
            return mTrips.size();
        } else return 0;
    }
    public void setTrips(List<Trip> trips) {
        mTrips = trips;
        notifyDataSetChanged();
    }
    }


