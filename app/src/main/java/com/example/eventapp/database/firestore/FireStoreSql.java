package com.example.eventapp.database.firestore;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.eventapp.database.SQLBase;
import com.example.eventapp.models.Event;
import com.example.eventapp.models.User;
import com.example.eventapp.utitlities.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreSql extends SQLBase {

    FirebaseFirestore mDB;

    public FireStoreSql(Context context) {
        super(context);
        mDB = FirebaseFirestore.getInstance();
    }


    @Override
    public void updateApprovedUser(Event event, String uuidOfUserApproved) {
        DocumentReference docRef = mDB.collection(EVENT_TABLE_NAME).document(event.id_document);
        Map<String, Object> map = new HashMap<>();
        map.put("approved_users_list", event.approved_users_list);
        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDB.collection(USER_TABLE_NAME).document(event.user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        int currencyToAddToCreatedUser = 0;
                        if (event.approved_users_list.size() == 5) {
                            currencyToAddToCreatedUser = 10;
                        } else if (event.approved_users_list.size() > 5) {
                            currencyToAddToCreatedUser = 2;
                        }
                        String currentCurrency = (String) task.getResult().get("coins_aprroved_by_other_users_to_my_events");
                        int result = Integer.parseInt(currentCurrency) + currencyToAddToCreatedUser;
                        HashMap<String, Object> objectHashMap = new HashMap<>();
                        objectHashMap.put("coins_aprroved_by_other_users_to_my_events", String.valueOf(result));
                        mDB.collection(USER_TABLE_NAME).document(event.user_id).update(objectHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mDB.collection(USER_TABLE_NAME).document(uuidOfUserApproved).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        String conis_approved_to_other = (String) task.getResult().get("conis_from_my_approve_to_other_user");
                                        String amount_approved_by_me = (String) task.getResult().get("amount_approved_by_me");
                                        int result_conis_approved_to_other = Integer.parseInt(conis_approved_to_other) + 3;
                                        int result_amount_approved_by_me = Integer.parseInt(amount_approved_by_me) + 1;
                                        HashMap<String, Object> objectHashMap = new HashMap<>();
                                        objectHashMap.put("conis_from_my_approve_to_other_user", String.valueOf(result_conis_approved_to_other));
                                        objectHashMap.put("amount_approved_by_me", String.valueOf(result_amount_approved_by_me));
                                        mDB.collection(USER_TABLE_NAME).document(uuidOfUserApproved).update(objectHashMap);
                                    }
                                });


                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void updateRejectedUser(String id_document, List<String> rejectedUser) {
        DocumentReference docRef = mDB.collection(EVENT_TABLE_NAME).document(id_document);
        Map<String, Object> map = new HashMap<>();
        map.put("rejected_users_list", rejectedUser);
        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                final String uuid = Utilities.getUUID();
                mDB.collection(USER_TABLE_NAME).document(uuid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String currentCurrency = (String) task.getResult().get("amount_rejected_by_me");
                        int result = Integer.parseInt(currentCurrency) + 1;
                        HashMap<String, Object> objectHashMap = new HashMap<>();
                        objectHashMap.put("amount_rejected_by_me", result);
                        mDB.collection(USER_TABLE_NAME).document(uuid).update(objectHashMap);
                    }
                });
            }
        });
    }

    @Override
    public void removeEvent(String id_document) {
        mDB.collection(EVENT_TABLE_NAME).document(id_document).delete();
    }

    @Override
    public void updateEvent(String id_document, Event event, FireStoreSql.SqlListener listener) {
        DocumentReference docRef = mDB.collection(EVENT_TABLE_NAME).document(id_document);
        docRef.update(event.genereateHashMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (listener != null) {
                    listener.onCompleteListener();
                }
            }
        });
    }

    @Override
    public void insertEvent(String userCreated, Event event, SqlListener sqlListener) {
        DocumentReference docRef = mDB.collection(EVENT_TABLE_NAME).document();
        docRef.set(event.genereateHashMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (sqlListener != null) {
                    mDB.collection(USER_TABLE_NAME).document(userCreated).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String currentCurrency = (String) task.getResult().get("amout_of_my_events");
                            int result = Integer.parseInt(currentCurrency) + 1;
                            HashMap<String, Object> objectHashMap = new HashMap<>();
                            objectHashMap.put("amout_of_my_events", String.valueOf(result));
                            mDB.collection(USER_TABLE_NAME).document(userCreated).update(objectHashMap);
                        }
                    });
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

    @Override
    public void createUser(User user) {
        DocumentReference docRef = mDB.collection(USER_TABLE_NAME).document(user.uuid);
        docRef.set(user.genereateHashMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {

                }
            }
        });
    }
}
