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

import java.util.List;

public class ConfAdapter extends RecyclerView.Adapter<ConfAdapter.ConferenceViewHolder> {
    class ConferenceViewHolder extends RecyclerView.ViewHolder {

        private final TextView conferenceItemView;
        private final TextView conferenceItemView2;

        private ConferenceViewHolder(View itemView) {
            super(itemView);
            conferenceItemView = itemView.findViewById(R.id.textView2);
            conferenceItemView2 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Conference current = mConferences.get(position);
                    Intent intent = new Intent(context, ConfDetails.class);
                    intent.putExtra("id", current.getConferenceID());
                    intent.putExtra("name", current.getConferenceName());
                    intent.putExtra("date",current.getDate());
                    intent.putExtra("prodID", current.getTripID());
                    context.startActivity(intent);
                }

            });
        }
    }
    private List<Conference> mConferences;
    private final Context context;
    private final LayoutInflater mInflater;

    public ConfAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public ConferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView= mInflater.inflate(R.layout.conference_list_item, parent, false);
        return new ConferenceViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ConfAdapter.ConferenceViewHolder holder, int position) {
        if(mConferences != null){
            Conference current= mConferences.get(position);
            String name= current.getConferenceName();
            int prodID= current.getTripID();
            holder.conferenceItemView.setText(name);
            holder.conferenceItemView2.setText(Integer.toString(prodID));
        } else {
            holder.conferenceItemView.setText("No excursion name");
            holder.conferenceItemView2.setText("No id");
        }
        }
        public void setmConferences(List<Conference> conferences){
            mConferences = conferences;
            notifyDataSetChanged();
        }
        @Override
        public int getItemCount() {
            if(mConferences!= null) return mConferences.size();
            else return 0;
        }
}
