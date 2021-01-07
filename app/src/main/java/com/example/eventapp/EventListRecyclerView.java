package com.example.eventapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.models.Event;
import com.example.eventapp.utitlities.Utilities;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

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
        holder.mDescription.setText(event.event_description);
        holder.mAdress.setText(event.event_adress);
        holder.mApprovedCounter.setText(String.valueOf(event.approved_users_list.size()));
        holder.mRejectedCounter.setText(String.valueOf(event.rejected_users_list.size()));
        Picasso.get().load(event.url_of_pitcure).into(holder.mImageView);
        holder.mButtonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.approved_users_list.add(Utilities.getUUID());
                SQLHolder.getInstance().updateApprovedUser(event.id_document, event.approved_users_list);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mEventName;
        TextView mDescription;
        TextView mAdress;
        EditText mApprovedCounter;
        EditText mRejectedCounter;
        ImageView mImageView;
        ImageView mButtonApprove;

        ViewHolder(View itemView) {
            super(itemView);
            mButtonApprove = itemView.findViewById(R.id.bth_approve_user);
            mImageView = itemView.findViewById(R.id.eventPic);
            mEventName = itemView.findViewById(R.id.eventNameFill);
            mDescription = itemView.findViewById(R.id.descriptionFill);
            mAdress = itemView.findViewById(R.id.eventAddressFill);
            mApprovedCounter = itemView.findViewById(R.id.approve_count_user);
            mRejectedCounter = itemView.findViewById(R.id.disaprove_count);


        }
    }

    public void updateEventList(List<Event> events) {
        mData.addAll(events);
        notifyDataSetChanged();
    }
}
