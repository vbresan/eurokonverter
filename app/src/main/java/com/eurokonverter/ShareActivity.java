package com.eurokonverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 */
public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
    }

    /**
     *
     * @param view
     */
    public void onSend(View view) {

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

    /**
     *
     * @param view
     */
    public void onDone(View view) {
        finish();
    }
}
