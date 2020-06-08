package com.example.sdklibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

class SampleDialog extends Dialog {
    public SampleDialog(@NonNull Activity activity) {
        super(activity);
        View view = View.inflate(activity, R.layout.dialog_layout, null);
        this.setContentView(view);
    }
}
