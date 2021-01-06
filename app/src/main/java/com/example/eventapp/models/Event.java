package com.example.eventapp.models;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Event {

    public String event_adress;
    public String approved_count;
    public String event_description;
    public String event_name;
    public String level_of_risk;
    public String pictureBase64;
    public String rejected_count;
    public String user_id;
    public String id_document;

    public Event(String event_adress, String event_description, String event_name, String level_of_risk, String pictureBase64, String user_id) {
        this(event_adress, "0", event_description, event_name, level_of_risk, pictureBase64, "0", user_id);
    }

    public void setEventDocument(String id_document) {
        this.id_document = id_document;
    }


    public Event(String event_adress, String approved_count, String event_description, String event_name, String level_of_risk, String pictureBase64, String rejected_count, String user_id) {
        this.event_adress = event_adress;
        this.approved_count = approved_count;
        this.event_description = event_description;
        this.event_name = event_name;
        this.level_of_risk = level_of_risk;
        this.pictureBase64 = pictureBase64;
        this.rejected_count = rejected_count;
        this.user_id = user_id;
    }

    public static Event fromDocument(QueryDocumentSnapshot document) {
        Map<String, Object> values = document.getData();
        String adress = (String) values.get("event_adress");

        String approved_count = (String) values.get("approved_count");
        String event_description = (String) values.get("event_description");
        String event_name = (String) values.get("event_name");
        String level_of_risk = (String) values.get("event_adress");
        String pictureBase64 = (String) values.get("pictureBase64");
        String rejected_count = (String) values.get("rejected_count");
        String user_id = (String) values.get("user_id");
        Event event = new Event(adress, approved_count, event_description, event_name, level_of_risk, pictureBase64, rejected_count, user_id);
        event.setEventDocument(document.getId());
        return event;
    }

    public Map<String, String> genereateHashMap() {
        Field[] fields = this.getClass().getDeclaredFields();
        Map<String, String> valuesMap = new HashMap<>();
        for (Field field : fields) {
            try {
                if (!field.getName().equals("id_document")) {
                    valuesMap.put(field.getName(), (String) field.get(this));
                }
            } catch (Exception e) {
            }
        }
        return valuesMap;
    }
}
