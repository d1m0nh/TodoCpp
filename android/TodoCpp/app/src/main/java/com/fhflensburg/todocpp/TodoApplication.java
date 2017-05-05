package com.fhflensburg.todocpp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fhflensburg.libtodo.djinni.TodoAppCore;

/**
 * Author: Dmitri Hammernik
 * Date: 03.04.17
 */

public class TodoApplication extends Application {
    static {
        System.loadLibrary("TodoCpp");
    }

    private TodoAppCore appTodo;

    @Override
    public void onCreate() {
        super.onCreate();

        String rootPath = this.getFilesDir().getAbsolutePath();
        appTodo = TodoAppCore.createApp(rootPath, new Logger());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @NonNull
    public static TodoApplication get(@NonNull Context context) {
        return (TodoApplication) context.getApplicationContext();
    }

    @NonNull
    public TodoAppCore getTodoApp() {
        assert appTodo != null;
        return appTodo;
    }
}
