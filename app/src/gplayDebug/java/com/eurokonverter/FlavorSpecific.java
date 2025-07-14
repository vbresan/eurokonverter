package com.eurokonverter;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

public class FlavorSpecific {

    private final Activity activity;

    public FlavorSpecific(Activity activity) {
        this.activity = activity;
    }

    public void showAd() {

        View view = activity.findViewById(R.id.frameLayoutImageHeader);
        if (view == null) {
            return;
        }

        view.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int heightPx = view.getHeight();
                int heightDp = (int) (heightPx / activity.getResources().getDisplayMetrics().density);
                System.out.println("===============> heightPx: " + heightPx);
                System.out.println("===============> heightDp: " + heightDp);

                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
