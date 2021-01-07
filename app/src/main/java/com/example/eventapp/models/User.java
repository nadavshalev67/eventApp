package com.example.eventapp.models;

import java.util.HashMap;

public class User {

    public String coins_amount;
    public String uuid;

    public User(String coins_amount, String uuid) {
        this.coins_amount = coins_amount;
        this.uuid = uuid;
    }

    public HashMap<String, String> genereateHashMap() {
        HashMap<String, String> values = new HashMap<>();
        values.put("coins_amount", "0");
        return values;
    }
}