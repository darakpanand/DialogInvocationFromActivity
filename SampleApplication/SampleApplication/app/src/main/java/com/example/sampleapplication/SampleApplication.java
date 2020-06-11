package com.example.sampleapplication;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.sdklibrary.IActivitySpanProvider;
import com.example.sdklibrary.SdkLibraryManager;

public class SampleApplication extends Application {
    private Activity mActivity;
    private BroadcastReceiver mBroadcastReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Anand", "Darak");
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Anand", "isMainThread : " + (Looper.getMainLooper().getThread() == Thread.currentThread()));
                        SdkLibraryManager.launch((IActivitySpanProvider) mActivity);
                    }
                }, 10000);
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBroadcastReceiver, new IntentFilter("sample"));

    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }
}
