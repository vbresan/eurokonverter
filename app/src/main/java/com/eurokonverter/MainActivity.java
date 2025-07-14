package com.eurokonverter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eurokonverter.util.CustomScrollingMovementMethod;
import com.eurokonverter.util.CustomScrollingMovementMethod.Callback;
import com.eurokonverter.util.TabListenerActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

/**
 *
 */
public class MainActivity extends TabListenerActivity {

    /**
     *
     */
    class Tab {

        private static final int CONVERSION  = 0;
        private static final int CALCULATION = 1;

        private TextView inputView;
        private TextView outputView;

        /**
         *
         */
        private void convert() {

            double value = getDouble(inputView);
            if (inputView.getId() == R.id.textViewCurrencyOther) {
                value /= conversionRate;
            } else {
                value *= conversionRate;
            }

            setDouble(outputView, value);
            scrollToEnd(outputView);
        }

        /**
         *
         */
        private void calculate() {

            try {
                double cash   = getDouble(findViewById(R.id.textViewCash));
                double price  = getDouble(findViewById(R.id.textViewPrice));
                double change = (cash - price) / conversionRate;

                if (change > 0) {
                    setDouble(outputView, change);
                    scrollToEnd(outputView);
                } else {
                    outputView.setText("");
                }
            } catch (Exception e) {
                outputView.setText("");
            }
        }
    }

    private int         selectedTab;
    private final Tab[] tabs = { new Tab(), new Tab() };

    private double conversionRate = 7.53450;


    /**
     *
     * @param textView
     */
    private void scrollToEnd(TextView textView) {

        textView.append("");
        Selection.setSelection(textView.getEditableText(), textView.length());
    }

    /**
     *
     */
    private void setInputViewScrolling(int id, Callback callback) {

        TextView inputView = findViewById(id);
        if (inputView == null) {
            return;
        }

        inputView.setMovementMethod(new CustomScrollingMovementMethod(this, callback));
        scrollToEnd(inputView);
    }

    /**
     *
     * @param layoutId
     */
    private void setSelectedCurrencyView(int layoutId) {

        LinearLayout other = findViewById(R.id.linearLayoutCurrencyOther);
        LinearLayout euro  = findViewById(R.id.linearLayoutCurrencyEuro);

        if (other == null || euro == null) {
            return;
        }

        if (layoutId == R.id.linearLayoutCurrencyOther) {
            euro.setBackgroundColor(Color.TRANSPARENT);
            other.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));

