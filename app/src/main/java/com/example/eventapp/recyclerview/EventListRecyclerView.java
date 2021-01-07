package com.example.eventapp.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;
import com.example.eventapp.activities.AddEditEventActivity;
import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.models.Event;
import com.example.eventapp.utitlities.Utilities;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventListRecyclerView extends RecyclerView.Adapter<EventListRecyclerView.ViewHolder> {

    private List<Event> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    final private Activity mActivity;

    // data is passed into the constructor
    public EventListRecyclerView(Context context, Activity activity) {
        this.mInflater = LayoutInflater.from(context);
        mActivity = activity;
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
        if (event.user_id.equals(Utilities.getUUID())) {
            disableViews(holder.mButtonApprove, holder.mButtonDisapproved);
            holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mData.remove(position);
                    SQLHolder.getInstance().removeEvent(event.id_document);
                    notifyDataSetChanged();
                }
            });
            holder.mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, AddEditEventActivity.class);
                    Gson gson = new Gson();
                    String eventJson = gson.toJson(event);
                    intent.putExtra("eventJson", eventJson);
                    mActivity.startActivity(intent);
                }
            });
        } else {
            hideViews(holder.mDeleteButton, holder.mEditButton);
            if (isUserAlreadyResponed(event, Utilities.getUUID())) {
                disableViews(holder.mButtonApprove, holder.mButtonDisapproved);
            } else {
                holder.mButtonDisapproved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        disableViews(holder.mButtonApprove, holder.mButtonDisapproved);
                        event.rejected_users_list.add(Utilities.getUUID());
                        SQLHolder.getInstance().updateRejectedUser(event.id_document, event.approved_users_list);
                        notifyDataSetChanged();

                    }
                });
                holder.mButtonApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        disableViews(holder.mButtonApprove, holder.mButtonDisapproved);
                        event.approved_users_list.add(Utilities.getUUID());
                        SQLHolder.getInstance().updateApprovedUser(event.id_document, event.approved_users_list);
                        SQLHolder.getInstance().updateCurrencyForUser(Utilities.getUUID(), 3);
                        if (event.approved_users_list.size() == 5) {
                            SQLHolder.getInstance().updateCurrencyForUser(Utilities.getUUID(), 10);
                        } else if (event.approved_users_list.size() > 5) {
                            SQLHolder.getInstance().updateCurrencyForUser(Utilities.getUUID(), 2);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }

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
        ImageView mButtonDisapproved;
        ImageView mImageView;
        ImageView mButtonApprove;
        ImageView mEditButton;
        ImageView mDeleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            mEditButton = itemView.findViewById(R.id.bth_edit);
            mDeleteButton = itemView.findViewById(R.id.bth_delete);
            mButtonApprove = itemView.findViewById(R.id.bth_approve_user);
            mButtonDisapproved = itemView.findViewById(R.id.bth_disapprove_user);
            mImageView = itemView.findViewById(R.id.eventPic);
            mEventName = itemView.findViewById(R.id.eventNameFill);
            mDescription = itemView.findViewById(R.id.descriptionFill);
            mAdress = itemView.findViewById(R.id.eventAddressFill);
            mApprovedCounter = itemView.findViewById(R.id.approve_count_user);
            mRejectedCounter = itemView.findViewById(R.id.disaprove_count);


        }
    }

    public void updateEventList(List<Event> events) {
        mData.clear();
        mData.addAll(events);
        notifyDataSetChanged();
    }


    private boolean isUserAlreadyResponed(Event event, String uid) {
        if (event.rejected_users_list.contains(uid) || event.approved_users_list.contains(uid)) {
            return true;
        }
        return false;
    }

    private void disableViews(View... views) {
        for (View view : views) {
            view.setEnabled(false);
        }
    }

    private void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
