package fv017739.PasswordProtect;

import android.content.Context;
import android.content.Intent;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import fonts.cairoButton;

/**
 * Class to handle fingerprint authentication sign in and user choice to sign in or sign up
 */
public class MainSignActivity extends AppCompatActivity implements View.OnClickListener, BioCallback {


    cairoButton signInButton, signUpButton; //Define buttons
    BioManager fingerprintAuth; //Call Biometric authentication

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign); //Link XML layout file


        //Find buttons ID in XML file
        signInButton = findViewById(R.id.id_signIn_Button);
        signUpButton = findViewById(R.id.id_signUp_Button);

        //Let signIn and signUp listen to user clicks
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    /**
     * On click method for two buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        //Userfeed back functionality in the form of vibration
        final Vibrator vibration = (Vibrator)
                MainSignActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        //If sign in button pressed take user to Sign in screen, vibrate for 80milliseconds on press
        if (v == signInButton) {
            Intent i = new Intent(this, SignIn.class);
            startActivity(i);
            overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
            vibration.vibrate(80);
        }
        //If Sign up button pressed take user to sign up screen, vibrate for 80 milliseconds on press
        if (v == signUpButton) {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
            overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
            vibration.vibrate(80);
        }
    }

    /**
     * Method to handle user choice in the app selecting fingerprint authentication
     */
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("checkBox", MODE_PRIVATE); //Put user choice on stack and store in shared preferences
        boolean mswitch = sharedPreferences.getBoolean("Switch", false); //if user choice is false ignore
        if (mswitch == true) { //if user choice is true
            fingerPrint(); //Perform fingerprint method below
        }

    }

    /**
     * Method for fingerprint authentication
     */
    public void fingerPrint() {
        fingerprintAuth = new BioManager.BiometricBuilder(MainSignActivity.this) //Set biometric dialog to display on MainSignActivity page
                .setTitle(getString(R.string.fingerAuth_title)) //set title to login
                .setSubtitle(getString(R.string.fingerAuth_subtitle)) //Set subtitle to password protect
                .setDescription(getString(R.string.fingerAuth_description)) //Set description for what the user should do
                .setNegativeButtonText(getString(R.string.fingerAuth_button_cancel)) //set button description to cancle
                .build(); //build biodialog

        //start authentication
        fingerprintAuth.authenticate(MainSignActivity.this);
    }

    /**
     * Method for SDK version
     */
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.fingerAuth_error_sdk_not_supported), Toast.LENGTH_LONG).show(); //if version not supported return error
    }

    /**
     * Method for authentication not support
     */
    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.fingerAuth_error_hardware_not_supported), Toast.LENGTH_LONG).show(); //Error message for biometrics not supported
    }

    /**
     * Method for authentication not available
     */
    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.fingerAuth_error_fingerprint_not_available), Toast.LENGTH_LONG).show(); //Error message for Fingerprint not registered on device
    }

    /**
     * Method for authentication not granted
     */
    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.fingerAuth_error_permission_not_granted), Toast.LENGTH_LONG).show(); //Error message is fingerprint authentication not granted
    }

    /**
     * Method for authentication error
     * @param error
     */
    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show(); //Display error if occurs
    }

    /**
     * Method for if authentication fails
     */
    @Override
    public void onAuthenticationFailed() {
//        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show(); //Error message if authentication fails
    }

    /**
     * Method for authentication cancelation
     */
    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.fingerAuth_cancelled), Toast.LENGTH_LONG).show(); //If authentication canceled inform user
        fingerprintAuth.cancelAuthentication(); //Cancel authentication
    }

    /**
     * Method if authentication is successful
     */
    @Override
    public void onAuthenticationSuccessful() {
        Intent signIn = new Intent(this, HomeScreen.class); //If authentication successful Log user into app
        startActivity(signIn);
        Toast.makeText(getApplicationContext(), getString(R.string.fingerAuth_success), Toast.LENGTH_LONG).show(); //display message "log in successful"
    }

    /**
     * Method for authentication help
     * @param helpCode
     * @param helpString
     */
    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show(); //If help needed display help string
    }

    /**
     * Method to handle authentication error
     * @param errorCode
     * @param errString
     */
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
//        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show(); //If error occurs display error string
    }

}
