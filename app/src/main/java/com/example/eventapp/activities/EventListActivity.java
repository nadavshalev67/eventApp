package com.example.eventapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.recyclerview.EventListRecyclerView;
import com.example.eventapp.R;
import com.example.eventapp.database.SQLBase;
import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.models.Event;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class EventListActivity extends AppCompatActivity implements SQLBase.SqlListenerEvents {

    EventListRecyclerView mAdapter;
    ProgressBar mProgressBar;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchEventList();
    }

    private void fetchEventList() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        SQLHolder.getInstance().getAllEvents(this);
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EventListRecyclerView(this, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onGetAllEvents(List<Event> events) {
        if (events != null) {
            mAdapter.updateEventList(events);
            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.add_event:
                Intent intent2 = new Intent(this, AddEditEventActivity.class);
                startActivity(intent2);
                return true;
            case R.id.summary:
                Intent intent1 = new Intent(this,Summary.class);
                startActivity(intent1);

                return true;


        }
        return false;
    }
}






