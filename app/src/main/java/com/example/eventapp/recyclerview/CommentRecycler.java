package com.example.eventapp.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;
import com.example.eventapp.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentRecycler extends RecyclerView.Adapter<CommentRecycler.ViewHolder> {

    private List<Comment> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public CommentRecycler(Context context) {
        this.mInflater = LayoutInflater.from(context);

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comments_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mData.get(position);
        holder.mUserEmailComment.setText(comment.email);
        holder.mContentComment.setText(comment.content);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView mUserEmailComment;
        TextView mContentComment;
        ImageView mImageView;


        ViewHolder(View itemView) {
            super(itemView);
            mUserEmailComment = itemView.findViewById(R.id.userEmailComment);
            mContentComment = itemView.findViewById(R.id.contentComment);
            mImageView = itemView.findViewById(R.id.delete_comment_btn);
        }

    }


    public void updateCommentList(List<Comment> comments) {
        mData.clear();
        mData.addAll(comments);
        notifyDataSetChanged();
    }

}

