package fv017739.PasswordProtect;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@TargetApi(Build.VERSION_CODES.M)
/**
 *Class to handle encryption and decryption of biometric authentication
 */
public class BioManagerHandler {

    private static final String KEY_NAME = UUID.randomUUID().toString(); //Define random UID

    private Cipher cipher; //Define variable for cipher with use in encryption
    private KeyStore keyStore; //Define variable to store the key
    private KeyGenerator keyGenerator; //Define variable to generate the key
    private FingerprintManagerCompat.CryptoObject cryptoObject; //Define object


    protected Context context;

    protected String title; //Create title
    protected String subtitle; //Create subtitle
    protected String description; //Create description
    protected String negativeButtonText; //Create button text
    private BioDialog bioDialog; //Biodialog interface
    protected CancellationSignal mCancellationSignal = new CancellationSignal(); //Cancel authentication

    /**
     * Method to display BiometricPromtV23
     *
     * @param biometricCallback
     */
    public void displayBiometricPromptV23(final BioCallback biometricCallback) {
        generateKey(); //generate random key by calling method

        if (initCipher()) {

            cryptoObject = new FingerprintManagerCompat.CryptoObject(cipher); //encrypt fingerprint
            FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context); //call fingerprint manager

            fingerprintManagerCompat.authenticate(cryptoObject, 0, mCancellationSignal, //authenticate fingerprint
                    new FingerprintManagerCompat.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errMsgId, CharSequence errString) {
                            super.onAuthenticationError(errMsgId, errString);   //If there is an error during authentication call authentication error
                            updateStatus(String.valueOf(errString));           // Display error to user
                            biometricCallback.onAuthenticationError(errMsgId, errString);
                        }

                        @Override
                        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                            super.onAuthenticationHelp(helpMsgId, helpString);   //If user requires help call authentication help
                            updateStatus(String.valueOf(helpString));           //Display help message to use
                            biometricCallback.onAuthenticationHelp(helpMsgId, helpString);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            dismissDialog();                                //If authentication successful allow authentication into app
                            biometricCallback.onAuthenticationSuccessful();
                        }


                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();             //If authentication unsuccessful inform user
                            updateStatus(context.getString(R.string.bioFailed)); //Finger print not recognised
                            biometricCallback.onAuthenticationFailed();
                        }
                    }, null);

            displayBiometricDialog(biometricCallback);
        }
    }


    /**
     * Set view of biometric dialog box
     *
     * @param biometricCallback
     */
    private void displayBiometricDialog(final BioCallback biometricCallback) {
        bioDialog = new BioDialog(context, biometricCallback);
        bioDialog.setTitle(title);  //Define title
        bioDialog.setSubtitle(subtitle); //Define subtitle
        bioDialog.setDescription(description); //Define description
        bioDialog.setButtonText(negativeButtonText); //Define button text
        bioDialog.show(); //Show dialog
    }


    /**
     * Method to remove dialog
     */
    private void dismissDialog() {
        if (bioDialog != null) { //If dialog not equal to null
            bioDialog.dismiss(); //Dismiss dialog
        }
    }

    /**
     * Method to update the status of dialog
     *
     * @param status
     */
    private void updateStatus(String status) {
        if (bioDialog != null) {    //If dialog not equal to null
            bioDialog.updateStatus(status); //Update dialog status
        }
    }

    /**
     * Method to generate random key
     */
    private void generateKey() {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore"); //Get a key from "android key store"
            keyStore.load(null);

            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"); //generate key with AES encrpytion
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT) //Encryption features
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()); //Build encryption key

            keyGenerator.generateKey();

        } catch (KeyStoreException //Catch any errors durying key generation
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace(); //Display to stack trace
        }
    }


    /**
     * Encryption method using Cipher
     *
     * @return
     */
    private boolean initCipher() {
        try {
            cipher = Cipher.getInstance( //Retrive cipher
                    KeyProperties.KEY_ALGORITHM_AES + "/" //Apply these properties to cipher
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException | //Catch any errors
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e); //Throw exception if unable to retrieve cipher
        }

        try {
            keyStore.load(null); //Load secret key
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, //Retirve key
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key); //Encrypt key
            return true;


        } catch (KeyPermanentlyInvalidatedException e) { //Catch errors
            return false; //If error return false

        } catch (KeyStoreException | CertificateException //Catch errors
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {

            throw new RuntimeException("Failed to init Cipher", e); //Throw exception if unable to retrieve cipher
        }
    }
}
