package com.example.eventapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.eventapp.R;
import com.example.eventapp.database.SQLBase;
import com.example.eventapp.database.SQLHolder;
import com.example.eventapp.database.firestore.FireStoreSql;
import com.example.eventapp.models.Event;
import com.example.eventapp.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class Summary extends Activity implements SQLBase.SqlListenerEvents , SQLBase.SqlListenerUsers {


    EditText mMyEvents, mApproved, mRejected,
            mProfitFromEvent, mProfitFromApperoved,
            mMaxEvent, mUser, mMaxTotalT,
            mUserSec, mLevelE, mLevelM, mLevelH;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summery);
        init();
        SQLHolder.getInstance().getAllEvents(this);
        SQLHolder.getInstance().getAllUsers(this);
    }

    public void init() {
        mMyEvents = findViewById(R.id.myevent_edittext);
        mApproved = findViewById(R.id.approved_edittext);
        mRejected = findViewById(R.id.rejected_edittext);
        mProfitFromEvent = findViewById(R.id.TotalProfit_edittext);
        mProfitFromApperoved = findViewById(R.id.TotalApproved_edittext);
        mMaxEvent = findViewById(R.id.eventNum_edittext);
        mUser = findViewById(R.id.summery_emailedittext);
        mMaxTotalT = findViewById(R.id.eventProfit_edittext);
        mUserSec = findViewById(R.id.TotalProfit_userEdittext);
        mLevelE = findViewById(R.id.easy_edittext);
        mLevelM = findViewById(R.id.medium_edittext);
        mLevelH = findViewById(R.id.editTextTextPersonName12);
    }


    @Override
    public void onGetAllEvents(List<Event> events) {
        int easy = 0;
        int medium = 0;
        int hard = 0;
        for (Event event : events) {
            if (event.level_of_risk.equals("easy")) {
                easy++;
            }
            if (event.level_of_risk.equals("medium")) {
                medium++;
            }
            if (event.level_of_risk.equals("hard")) {
                hard++;
            }
            mLevelE.setText(String.valueOf(easy));
            mLevelM.setText(String.valueOf(medium));
            mLevelH.setText(String.valueOf(hard));
        }

    }

    @Override
    public void onGetAllUsers(List<User> users) {

        int numOfEvents = 0 ;
        String email = "" ;
        int maxTotal = 0 ;
        String emailMax = "" ;

        for (User user : users){
            if(Integer.parseInt(user.amout_of_my_events) > numOfEvents) {
                numOfEvents = Integer.parseInt(user.amout_of_my_events);
                email = user.email;

            }

            if(Integer.parseInt(user.coins_aprroved_by_other_users_to_my_events) >= maxTotal){
                maxTotal = Integer.parseInt(user.coins_aprroved_by_other_users_to_my_events);
                emailMax = user.email;
            }


            if(user.uuid.equals(FirebaseAuth.getInstance().getUid())){
                mMyEvents.setText(user.amout_of_my_events);
                mApproved.setText(user.amount_approved_by_me);
                mRejected.setText(user.amount_rejected_by_me);
                mProfitFromEvent.setText(user.coins_aprroved_by_other_users_to_my_events);
                mProfitFromApperoved.setText(user.conis_from_my_approve_to_other_user);



            };
        }
        mMaxEvent.setText(String.valueOf(numOfEvents));
        mUser.setText(email);
        mMaxTotalT.setText(String.valueOf(maxTotal));
        mUserSec.setText(emailMax);





    }
}
