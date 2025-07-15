package com.eurokonverter;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class FlavorSpecific {

    private static final int MIN_AD_HEIGHT = 50;
    private static final int MIN_AD_PADDING = 16;
    private static final int MIN_AD_REQUIRED_SPACE =
        MIN_AD_HEIGHT + MIN_AD_PADDING;

    private final Activity activity;

    private void doShowAd(FrameLayout parent, int widthDp, int heightDp) {

        AdSize adSize = AdSize.getInlineAdaptiveBannerAdSize(widthDp, heightDp);
        AdView bannerView = new AdView(activity);
        bannerView.setAdUnitId(activity.getString(R.string.admob_ad_id));
        bannerView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();
        bannerView.loadAd(adRequest);

        LinearLayout adContainer = new LinearLayout(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP;

        adContainer.setLayoutParams(params);
        adContainer.setOrientation(LinearLayout.VERTICAL);

        adContainer.addView(bannerView);
        parent.addView(adContainer);
    }

    public FlavorSpecific(Activity activity) {
        this.activity = activity;
    }

    public void enableView() {

        TextView textView = activity.findViewById(R.id.textViewPrivacyPolicy);
        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public void showAd() {

        if (!activity.getResources().getBoolean(R.bool.show_ads)) {
            return;
        }

        FrameLayout view = activity.findViewById(R.id.frameLayoutImageHeader);
        if (view == null) {
            return;
        }

        view.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {

                private float getDensity() {
                    return activity.getResources().getDisplayMetrics().density;
                }

                @SuppressWarnings("UnnecessaryLocalVariable")
                private int getWidth(View view) {

                    int widthPx = view.getWidth();
                    int widthDp = (int) (widthPx / getDensity());

                    return widthDp;
                }

                @SuppressWarnings("UnnecessaryLocalVariable")
                private int getHight(View view) {

                    int heightPx = view.getHeight();
                    int heightDp = (int) (heightPx / getDensity());

                    return heightDp;
                }

                @Override
                public void onGlobalLayout() {

                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    int widthDp  = getWidth(view);
                    int heightDp = getHight(view);

                    if (heightDp >= MIN_AD_REQUIRED_SPACE) {
                        doShowAd(view, widthDp, heightDp - MIN_AD_PADDING);
                    }
                }
            });
    }
}
