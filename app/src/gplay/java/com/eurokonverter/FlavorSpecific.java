package com.eurokonverter;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class FlavorSpecific {

    private final Activity activity;

    public FlavorSpecific(Activity activity) {
        this.activity = activity;
    }

    public void enablePrivacyPolicyLink() {

        TextView textView = activity.findViewById(R.id.textViewPrivacyPolicy);
        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
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
