package com.example.eventapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class AddEditEventActivity extends Activity implements View.OnClickListener, FireStoreSql.SqlListener {
    private ImageView mAddPicBtn, mImageDescription;
    private EditText mEventName, mEventDesc, mEventAddress;
    private Spinner mLevelOfRiskSpinnner;
    private Button mAddBtn;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private boolean mIsImageSeted = false;


    private Event editEvent;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String[] ARRAY_SPINNER = new String[]{
            "easy", "medium", "hard"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_event_activity);
        Gson gson = new Gson();
        editEvent = gson.fromJson(getIntent().getStringExtra("eventJson"), Event.class);
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
        if (editEvent != null) {
            mEventName.setText(editEvent.event_name);
            mEventDesc.setText(editEvent.event_description);
            mEventAddress.setText(editEvent.event_adress);
            mIsImageSeted = true;
            Picasso.get().load(editEvent.url_of_pitcure).into(mImageDescription);
            mAddBtn.setText("Edit");
        }
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
                    Toast.makeText(AddEditEventActivity.this, "please enter name", Toast.LENGTH_LONG).show();
                    return;
                }
                eventName = mEventName.getText().toString();

                if (TextUtils.isEmpty(mEventDesc.getText().toString())) {
                    Toast.makeText(AddEditEventActivity.this, "please enter description", Toast.LENGTH_LONG).show();
                    return;
                }
                eventDescription = mEventDesc.getText().toString();

                if (TextUtils.isEmpty(mEventAddress.getText().toString())) {
                    Toast.makeText(AddEditEventActivity.this, "please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                eventAdress = mEventAddress.getText().toString();


                if (!mIsImageSeted) {
                    Toast.makeText(AddEditEventActivity.this, "please take a photo", Toast.LENGTH_LONG).show();
                    return;
                }

                //Upload Image
                mImageDescription.setDrawingCacheEnabled(true);
                mImageDescription.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) mImageDescription.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                StorageReference mountainImagesRef = mStorageRef.child(String.valueOf(mImageDescription.hashCode()));
                UploadTask uploadTask = mountainImagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Event event = new Event(eventAdress, eventDescription, eventName, levelOfRisk, uri.toString(), Utilities.getUUID());
                                        if (editEvent == null) {
                                            SQLHolder.getInstance().insertEvent(Utilities.getUUID(), event, AddEditEventActivity.this);
                                        } else {
                                            SQLHolder.getInstance().updateEvent(editEvent.id_document, event, AddEditEventActivity.this);
                                        }

                                    }
                                });
                            }
                        }


                    }
                });


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
        Toast.makeText(AddEditEventActivity.this, "Event added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailedListener(Exception e) {
        Toast.makeText(AddEditEventActivity.this, "Failed to upload event", Toast.LENGTH_SHORT).show();
    }
}


