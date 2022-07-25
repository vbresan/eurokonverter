package com.eurokonverter.util;

import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.eurokonverter.MainActivity;

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
                activity.onCurrencySelected((View) textView.getParent());
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
