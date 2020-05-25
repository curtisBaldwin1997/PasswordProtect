package fv017739.PasswordProtect;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Class to check biometrics on users device
 */
public class CheckBiometrics {

    /**
     * Method to check SDK version
     *
     * @return
     */
    public static boolean BiometricPrompt() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P); //Check i Buildversion is equal to or greater than the fingerprint build codes
    }

    /**
     * Method to check if the device has a fingerprint scanner
     *
     * @param context
     * @return
     */
    public static boolean HardwareSupported(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context); //Check device for fingerprint scanner
        return fingerprintManager.isHardwareDetected(); //If detected return hardware is detected.
    }

    /**
     * Method to check if device has a stored fingerprint on device
     *
     * @param context
     * @return
     */
    public static boolean FingerprintAvailable(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context); //Check if device has stored fingerprints
        return fingerprintManager.hasEnrolledFingerprints(); //If detected return method
    }

    /**
     * Method to check if fingerprint grants authorisation
     *
     * @param context
     * @return
     */
    public static boolean PermissionAuthorised(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == //Check permissions
                PackageManager.PERMISSION_GRANTED; //If permission granted, allow authorisation
    }
}



