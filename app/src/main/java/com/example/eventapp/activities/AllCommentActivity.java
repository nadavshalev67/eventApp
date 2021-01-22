package com.example.eventapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;
import com.example.eventapp.database.SQLBase;
import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.models.Comment;
import com.example.eventapp.recyclerview.CommentRecycler;
import com.example.eventapp.recyclerview.EventListRecyclerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class AllCommentActivity extends Activity implements SQLBase.SqlListenerComments {

    CommentRecycler mAdapter;
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);
        initViews();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        SQLHolder.getInstance().getAllCommentsOfOneEvent(id, this);
    }


    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerViewComment);
        mAdapter = new CommentRecycler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onGetCommentsFromEvent(List<Comment> comments) {
        mAdapter.updateCommentList(comments);


    }
}

