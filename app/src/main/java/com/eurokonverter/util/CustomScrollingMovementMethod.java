package com.eurokonverter.util;

import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.eurokonverter.MainActivity;
import com.eurokonverter.R;

/**
 *
 */
public class CustomScrollingMovementMethod extends ScrollingMovementMethod {

    private       TextView        textView;
    private final GestureDetector gestureDetector;

    /**
     *
     * @param activity
     */
    public CustomScrollingMovementMethod(MainActivity activity) {

        gestureDetector = new GestureDetector(activity, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                if (textView == null) {
                    return super.onSingleTapUp(e);
                }

                View parent = (View) textView.getParent();
                if (parent == null) {
                    return super.onSingleTapUp(e);
                }

                int id = parent.getId();
                if (id == R.id.linearLayoutHRK || id == R.id.linearLayoutEUR) {
                    activity.onCurrencyViewSelected(parent);
                } else {
                    activity.onChangeViewSelected(parent);
                }

                return super.onSingleTapUp(e);
            }
        });
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        textView = widget;
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(widget, buffer, event);
    }
}
