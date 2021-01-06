package com.example.eventapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListRecyclerView extends RecyclerView.Adapter<EventListRecyclerView.ViewHolder> {

    private List<Event> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public EventListRecyclerView(Context context) {
        this.mInflater = LayoutInflater.from(context);

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mData.get(position);
        holder.mEventName.setText(event.event_name);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mEventName;

        ViewHolder(View itemView) {
            super(itemView);
            mEventName = itemView.findViewById(R.id.eventNameFill);
        }
    }


    public void updateEventList(List<Event> events) {
        mData.addAll(events);
        notifyDataSetChanged();
    }
}
