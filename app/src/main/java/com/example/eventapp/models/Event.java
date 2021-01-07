package com.example.eventapp.models;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event implements Serializable {

    public String event_adress;
    public List<String> approved_users_list = new ArrayList<>();
    public String event_description;
    public String event_name;
    public String level_of_risk;
    public String url_of_pitcure;
    public List<String> rejected_users_list = new ArrayList<>();
    public String user_id;
    public String id_document;

    public Event(String event_adress, String event_description, String event_name, String level_of_risk, String url_of_pitcure, String user_id) {
        this(event_adress, new ArrayList<>(), event_description, event_name, level_of_risk, url_of_pitcure, new ArrayList<>(), user_id);
    }

    public void setEventDocument(String id_document) {
        this.id_document = id_document;
    }


    public Event(String event_adress, List<String> approved_count, String event_description, String event_name, String level_of_risk, String url_of_pitcure, List<String> rejected_count, String user_id) {
        this.event_adress = event_adress;
        this.approved_users_list = approved_count;
        this.event_description = event_description;
        this.event_name = event_name;
        this.level_of_risk = level_of_risk;
        this.url_of_pitcure = url_of_pitcure;
        this.rejected_users_list = rejected_count;
        this.user_id = user_id;
    }

    public static Event fromDocument(QueryDocumentSnapshot document) {
        Map<String, Object> values = document.getData();
        String adress = (String) values.get("event_adress");
        List<String> approved_users_list = (List<String>) values.get("approved_users_list");
        String event_description = (String) values.get("event_description");
        String event_name = (String) values.get("event_name");
        String level_of_risk = (String) values.get("event_adress");
        String url_of_pitcure = (String) values.get("url_of_pitcure");
        List<String> rejected_users_list = (List<String>) values.get("rejected_users_list");
        String user_id = (String) values.get("user_id");
        Event event = new Event(adress, approved_users_list, event_description, event_name, level_of_risk, url_of_pitcure, rejected_users_list, user_id);
        event.setEventDocument(document.getId());
        return event;
    }

    public Map<String, Object> genereateHashMap() {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("event_adress", event_adress);
        valuesMap.put("approved_users_list", approved_users_list);
        valuesMap.put("event_description", event_description);
        valuesMap.put("event_name", event_name);
        valuesMap.put("url_of_pitcure", url_of_pitcure);
        valuesMap.put("rejected_users_list", rejected_users_list);
        valuesMap.put("user_id", user_id);
        return valuesMap;
    }


    public Map<String, Object> genereateUpadteHashMap() {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("event_adress", event_adress);
        valuesMap.put("event_description", event_description);
        valuesMap.put("event_name", event_name);
        valuesMap.put("url_of_pitcure", url_of_pitcure);
        return valuesMap;
    }
}