            tabs[Tab.CONVERSION].inputView  = other.findViewById(R.id.textViewCurrencyOther);
            tabs[Tab.CONVERSION].outputView = euro.findViewById(R.id.textViewCurrencyEuro);
        } else {
            other.setBackgroundColor(Color.TRANSPARENT);
            euro.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));

            tabs[Tab.CONVERSION].inputView  = euro.findViewById(R.id.textViewCurrencyEuro);
            tabs[Tab.CONVERSION].outputView = other.findViewById(R.id.textViewCurrencyOther);
        }
    }

    /**
     *
     * @param layoutId
     */
    private void setSelectedChangeView(int layoutId) {

        LinearLayout cash  = findViewById(R.id.linearLayoutCash);
        LinearLayout price = findViewById(R.id.linearLayoutPrice);

        if (cash == null || price == null) {
            return;
        }

        if (layoutId == R.id.linearLayoutCash) {
            price.setBackgroundColor(Color.TRANSPARENT);
            cash.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));
            tabs[Tab.CALCULATION].inputView = cash.findViewById(R.id.textViewCash);
        } else {
            cash.setBackgroundColor(Color.TRANSPARENT);
            price.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));
            tabs[Tab.CALCULATION].inputView = price.findViewById(R.id.textViewPrice);
        }
    }

    /**
     *
     * @param textView
     * @return
     */
    private double getDouble(TextView textView) {

        String string = textView.getText().toString().replaceAll("\\.", "").replaceAll(",", ".");
        return Double.parseDouble(string);
    }

    /**
     *
     * @param textView
     * @param value
     */
    private void setDouble(TextView textView, double value) {

        if (value == 0) {
            textView.setText("0");
            return;
        }

        textView.setText(String.format(Locale.GERMANY, "%,.2f", value));
    }

    /**
     *
     * @param textView
     * @param value
     */
    private void setDoubleNoDecimals(TextView textView, double value) {

        if (value == 0) {
            textView.setText("0");
            return;
        }

        String string = String.format(Locale.GERMANY, "%,.2f", value);

        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }

        if (string.endsWith(",")) {
            string = string.substring(0, string.length() - 1);
        }

        textView.setText(string);
    }

    /**
     *
     */
    private void setTabListener() {

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        if (tabLayout != null) {
            tabLayout.addOnTabSelectedListener(this);
        }
    }

    /**
     *
     * @return
     */
    private int getPreselectedCurrencyView() {

        int[] layoutIds = {
            R.id.linearLayoutCurrencyOther,
            R.id.linearLayoutCurrencyEuro
        };

        int index =
            getResources()
                .getInteger(R.integer.preselected_currency_view_index);

        return layoutIds[index];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionRate = Double.parseDouble(getString(R.string.conversion_rate));

        setInputViewScrolling(R.id.textViewCurrencyOther, this::onCurrencyViewSelected);
        setInputViewScrolling(R.id.textViewCurrencyEuro,  this::onCurrencyViewSelected);

        setInputViewScrolling(R.id.textViewCash,   this::onChangeViewSelected);
        setInputViewScrolling(R.id.textViewPrice,  this::onChangeViewSelected);
        setInputViewScrolling(R.id.textViewChange, this::onChangeViewSelected);

        setSelectedCurrencyView(getPreselectedCurrencyView());
        setSelectedChangeView(R.id.linearLayoutCash);

        tabs[Tab.CALCULATION].outputView = findViewById(R.id.textViewChange);
        setTabListener();

        new FlavorSpecific(this).showAd();
    }

    /**
     *
     * @param view
     */
    public void onCurrencyViewSelected(View view) {
        setSelectedCurrencyView(view.getId());
    }

    /**
     *
     * @param view
     */
    public void onChangeViewSelected(View view) {
        setSelectedChangeView(view.getId());
    }

    /**
     *
     * @param view
     */
    public void onKeyPressed(View view) {

        TextView inputView = tabs[selectedTab].inputView;
        if (inputView == null) {
            return;
        }

        char key = ((Button) view).getText().toString().charAt(0);
        if (key == 'C') {
            inputView.setText(selectedTab == Tab.CONVERSION ? "0" : "");
        } else if (key == 8592) {

            int length = inputView.length();
            if (length > 1) {
                inputView.setText(inputView.getText().subSequence(0, length - 1));
            } else {
                inputView.setText(selectedTab == Tab.CONVERSION ? "0" : "");
            }
        } else if (key == ',') {
            if (!inputView.getText().toString().contains(",")) {
                inputView.append("" + key);
            }
        } else {

            String text = inputView.getText().toString();
            if (text.contains(",") && text.indexOf(',') == text.length() - 3) {
                return;
            }

            if (inputView.length() == 1 && text.charAt(0) == '0') {
                inputView.setText("" + key);
            } else {
                inputView.append("" + key);
            }
        }

        String string = inputView.getText().toString();
        if (!TextUtils.isEmpty(string) && !string.contains(",")) {
            setDoubleNoDecimals(inputView, getDouble(inputView));
        }

        scrollToEnd(inputView);
        if (selectedTab == Tab.CONVERSION) {
            tabs[selectedTab].convert();
        } else {
            tabs[selectedTab].calculate();
        }
    }

    /**
     *
     * @param view
     */
    public void onShare(View view) {

        Intent intent = new Intent(this, ShareActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        View conversion  = findViewById(R.id.linearLayoutContainerConversion);
        View calculation = findViewById(R.id.linearLayoutContainerCalculation);

        if (conversion == null || calculation == null) {
            return;
        }

        selectedTab = tab.getPosition();
        if (selectedTab == Tab.CONVERSION) {
            calculation.setVisibility(View.INVISIBLE);
            conversion.setVisibility(View.VISIBLE);
        } else {
            conversion.setVisibility(View.INVISIBLE);
            calculation.setVisibility(View.VISIBLE);
        }
    }
}