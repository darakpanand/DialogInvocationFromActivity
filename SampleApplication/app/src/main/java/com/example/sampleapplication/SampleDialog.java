package com.example.sampleapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

public class SampleDialog extends Dialog {
    public SampleDialog(@NonNull Context context) {
        super(context);
        View view = View.inflate(context, R.layout.fragment_first, null);
        this.setContentView(view);
    }
}
