package com.example.eventapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.eventapp.R;
import com.example.eventapp.database.firestore.FireStoreSql;
import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.models.Event;
import com.example.eventapp.utitlities.Utilities;


public class AddEventActivity extends Activity implements View.OnClickListener, FireStoreSql.SqlListener {
    private ImageView mAddPicBtn, mImageDescription;
    private EditText mEventName, mEventDesc, mEventAddress;
    private Spinner mLevelOfRiskSpinnner;
    private Button mAddBtn;
    private boolean mIsImageSeted = false;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String[] ARRAY_SPINNER = new String[]{
            "easy", "medium", "hard"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_event);
        initViews();

    }

    private void initViews() {
        mImageDescription = findViewById(R.id.EventImage);
        mEventName = findViewById(R.id.eventName);
        mEventDesc = findViewById(R.id.eventDesc);
        mEventAddress = findViewById(R.id.eventAddress);
        mAddBtn = findViewById(R.id.addBtn);
        mAddPicBtn = findViewById(R.id.AddPicBtn);
        initSpinnner();
        setOnCLickLisenterToMultipleViews(mAddPicBtn, mAddBtn);
    }

    private void setOnCLickLisenterToMultipleViews(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(this);
        }
    }

    private void initSpinnner() {
        mLevelOfRiskSpinnner = (Spinner) findViewById(R.id.levelOfRisk);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ARRAY_SPINNER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLevelOfRiskSpinnner.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                openCamera();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mIsImageSeted = true;
            mImageDescription.setImageBitmap(photo);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn: {
                String eventName;
                String eventDescription;
                String eventAdress;
                String levelOfRisk = mLevelOfRiskSpinnner.getSelectedItem().toString();

                if (TextUtils.isEmpty(mEventName.getText().toString())) {
                    Toast.makeText(AddEventActivity.this, "please enter name", Toast.LENGTH_LONG).show();
                    return;
                }
                eventName = mEventName.getText().toString();

                if (TextUtils.isEmpty(mEventDesc.getText().toString())) {
                    Toast.makeText(AddEventActivity.this, "please enter description", Toast.LENGTH_LONG).show();
                    return;
                }
                eventDescription = mEventDesc.getText().toString();

                if (TextUtils.isEmpty(mEventAddress.getText().toString())) {
                    Toast.makeText(AddEventActivity.this, "please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                eventAdress = mEventAddress.getText().toString();


                if (!mIsImageSeted) {
                    Toast.makeText(AddEventActivity.this, "please take a photo", Toast.LENGTH_LONG).show();
                    return;
                }

                Bitmap bm = ((BitmapDrawable) mImageDescription.getDrawable()).getBitmap();
                Event event = new Event(eventAdress, eventDescription, eventName, levelOfRisk, Utilities.convertBitMapToString(bm), Utilities.getUUID());
                SQLHolder.getInstance().insertEvent(event, this);
                break;
            }
            case R.id.AddPicBtn: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                        }
                    } else {
                        openCamera();
                    }
                }

                break;
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    @Override
    public void onCompleteListener() {
        Toast.makeText(AddEventActivity.this, "Event added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailedListener(Exception e) {
        Toast.makeText(AddEventActivity.this, "Failed to upload event", Toast.LENGTH_SHORT).show();
    }
}


