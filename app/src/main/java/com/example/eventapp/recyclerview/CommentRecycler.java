package com.example.eventapp.recyclerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;
import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.models.Comment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CommentRecycler extends RecyclerView.Adapter<CommentRecycler.ViewHolder> {

    private List<Comment> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public CommentRecycler(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;

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

        if (mData.get(position).email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Delete comment?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mData.remove(position);
                                    SQLHolder.getInstance().removeComment(mData.get(position).commentID);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "comment deleted", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });

        } else {
            holder.mDeleteButton.setVisibility(View.INVISIBLE);
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mUserEmailComment;
        TextView mContentComment;
        ImageView mDeleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            mUserEmailComment = itemView.findViewById(R.id.userEmailComment);
            mContentComment = itemView.findViewById(R.id.contentComment);
            mDeleteButton = itemView.findViewById(R.id.delete_comment_btn);
        }

    }

    public void updateCommentList(List<Comment> comments) {
        mData.clear();
        mData.addAll(comments);
        notifyDataSetChanged();
    }

}

