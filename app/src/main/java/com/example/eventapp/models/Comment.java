package com.example.eventapp.models;


import java.util.HashMap;
import java.util.Map;

public class Comment {

    public String commentID;
    public String content;
    public String eventIdrelated;
    public String email;

    public Comment(String content, String eventIdrelated, String email) {
        this.content = content;
        this.eventIdrelated = eventIdrelated;
        this.email = email;
        commentID = "";
    }

    public Map<String, Object> genereateHashMap() {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("content", content);
        valuesMap.put("eventIdrelated", eventIdrelated);
        valuesMap.put("email", email);
        return valuesMap;
    }
}
