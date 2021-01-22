package com.example.eventapp.models;


import com.google.firebase.firestore.QueryDocumentSnapshot;

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

    public Comment(String content, String eventIdrelated, String email, String commentID) {
        this.content = content;
        this.eventIdrelated = eventIdrelated;
        this.email = email;
        this.commentID = commentID;
    }

    public static Comment fromDocument(QueryDocumentSnapshot document) {
        Map<String, Object> values = document.getData();
        String content = (String) values.get("content");
        String email = (String) values.get("email");
        String eventIdrelated = (String) values.get("eventIdrelated");
        Comment comment = new Comment(content, eventIdrelated, email, document.getId());
        return comment;
    }

    public Map<String, Object> genereateHashMap() {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("content", content);
        valuesMap.put("eventIdrelated", eventIdrelated);
        valuesMap.put("email", email);
        return valuesMap;
    }
}
