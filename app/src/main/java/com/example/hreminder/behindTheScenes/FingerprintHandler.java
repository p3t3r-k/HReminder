package com.example.hreminder.behindTheScenes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.hreminder.activities.CalenderActivity;
import com.example.hreminder.R;

/**
 * FingerprintHandler Class extends FingerprintManager
 */
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    // You should use the CancellationSignal method whenever your app can no longer process user input, for example when your app goes
    // into the background. If you don’t use this method, then other apps will be unable to access the touch sensor, including the lockscreen!

    private final Context context;

    /**
     * constructor
     * @param mContext Context
     */
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }


    /**
     * startAuth method is responsible for starting the fingerprint authentication process
     * @param manager FingerprintManager
     * @param cryptoObject CryptoObj
     */
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    /**
     * onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters
     * @param errMsgId Error Message ID
     * @param errString Error Nessage
     */
    @Override
   public void onAuthenticationError(int errMsgId, CharSequence errString) {

        //I’m going to display the results of fingerprint authentication as a series of toasts.
        //Here, I’m creating the message that’ll be displayed if an error occurs
        Toast.makeText(context, context.getResources().getString(R.string.fail) + "\n" + errString, Toast.LENGTH_LONG).show();
    }

    /**
     * onAuthenticationFailed is called when the fingerprint doesn’t match with any of the fingerprints registered on the device
     */
    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, context.getResources().getString(R.string.fail), Toast.LENGTH_LONG).show();
    }

    /**
     * onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
     * to provide the user with as much feedback as possible this information is incorporated into toast
     * @param helpMsgId message ID
     * @param helpString help message
     */
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, context.getResources().getString(R.string.authentHelp) + "\n" + helpString, Toast.LENGTH_LONG).show();
    }

    /**
     * onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device
     * when successful go to CalenderActivity
     * @param result Result of  Authentification
     */
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        Toast.makeText(context, R.string.success, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, CalenderActivity.class);
        context.startActivity(intent);
    }

}
