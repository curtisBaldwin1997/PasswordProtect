package fv017739.PasswordProtect;

import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Class to handle errors
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class Callback extends BiometricPrompt.AuthenticationCallback {

    /**
     * Method to call BioCallBack interface
     */
    private BioCallback biometricCallback;

    public Callback(BioCallback bioCallback) {
        this.biometricCallback = bioCallback; //Define biometric call back is equal to biocallback interface
    }

    /**
     * Method to check if authentication successful
     *
     * @param result
     */
    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        biometricCallback.onAuthenticationSuccessful(); //If authentication successful call this method
    }

    /**
     * Method to help user with authentication
     *
     * @param helpCode
     * @param helpString
     */
    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        biometricCallback.onAuthenticationHelp(helpCode, helpString); //If need help with authentication call help code and help string
    }

    /**
     * Method to display error during authentication
     *
     * @param errorCode
     * @param errString
     */
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        biometricCallback.onAuthenticationError(errorCode, errString); //If error display error code and error string
    }

    /**
     * Method if authentication fails
     */
    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        biometricCallback.onAuthenticationFailed(); //If authentication fails call method
    }
}
