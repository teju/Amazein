package com.amazein.library.helper;

import android.view.View;

public class BaseHelper {
    public static void goneView(View v) {
        if (v != null) {
            v.setVisibility(View.GONE);
        }

    }
    public static void visibleView(View v) {
        if (v != null) {
            v.setVisibility(View.VISIBLE);
        }
    }
    public boolean  isEmpty(String v) {
        if (v != null && v.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }

}
