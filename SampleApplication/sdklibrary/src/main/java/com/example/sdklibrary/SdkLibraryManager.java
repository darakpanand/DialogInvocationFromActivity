package com.example.sdklibrary;

import android.app.Activity;

public class SdkLibraryManager {
    public static void launch(Activity activity) {
        new SampleDialog(activity).show();
    }
}
