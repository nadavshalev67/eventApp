package com.example.eventapp.database.firestore;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.eventapp.database.SQLBase;
import com.example.eventapp.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireStoreSql extends SQLBase {

    FirebaseFirestore mDB;

    public FireStoreSql(Context context) {
        super(context);
        mDB = FirebaseFirestore.getInstance();
    }


    @Override
    public void insertEvent(Event event, SqlListener sqlListener) {
        DocumentReference docRef = mDB.collection(EVENT_TABLE_NAME).document();
        docRef.set(event.genereateHashMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (sqlListener != null) {
                    sqlListener.onCompleteListener();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (sqlListener != null) {
                    sqlListener.onFailedListener(e);
                }
            }
        });
    }

    @Override
    public void getAllEvents(SqlListenerEvents sqlListenerEvents) {
        mDB.collection(EVENT_TABLE_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Event> events = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        events.add(Event.fromDocument(document));
                    }
                    if (sqlListenerEvents != null) {
                        sqlListenerEvents.onGetAllEvents(events);
                    }
                } else {
                    //Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }


}
