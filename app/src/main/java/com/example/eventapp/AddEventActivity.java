package com.example.eventapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class AddEventActivity extends Activity implements View.OnClickListener {
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
                String emailAdd;
                String spinner;

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
                emailAdd = mEventAddress.getText().toString();

                String spin = mLevelOfRiskSpinnner.getSelectedItem().toString();


                if (!mIsImageSeted) {
                    Toast.makeText(AddEventActivity.this, "please take a photo", Toast.LENGTH_LONG).show();
                    return;
                }

                Firestore db = FirestoreClient.getFirestore();
                Bitmap bm=((BitmapDrawable)mImageDescription.getDrawable()).getBitmap();
                String base64 = convertPicture(bm);

                DocumentReference docRef = db.collection("event").document();
                Map<String, String> data = new HashMap<>();
                data.put("event_name" , eventName );
                data.put("event_description" , eventDescription );
                data.put("email_address " , emailAdd );
                data.put("level_of_ " , spin );
                data.put("picture" ,base64 );

                 docRef.set(data);


                break;
            }
            case R.id.addPicBtn: {
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

    private String convertPicture(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}


