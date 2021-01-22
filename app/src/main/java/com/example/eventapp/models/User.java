package com.example.eventapp.models;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String coins_aprroved_by_other_users_to_my_events;
    public String conis_from_my_approve_to_other_user;
    public String amount_approved_by_me;
    public String amount_rejected_by_me;
    public String amout_of_my_events;
    public String email;
    public String uuid;

    public User(String coins_aprroved_by_other_users_to_my_events, String conis_from_my_approve_to_other_user, String amount_approved_by_me, String amount_rejected_by_me, String amout_of_my_events, String email, String uuid) {
        this.coins_aprroved_by_other_users_to_my_events = coins_aprroved_by_other_users_to_my_events;
        this.conis_from_my_approve_to_other_user = conis_from_my_approve_to_other_user;
        this.amount_approved_by_me = amount_approved_by_me;
        this.amount_rejected_by_me = amount_rejected_by_me;
        this.amout_of_my_events = amout_of_my_events;
        this.email = email;
        this.uuid = uuid;
    }
    public User(){}

    public User(String uuid, String email) {
        this.coins_aprroved_by_other_users_to_my_events = "0";
        this.amount_approved_by_me = "0";
        this.amount_rejected_by_me = "0";
        this.amout_of_my_events = "0";
        this.conis_from_my_approve_to_other_user = "0";
        this.uuid = uuid;
        this.email = email;
    }

    public static User fromDocument(QueryDocumentSnapshot document) {
        Map<String, Object> values = document.getData();
        String coins_aprroved_by_other_users_to_my_events = (String) values.get("coins_aprroved_by_other_users_to_my_events");
        String conis_from_my_approve_to_other_user = (String) values.get("conis_from_my_approve_to_other_user");
        String amount_approved_by_me = (String) values.get("amount_approved_by_me");
        String amount_rejected_by_me = (String) values.get("amount_rejected_by_me");
        String amout_of_my_events = (String) values.get("amout_of_my_events");
        String email = (String) values.get("email");
        User user = new User(coins_aprroved_by_other_users_to_my_events, conis_from_my_approve_to_other_user, amount_approved_by_me, amount_rejected_by_me, amout_of_my_events, email, document.getId());
        return user;
    }

    public HashMap<String, String> genereateHashMap() {
        HashMap<String, String> values = new HashMap<>();
        values.put("amout_of_my_events", "0");
        values.put("coins_aprroved_by_other_users_to_my_events", "0");
        values.put("amount_approved_by_me", "0");
        values.put("amount_rejected_by_me", "0");
        values.put("conis_from_my_approve_to_other_user", "0");
        values.put("email", this.email);
        return values;
    }

    public String getCoins_aprroved_by_other_users_to_my_events() {
        return coins_aprroved_by_other_users_to_my_events;
    }

    public void setCoins_aprroved_by_other_users_to_my_events(String coins_aprroved_by_other_users_to_my_events) {
        this.coins_aprroved_by_other_users_to_my_events = coins_aprroved_by_other_users_to_my_events;
    }

    public String getConis_from_my_approve_to_other_user() {
        return conis_from_my_approve_to_other_user;
    }

    public void setConis_from_my_approve_to_other_user(String conis_from_my_approve_to_other_user) {
        this.conis_from_my_approve_to_other_user = conis_from_my_approve_to_other_user;
    }

    public String getAmount_approved_by_me() {
        return amount_approved_by_me;
    }

    public void setAmount_approved_by_me(String amount_approved_by_me) {
        this.amount_approved_by_me = amount_approved_by_me;
    }

    public String getAmount_rejected_by_me() {
        return amount_rejected_by_me;
    }

    public void setAmount_rejected_by_me(String amount_rejected_by_me) {
        this.amount_rejected_by_me = amount_rejected_by_me;
    }

    public String getAmout_of_my_events() {
        return amout_of_my_events;
    }

    public void setAmout_of_my_events(String amout_of_my_events) {
        this.amout_of_my_events = amout_of_my_events;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}