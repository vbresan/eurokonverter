package com.eurokonverter;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.view.View;
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
}
