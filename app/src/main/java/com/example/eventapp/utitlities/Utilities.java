package com.example.eventapp.utitlities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

public class Utilities {
    private static Context mAppContext;

    public static Context getAppContenxt() {
        return mAppContext;
    }

    public static void setAppContext(Context context) {
        mAppContext = context;
    }

    public static String getUUID() {
        return FirebaseAuth.getInstance().getUid();
    }


}
