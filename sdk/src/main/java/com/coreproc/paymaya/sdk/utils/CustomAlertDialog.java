package com.coreproc.paymaya.sdk.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CustomAlertDialog {
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alert;
    private Context context;

    public CustomAlertDialog(Context context) {
        this.context = context;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Processing your card...")
                .setCancelable(false);

        alert = alertDialogBuilder.create();
    }

    public void show() {
        Window window = alert.getWindow();
        if (window != null) {
            // the important stuff..
            window.setType(WindowManager.LayoutParams.TYPE_TOAST);
            alert.show();
        } else
            Toast.makeText(context, "Processing your card...", Toast.LENGTH_LONG).show();
    }

    public void hide() {
        Window window = alert.getWindow();
        if (window != null) {
            alert.dismiss();
        } else
            Toast.makeText(context, "Processing your card...", Toast.LENGTH_LONG).show();
    }
}