package fv017739.PasswordProtect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import fonts.cairoEditText;

/**
 * Class to handle phone verification
 */
public class PhoneActivity extends AppCompatActivity {


    private String phoneVerificationID; //Define a verification ID
    private cairoEditText verificationCode; //Edit text file for OTP entry
    private FirebaseAuth authentication; //Link to firebase
    private static final String TAG = "PhoneActivity"; //TAG to handle stacktrace

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone); //Set layout to relevant XML

        authentication = FirebaseAuth.getInstance(); //Initiate firebase
        verificationCode = findViewById(R.id.SmsCode); //Find ID in XML file


        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile"); //Get the mobile entry from sign up page

        //Method to send verification code
        sendVerificationCode(mobile);
        findViewById(R.id.id_SignIn_Button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //handle user click on sign up
                String code = verificationCode.getText().toString().trim(); //get the users sms code
                if (code.isEmpty() || code.length() < 6) {  //check validation
                    verificationCode.setError("Enter valid code"); //if not validated return error
                    verificationCode.requestFocus();
                    return;
                }

                //verifying the code
                verifyVerificationCode(code);
            }
        });


    }

    /**
     * @param mobile send code to users registered mobile
     */
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber( //get users mobile
                "+44" + mobile, //check it is in correct area code
                60, //send in 60 seconds
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    /**
     * Method to check SMS authentication successful
     */
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        /**
         *
         * @param phoneAuthCredential check phone credentials and retrieval of SMS code
         */
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //retriving the OTP code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //User need to enter the code sent to them
            if (code != null) {
                verificationCode.setText(code);
                //verifying the code sent
                verifyVerificationCode(code);
            }
        }

        /**
         *
         * @param e If authentication failed get exception
         */
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(PhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();  //Display error message to user
        }

        /**
         *
         * @param s
         * @param forceResendingToken Method to store ID
         */
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);


            phoneVerificationID = s;  //Store the verification ID
        }
    };

    /**
     * @param code Method to create the code and Sign the user in
     */
    private void verifyVerificationCode(String code) {
        //create the code
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationID, code);

        //sign the user in
        linkWithCredential(credential);
    }

    /**
     * @param credential Check if the user links with the correct credentials
     */
    public void linkWithCredential(AuthCredential credential) {
        authentication.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {      //On complete listener
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser(); //If task successful like phone authentication with user
                            updateUI(user);

                        } else {                                   //if unsuccessful
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(PhoneActivity.this, "Authentication failed.",    //Inform user authentication has failed
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * @param user method to take user to next page
     */
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(PhoneActivity.this, SplashRegisterScreen.class);   //If authentication successful take user to splash screen
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);     //Start new activity page
        }

    }


}



