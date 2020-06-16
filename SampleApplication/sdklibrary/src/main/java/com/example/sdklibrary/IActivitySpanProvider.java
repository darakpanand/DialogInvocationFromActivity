package com.example.sdklibrary;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;

public interface IActivitySpanProvider {
    //------for adal based contraint layout solution only following 2 api's are required.
    Activity getActivityContext();
    Rect getHinge();
    //-------
    int getActivityViewHeight();
    int getActivityViewWidth();
    Point getActivityViewTopPoint();
}
