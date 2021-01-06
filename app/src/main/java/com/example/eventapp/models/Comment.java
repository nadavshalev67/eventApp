package com.example.eventapp.models;

import java.util.UUID;

public class Comment {

    private String commentID;
    private String content;
    private String eventNum;
    private String currentEmail;

    public Comment() {
        this.commentID = UUID.randomUUID().toString();
    }

    public Comment(String content, String currentEmail, String eventNum, String commentID) {
        setContent(content);
        setCurrentEmail(currentEmail);
        setEventNum(eventNum);
        setCommentID(commentID);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }


    public String getEventNum() {
        return eventNum;
    }

    public void setEventNum(String eventNum) {
        this.eventNum = eventNum;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }
}
