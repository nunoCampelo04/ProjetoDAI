package com.example.myapplication;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public static void showNoInternetMessage(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Sem conexão com a internet")
                .setCancelable(false)
                .setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (NetworkUtils.isNetworkAvailable(context)) {
                            dialog.dismiss();
                        } else {
                            showNoInternetMessage(context);
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
