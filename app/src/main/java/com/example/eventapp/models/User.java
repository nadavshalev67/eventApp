package com.example.eventapp.models;

import java.util.HashMap;

public class User {

    public String coins_aprroved_by_other_users_to_my_events;
    public String conis_from_my_approve_to_other_user;
    public String amount_approved_by_me;
    public String amount_rejected_by_me;
    public String amout_of_my_events;
    public String email;
    public String uuid;

    public User(String uuid, String email) {
        this.coins_aprroved_by_other_users_to_my_events = "0";
        this.amount_approved_by_me = "0";
        this.amount_rejected_by_me = "0";
        this.amout_of_my_events = "0";
        this.conis_from_my_approve_to_other_user = "0";
        this.uuid = uuid;
        this.email = email;
    }

    public HashMap<String, String> genereateHashMap() {
        HashMap<String, String> values = new HashMap<>();
        values.put("amout_of_my_events", "0");
        values.put("coins_aprroved_by_other_users_to_my_events", "0");
        values.put("amount_approved_by_me", "0");
        values.put("amount_rejected_by_me", "0");
        values.put("conis_from_my_approve_to_other_user", "0");
        return values;
    }
}