package com.example.sdklibrary;

import android.content.Context;
import android.graphics.Point;

public interface IActivitySpanProvider {
    Context getActivityContext();
    int getActivityViewHeight();
    int getActivityViewWidth();
    Point getActivityViewTopPoint();
}
