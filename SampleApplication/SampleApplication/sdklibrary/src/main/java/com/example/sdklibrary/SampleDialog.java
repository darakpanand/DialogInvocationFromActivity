package com.example.sdklibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

class SampleDialog extends Dialog {
    public SampleDialog(@NonNull IActivitySpanProvider spanProvider) {
        super(spanProvider.getActivityContext());
        View view = View.inflate(spanProvider.getActivityContext(), R.layout.dialog_layout, null);
        this.setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.gravity = Gravity.TOP;
        Point topCoordinates = spanProvider.getActivityViewTopPoint();
        attrs.y = topCoordinates.y;
        attrs.x = topCoordinates.x;
        window.setAttributes(attrs);

        int height = spanProvider.getActivityViewHeight() > 0? spanProvider.getActivityViewHeight() : WindowManager.LayoutParams.MATCH_PARENT;
        int width = spanProvider.getActivityViewWidth() > 0? spanProvider.getActivityViewWidth() : WindowManager.LayoutParams.MATCH_PARENT;
        window.setLayout(width,height);
    }
}
