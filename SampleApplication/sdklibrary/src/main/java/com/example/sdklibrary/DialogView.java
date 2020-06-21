package com.example.sdklibrary;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class DialogView extends ConstraintLayout {
    private Context mContext;
    public DialogView(Context context) {
        super(context);
        mContext = context;
    }

    public DialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public DialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findViewById(R.id.button_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConstraintDialog.UPDATE_VIEW_INTENT);
                intent.putExtra(ConstraintDialog.UPDATE_VIEW_RESOURCE_ID, R.layout.first_view_layout);
                LocalBroadcastManager.getInstance(mContext.getApplicationContext()).sendBroadcast(intent);
            }
        });
    }
}
