package com.birdfoot.mystopwatch;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RvRecordAdapter extends RecyclerView.Adapter{

    private class RecordedTimeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOrder;
        private TextView tvRecordedTime;
        private TextView tvElapsedTime;

        private RecordedTimeViewHolder(View itemView) {
            super(itemView);

            tvOrder = itemView.findViewById(R.id.tvOrder);
            tvRecordedTime = itemView.findViewById(R.id.tvRecordedTime);
            tvElapsedTime = itemView.findViewById(R.id.tvElapsedTime);
        }

        private void bind(Item item) {
            tvOrder.setText(item.getOrder());
            tvRecordedTime.setText(item.getRecordedTime());
            tvElapsedTime.setText(item.getElapsedTime());
        }

    }

    private ArrayList<Item> items;

    public RvRecordAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recorded_time, parent, false);

        return new RecordedTimeViewHolder(itemView);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecordedTimeViewHolder)holder).bind(items.get(position));
    }

    @Override public int getItemCount() {
        return items.size();
    }
}
