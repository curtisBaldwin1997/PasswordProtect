package fv017739.PasswordProtect;

/**
 * Interface for fingerprint authentication
 */
public interface BioCallback {


    void onBiometricAuthenticationNotSupported(); //calls method to see if authentication supported on device

    void onBiometricAuthenticationNotAvailable(); //calls method to check if device includes fingerprint scanner

    void onBiometricAuthenticationPermissionNotGranted(); //calls method to check if fingerprint authentication granted

    void onBiometricAuthenticationInternalError(String error); //calls method to check for error


    void onAuthenticationFailed(); //calls method to check for authentication failure

    void onAuthenticationCancelled(); //calls method to check if fingerprint authentication cancled

    void onAuthenticationSuccessful(); //calls method for authetication success

    void onAuthenticationHelp(int helpCode, CharSequence helpString);

    void onAuthenticationError(int errorCode, CharSequence errString);
}

