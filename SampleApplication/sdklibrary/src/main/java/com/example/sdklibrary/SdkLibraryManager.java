package com.example.sdklibrary;

public class SdkLibraryManager {
    public static void launch(IActivitySpanProvider spanProvider) {
        new ConstraintDialog(spanProvider).show();
    }
}
