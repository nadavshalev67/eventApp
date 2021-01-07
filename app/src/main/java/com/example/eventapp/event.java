package com.example.eventapp;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.UUID;

public class event {
    private String ID ;
    private String eventType ;
    private String pic ;
    private String description;
    private String Address ;
    private String Levelofrisk ;
    private String ownerEmail;

    private ArrayList<String> ApprovalArray ;
    private ArrayList<String> disApprovlArray;
    private Boolean DeletedVald;

    public event() {
        this.ID = UUID.randomUUID().toString() ;
        this.ownerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        ApprovalArray = new ArrayList<String>();
        disApprovlArray = new ArrayList<String>();
    }

    public event(String ID, String eventType, String pic, String description, String address,
                 String level_of_risk, String ownerEmail,Boolean deletedVald) {
        setID(ID);
        setEventType(eventType);
        setPic(pic);
        setDescription(description);
        setAddress(address);
        setLevelofrisk(Levelofrisk);
        setOwnerEmail(ownerEmail);
        setDeletedVald(deletedVald);

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLevelofrisk() {
        return Levelofrisk;
    }

    public void setLevelofrisk(String levelofrisk) {
        Levelofrisk = levelofrisk;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Boolean getDeletedVald() {
        return DeletedVald;
    }

    public void setDeletedVald(Boolean deletedVald) {
        DeletedVald = deletedVald;
    }

    //ApprovalArray-----------------------------------------------------------------
    public ArrayList<String> getApprovalArray() {
        return ApprovalArray;
    }

    public void setApprovalArray(ArrayList<String> approvalArray) {
        this.ApprovalArray = approvalArray;
    }

    public void addToApprovalArrayList(String email) {
        ApprovalArray.add(email);
    }
    public void removeFromApprovalArrayList(String email) {
        ApprovalArray.remove(email);
    }

//validationArray-------------------------------------------------------------------------
    public ArrayList<String> getDisApprovlArray() {
        return disApprovlArray;
    }

    public void setDisApprovlArray(ArrayList<String> disApprovlArray) {
        this.disApprovlArray = disApprovlArray;
    }

    public void addToValidationArrayList(String email) {
        disApprovlArray.add(email);
    }
    public void removeFromValidationArrayList(String email) {
        disApprovlArray.remove(email);
    }

}
