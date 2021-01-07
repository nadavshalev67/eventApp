package com.example.eventapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.eventapp.R;
import com.example.eventapp.database.SQLBase;
import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.models.Comment;
import com.google.firebase.auth.FirebaseAuth;


public class AddCommentActivity extends Activity implements View.OnClickListener, SQLBase.SqlListener {
    private String eventId;
    private EditText userComment;
    private Button addBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_comment);
        userComment = findViewById(R.id.userComment);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        eventId = getIntent().getStringExtra("eventId");
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(userComment.getText().toString())) {
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            return;
        }
        Comment comment = new Comment(userComment.getText().toString(), eventId, FirebaseAuth.getInstance().getCurrentUser().getEmail());
        SQLHolder.getInstance().insertComment(comment, this);
    }

    @Override
    public void onCompleteListener() {
        Toast.makeText(this, "comment added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailedListener(Exception e) {
        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
    }
}
