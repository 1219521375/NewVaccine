package com.example.pokestar.vaccineremind.utils;

import android.util.Log;

public class LogUtil {
    private String TAG;
    private boolean isRelease = false;
    private boolean isDebug;

    public LogUtil(Class c, boolean isDebug) {
        this.TAG = c.getName();
        this.isDebug = isDebug;
    }

    public void d(String msg) {
        if (!isRelease && isDebug) {
            Log.d(TAG, "--------->" + msg);
        }
    }

    public void i(String msg) {
        if (!isRelease && isDebug) {
            Log.i(TAG, "--------->" + msg);
        }
    }

    public void w(String msg) {
        if (!isRelease && isDebug) {
            Log.w(TAG, "--------->" + msg);
        }
    }

    public void e(String msg) {
        if (!isRelease && isDebug) {
            Log.e(TAG, "--------->" + msg);
        }
    }
}
