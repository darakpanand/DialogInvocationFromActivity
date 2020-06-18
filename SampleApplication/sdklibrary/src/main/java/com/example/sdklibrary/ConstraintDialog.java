package com.example.sdklibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ConstraintDialog extends Dialog {
    public ConstraintDialog(@NonNull IActivitySpanProvider spanProvider) {
        super(spanProvider.getActivityContext());
        View view = View.inflate(spanProvider.getActivityContext(), R.layout.dual_screen_layout, null);
        this.setContentView(view);

        adjustLayoutForDualScreenActivity(spanProvider, view);
        final RelativeLayout contentLayout = view.findViewById(R.id.dual_screen_content);
        LayoutInflater.from(spanProvider.getActivityContext().getBaseContext()).inflate(R.layout.dialog_layout, contentLayout);

    }

    private void adjustLayoutForDualScreenActivity(IActivitySpanProvider spanProvider, View view) {
        int rotation = getRotation(spanProvider.getActivityContext());
        boolean isAppSpanned = isAppSpanned(spanProvider);
        boolean isHorizontal = rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180;

        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.connect(R.id.dual_screen_content, ConstraintSet.LEFT, R.id.dual_screen_layout, ConstraintSet.LEFT, 0);
        constraintSet.connect(R.id.dual_screen_content, ConstraintSet.RIGHT, R.id.dual_screen_layout, ConstraintSet.RIGHT, 0);
        constraintSet.connect(R.id.dual_screen_content, ConstraintSet.TOP, R.id.dual_screen_layout, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.dual_screen_content, ConstraintSet.BOTTOM, R.id.dual_screen_layout, ConstraintSet.BOTTOM, 0);

        constraintSet.connect(R.id.dual_screen_empty_view, ConstraintSet.LEFT, R.id.dual_screen_layout, ConstraintSet.LEFT, 0);
        constraintSet.connect(R.id.dual_screen_empty_view, ConstraintSet.RIGHT, R.id.dual_screen_layout, ConstraintSet.RIGHT, 0);
        constraintSet.connect(R.id.dual_screen_empty_view, ConstraintSet.TOP, R.id.dual_screen_layout, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.dual_screen_empty_view, ConstraintSet.BOTTOM, R.id.dual_screen_layout, ConstraintSet.BOTTOM, 0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (isAppSpanned) {
            if (isHorizontal) {
                int duoHingeWidth = spanProvider.getHinge().width() / 2;

                // WebView is on the right.
                constraintSet.connect(R.id.dual_screen_content, ConstraintSet.LEFT, R.id.vertical_guideline, ConstraintSet.RIGHT, duoHingeWidth);

                // Empty view is on the left.
                constraintSet.connect(R.id.dual_screen_empty_view, ConstraintSet.RIGHT, R.id.vertical_guideline, ConstraintSet.LEFT, 0);
            } else {
                int duoHingeWidth = spanProvider.getHinge().height() / 2;

                // WebView is on the top.
                constraintSet.connect(R.id.dual_screen_content, ConstraintSet.BOTTOM, R.id.horizontal_guideline, ConstraintSet.TOP, duoHingeWidth);

                // Empty view is in the bottom.
                constraintSet.connect(R.id.dual_screen_empty_view, ConstraintSet.TOP, R.id.horizontal_guideline, ConstraintSet.BOTTOM, 0);

                // In spanned vertical mode, keyboard will always be on the lower screen.
                // This means we do not need to shrink the webview.
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            }
        } else {
            // Shrink empty view. If constraint is not set, then its size will be (0,0).
            constraintSet.clear(R.id.dual_screen_empty_view);
        }
        Point size = new Point();
        spanProvider.getActivityContext().getWindowManager().getDefaultDisplay().getSize(size);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, (int)(size.y * 0.75f));
        final ConstraintLayout dualScreenLayout = view.findViewById(R.id.dual_screen_layout);
        dualScreenLayout.setConstraintSet(constraintSet);
    }

    /**
     * Returns true if the app is being spanned across two screens.
     */
    public boolean isAppSpanned(final IActivitySpanProvider spanProvider) {
        if (!isDualScreenDevice(spanProvider.getActivityContext())) {
            return false;
        }

        Rect hinge = spanProvider.getHinge();
        Rect windowRect = getWindowRect(spanProvider.getActivityContext());

        if (windowRect.width() > 0 && windowRect.height() > 0) {
            // The windowRect doesn't intersect hinge
            return hinge.intersect(windowRect);
        }

        return false;
    }

    /**
     * Get the device's rotation.
     *
     * @return Surface.ROTATION_0, Surface.ROTATION_90, Surface.ROTATION_180 or Surface.ROTATION_270
     */
    public int getRotation(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int rotation = 0;
        if (wm != null) {
            rotation = wm.getDefaultDisplay().getRotation();
        }
        return rotation;
    }

    /**
     * Returns true if this device supports dual screen mode.
     */
    private boolean isDualScreenDevice(final Context context) {
        final String feature = "com.microsoft.device.display.displaymask";
        final PackageManager pm = context.getPackageManager();

        if (pm.hasSystemFeature(feature)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the area of the displaying window.
     */
    private Rect getWindowRect(final Activity activity) {
        Rect windowRect = new Rect();
        activity.getWindowManager().getDefaultDisplay().getRectSize(windowRect);
        return windowRect;
    }
}
