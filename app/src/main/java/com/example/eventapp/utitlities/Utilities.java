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

    public static void  setAppContext(Context context) {
        mAppContext = context;
    }

    public static String getUUID() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static String convertBitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}
