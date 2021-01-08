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
import com.example.eventapp.models.Comment;
import com.example.eventapp.recyclerview.CommentRecycler;
import com.example.eventapp.recyclerview.EventListRecyclerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class AllCommentActivity extends Activity {

    CommentRecycler mAdapter;
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);
        initViews();
        fetchEventList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchEventList();
    }

    private void fetchEventList() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("hello", "2", "email@gmail.com"));
        comments.add(new Comment("hello1", "2", "email1@gmail.com"));
        comments.add(new Comment("hello2", "2", "email2@gmail.com"));
        comments.add(new Comment("hello3", "2", "email3@gmail.com"));
        comments.add(new Comment("hello4", "2", "email4@gmail.com"));
        mAdapter.updateCommentList(comments);
    }


    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerViewComment);
        mAdapter = new CommentRecycler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}

