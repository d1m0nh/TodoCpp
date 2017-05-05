package com.fhflensburg.todocpp;

import android.util.Log;

/**
 * Author: Dmitri Hammernik
 * Date: 04.04.17
 */

public class Logger extends com.fhflensburg.libtodo.djinni.Logger {
    @Override
    public void i(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void e(String tag, String error) {
        Log.e(tag, error);
    }
}
