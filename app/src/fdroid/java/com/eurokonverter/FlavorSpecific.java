package com.eurokonverter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class FlavorSpecific {

    private final Activity activity;

    private void donate(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(R.string.DonacijeDialogNaslov);
        builder.setMessage(R.string.DonacijeDialogSadrzaj);

        builder.setPositiveButton(R.string.Nastavi, (dialog, which) -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(activity.getString(R.string.donation_url)));
            activity.startActivity(intent);

            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public FlavorSpecific(Activity activity) {
        this.activity = activity;
    }

    public void enableView() {

        Button button = activity.findViewById(R.id.buttonDonate);
        if (button != null) {
            button.setOnClickListener(this::donate);
        }
    }

    public void showAd() {
        // do nothing
    }
}
