package com.coreproc.paymaya.sdk.utils;

import android.util.Log;

public class Logger {

    private static String TAG = "PayMayaSDKLog";
    public static Boolean DEBUGGABLE = false;

    public static void message(String message) {
        if (DEBUGGABLE)
            Log.d(TAG, message);
    }

    public static void message(Throwable throwable) {
        if (DEBUGGABLE) {
            Log.d(TAG, "" + throwable.getMessage());
            Log.d(TAG, "" + throwable.getLocalizedMessage());
            Log.d(TAG, "" + throwable.getCause());
        }
    }

    public static void message(Exception exception) {
        if (DEBUGGABLE) {
            Log.d(TAG, "" + exception.getMessage());
            Log.d(TAG, "" + exception.getLocalizedMessage());
            Log.d(TAG, "" + exception.getCause());
        }
    }

}
