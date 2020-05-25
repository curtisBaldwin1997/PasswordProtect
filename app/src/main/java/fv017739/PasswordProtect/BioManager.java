package fv017739.PasswordProtect;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;

/**
 * Class to handle authentication and dialog
 */
public class BioManager extends BioManagerHandler {

    protected CancellationSignal mCancellationSignal = new CancellationSignal(); //Cancelation variable

    /**
     * Conents of Bio dialog
     *
     * @param biometricBuilder
     */
    protected BioManager(final BiometricBuilder biometricBuilder) {
        this.context = biometricBuilder.context; //adding context to biometric builder
        this.title = biometricBuilder.title; //adding title to biometric builder
        this.subtitle = biometricBuilder.subtitle; //adding subtitle to biometric builder
        this.description = biometricBuilder.description; //adding discription to biometric builder
        this.negativeButtonText = biometricBuilder.negativeButtonText; //adding button text to biometric builder
    }

    /**
     * handle authenticate of biocallback
     *
     * @param biometricCallback
     */
    public void authenticate(@NonNull final BioCallback biometricCallback) {
        //if title = null call error
        if (title == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog title cannot be null");
            return;
        }

        //if subtitle = null call error
        if (subtitle == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog subtitle cannot be null");
            return;
        }

        //if description = null call error
        if (description == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog description cannot be null");
            return;
        }
        //if button text = null call error
        if (negativeButtonText == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog negative button text cannot be null");
            return;
        }

        //if biometrics to authurised dont grant access
        if (!CheckBiometrics.PermissionAuthorised(context)) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted();
            return;
        }
        //if hardware not supported dont allow biometrics
        if (!CheckBiometrics.HardwareSupported(context)) {
            biometricCallback.onBiometricAuthenticationNotSupported();
            return;
        }
        //if fingerprint not available dont allow authentication
        if (!CheckBiometrics.FingerprintAvailable(context)) {
            biometricCallback.onBiometricAuthenticationNotAvailable();
            return;
        }

        displayBiometricDialog(biometricCallback); //show biometric dialog
    }

    /**
     * Method to cancel authentication
     */
    public void cancelAuthentication() {
        if (CheckBiometrics.BiometricPrompt()) {
            if (!mCancellationSignal.isCanceled()) //If biometrics is canceld cancel process
                mCancellationSignal.cancel();
        } else {
            if (!mCancellationSignal.isCanceled()) //If biometrics is canceld cancel process
                mCancellationSignal.cancel();
        }
    }


    /**
     * Method to display dialog for authentication
     *
     * @param biometricCallback
     */
    private void displayBiometricDialog(BioCallback biometricCallback) {
        if (CheckBiometrics.BiometricPrompt()) {
            displayBiometricPrompt(biometricCallback); //if biometrics is prompted bring up dialog
        } else {
            displayBiometricPromptV23(biometricCallback); //else display promptv23
        }
    }


    /**
     * Method to display biometric prompt
     *
     * @param biometricCallback
     */
    @TargetApi(Build.VERSION_CODES.P)
    private void displayBiometricPrompt(final BioCallback biometricCallback) {
        new BiometricPrompt.Builder(context)
                .setTitle(title) //set the title
                .setSubtitle(subtitle) //set the subtitle
                .setDescription(description) //set the description
                .setNegativeButton(negativeButtonText, context.getMainExecutor(), new DialogInterface.OnClickListener() { //set the button text
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        biometricCallback.onAuthenticationCancelled(); //if users decides to cancel authentication carry out request
                    }
                })
                .build() //build the biometric prompt
                .authenticate(mCancellationSignal, context.getMainExecutor(),
                        new Callback(biometricCallback));
    }

    /**
     * Biometric builder class for the contents of prompt
     */
    public static class BiometricBuilder {

        private String title; //declare title as string
        private String subtitle; //declare subtitle as string
        private String description; //declare description as string
        private String negativeButtonText; //declare button as string

        private Context context; //define context

        public BiometricBuilder(Context context) {
            this.context = context; //set context
        }

        public BiometricBuilder setTitle(@NonNull final String title) {
            this.title = title; //set title
            return this;
        }

        public BiometricBuilder setSubtitle(@NonNull final String subtitle) {
            this.subtitle = subtitle; //set subtitle
            return this;
        }

        public BiometricBuilder setDescription(@NonNull final String description) {
            this.description = description; //set description
            return this;
        }


        public BiometricBuilder setNegativeButtonText(@NonNull final String negativeButtonText) {
            this.negativeButtonText = negativeButtonText; //set button text
            return this;
        }

        public BioManager build() {
            return new BioManager(this); //on build return new bio manager
        }
    }
}
