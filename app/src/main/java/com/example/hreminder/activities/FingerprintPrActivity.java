package com.example.hreminder.activities;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.FingerprintHandler;
import com.example.hreminder.database.DbHelper;
import com.example.hreminder.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Activity to use FingerprintSensor to log into Account
 */
public class FingerprintPrActivity extends BaseActivity {
    // Declare a string variable for the key we’re going to use in our fingerprint authentication
    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private boolean allRequirements = true;


    /**
     * get instance of KeyguardManager, FingerprintManager and DB
     * check if all requirements are fulfilled, if yes generate Key and init Cipher
     * @param savedInstanceState saved Instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_pr);

        //Get an instance of KeyguardManager and FingerprintManager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        TextView textView = findViewById(R.id.textViewInfo);

        DbHelper db = new DbHelper(this);
        if(!db.getAnyUser()){
                textView.setText(R.string.notRegistered);
                allRequirements = false;
        }

        if (db.checkIfLogExists()){
            String lastUser = db.getLastUserID();
            if (!db.checkIfProfileExists(lastUser)) {
                textView.setText(R.string.notRegistered);
                allRequirements = false;
            }
        }

        //Check whether the device has a fingerprint sensor
        if (!fingerprintManager.isHardwareDetected()) {
            // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality
            textView.append("\n\n"+getApplicationContext().getResources().getString(R.string.noHardware));
            allRequirements = false;
        }

        //Check whether the user has granted your app the USE_FINGERPRINT permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // If your app doesn't have this permission, then display the following text//
            textView.append("\n\n"+getApplicationContext().getResources().getString(R.string.noPermission));
            allRequirements = false;
        }

        //Check that the user has registered at least one fingerprint
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            // If the user hasn’t configured any fingerprints, then display the following message
            textView.append("\n\n"+getApplicationContext().getResources().getString(R.string.noFingerprint));
            allRequirements = false;
        }

        //Check that the lockscreen is secured
        if (!keyguardManager.isKeyguardSecure()) {
            // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text
            textView.append("\n\n"+getApplicationContext().getResources().getString(R.string.noLockscreen));
            allRequirements = false;
        }
        if(allRequirements) {
            try {
                generateKey();
            } catch (FingerprintException e) {
                e.printStackTrace();
            }if (initCipher()) {
                //If the cipher is initialized successfully, then create a CryptoObject instance
                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);

                FingerprintHandler helper = new FingerprintHandler(this);
                helper.startAuth(fingerprintManager, cryptoObject);
            }
        }
    }

    /**
     *generateKey method to gain access to the Android keystore and generate the encryption key
     * @throws FingerprintException Exception
     */
    private void generateKey() throws FingerprintException {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Generate the key//
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize an empty KeyStore//
            keyStore.load(null);

            //Initialize the KeyGenerator//
            keyGenerator.init(new

                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key//
            keyGenerator.generateKey();
        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    /**
     * method to initialize cipher
     * @return boolean
     */
    private boolean initCipher() {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {

            //Return false if cipher initialization failed
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    /**
     * FingerprintException
     */
    private class FingerprintException extends Exception {
        FingerprintException(Exception e) {
            super(e);
        }
    }

}



