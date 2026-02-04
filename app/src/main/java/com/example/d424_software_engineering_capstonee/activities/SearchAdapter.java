package com.example.d424_software_engineering_capstonee.activities;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.models.Search;


import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Search> results = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Search result);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener= listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.indiv_item_search_results, parent, false);
        return new ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Search result = results.get(position);
        holder.typeBadge.setText(result.getType());
        if(result.getType().equals("Trip")){
            holder.typeBadge.setBackgroundColor(Color.parseColor("#FF5722"));
        } else {
            holder.typeBadge.setBackgroundColor(Color.parseColor("#2196F3"));
        }

        holder.title.setText(result.getType());
        holder.details.setText(result.getDetails());
        holder.dates.setText(result.getDates());
        holder.price.setText(result.getPrice());

        holder.itemView.setOnClickListener(v->{
            if(listener != null) {
                listener.onItemClick(result);
            }
        });

    }
    @Override
    public int getItemCount(){
        return results.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView typeBadge, title, details, dates, price;

        ViewHolder(View itemView){
            super(itemView);
            typeBadge= itemView.findViewById(R.id.result_type);
            title= itemView.findViewById(R.id.title_result);
            details= itemView.findViewById(R.id.result_details);
            dates= itemView.findViewById(R.id.date_results);
            price= itemView.findViewById(R.id.price_results);
        }
    }
}

