package com.eurokonverter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eurokonverter.util.CustomScrollingMovementMethod;

import java.util.Locale;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private final double CONVERSION_RATE = 7.53450;

    private LinearLayout selectedView;
    private TextView     inputView;
    private TextView     outputView;

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
    private void setInputViewScrolling(int id) {

        TextView inputView = findViewById(id);
        if (inputView == null) {
            return;
        }

        inputView.setMovementMethod(new CustomScrollingMovementMethod(this));
        scrollToEnd(inputView);
    }

    /**
     *
     * @param layoutId
     */
    private void setSelectedView(int layoutId) {

        LinearLayout hrk = findViewById(R.id.linearLayoutHRK);
        LinearLayout eur = findViewById(R.id.linearLayoutEUR);

        if (hrk == null || eur == null) {
            return;
        }

        if (layoutId == R.id.linearLayoutHRK) {
            eur.setBackgroundColor(Color.TRANSPARENT);
            hrk.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));

            inputView    = hrk.findViewById(R.id.textViewHRK);
            outputView   = eur.findViewById(R.id.textViewEUR);
            selectedView = hrk;
        } else {
            hrk.setBackgroundColor(Color.TRANSPARENT);
            eur.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));

            inputView    = eur.findViewById(R.id.textViewEUR);
            outputView   = hrk.findViewById(R.id.textViewHRK);
            selectedView = eur;
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
    private void convert() {

        double value = getDouble(inputView);
        if (selectedView.getId() == R.id.linearLayoutHRK) {
            value /= CONVERSION_RATE;
        } else {
            value *= CONVERSION_RATE;
        }

        setDouble(outputView, value);
        scrollToEnd(outputView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInputViewScrolling(R.id.textViewHRK);
        setInputViewScrolling(R.id.textViewEUR);

        setSelectedView(R.id.linearLayoutHRK);
    }

    /**
     *
     * @param view
     */
    public void onCurrencySelected(View view) {
        setSelectedView(view.getId());
    }

    /**
     *
     * @param view
     */
    public void onKeyPressed(View view) {

        if (selectedView == null || inputView == null) {
            return;
        }

        char key = ((Button) view).getText().toString().charAt(0);
        if (key == 'C') {
            inputView.setText("0");
        } else if (key == 8592) {

            int length = inputView.length();
            if (length > 1) {
                inputView.setText(inputView.getText().subSequence(0, length - 1));
            } else {
                inputView.setText("0");
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

        if (!inputView.getText().toString().contains(",")) {
            setDoubleNoDecimals(inputView, getDouble(inputView));
        }

        scrollToEnd(inputView);
        convert();
    }

    /**
     *
     * @param view
     */
    public void onShareApp(View view) {

        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.ZaKonverzijuUpotrebljavam) +
            getString(R.string.distribution)
        );
        startActivity(intent);
    }
}