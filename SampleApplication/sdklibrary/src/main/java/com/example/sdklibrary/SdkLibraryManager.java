package com.example.sdklibrary;

import android.app.Activity;

public class SdkLibraryManager {
    public static void launch(IActivitySpanProvider spanProvider) {
        new SampleDialog(spanProvider).show();
    }
}
