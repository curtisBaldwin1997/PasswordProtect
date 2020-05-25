package fv017739.PasswordProtect;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import fonts.cairoButton;
import fonts.cairoEditText;
import fonts.cairoTextView;

/**
 * Class to handle user SignIn to password manager
 */
public class SignIn extends AppCompatActivity implements View.OnClickListener {
    cairoButton mSignInButton; //Define button
    cairoEditText mEmailEditText, mPasswordEditText, mSharedSecretEditText; //Define edittext fields
    cairoTextView _forgetPasswordTextView, changePassword; //Define text views
    ImageView _back; //Define image view

    FirebaseAuth firebaseAuthenti; //Initiate firebase authentication
    FirebaseFirestore fDatasebase; //Call firebase database
    private String userID; //Define user ID
    int counter = 2; //Define counter equlas to two in order to handle user login attempts

    /**
     * Set layout and handle finding views
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in); //Relavent layout file

        mSignInButton = findViewById(R.id.loginButton); //find login button ID in XML
        mEmailEditText = findViewById(R.id.emailEditText); //Find email edit text ID in XML
        mPasswordEditText = findViewById(R.id.passwordEditText); // Find password edit text ID in XML
        mSharedSecretEditText = findViewById(R.id.sharedSecretEditText); // find shared secret ID in XML
        changePassword = findViewById(R.id.id_forgetPassword); // Find change password ID in XML

        _forgetPasswordTextView = findViewById(R.id.id_forgetPassword_TextView); //Find password text view in XML
        _back = findViewById(R.id.id_back_ImageView); //Find back image view in XML

        //Make buttons and image view listen to clicks
        mSignInButton.setOnClickListener(this);
        _forgetPasswordTextView.setOnClickListener(this);
        _back.setOnClickListener(this);
        changePassword.setOnClickListener(this);

        //Get instance of firebase database and firebase authentication
        fDatasebase = FirebaseFirestore.getInstance();
        firebaseAuthenti = FirebaseAuth.getInstance();

    }

    /**
     * Method to hand button and image view click
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        //Call vibration feature and set it this activity
        final Vibrator vibration = (Vibrator)
                SignIn.this.getSystemService(Context.VIBRATOR_SERVICE);
        if
        (v == _forgetPasswordTextView) { //if forgetPasswordTextView pressed
            returnHint();                  //Peform return hint method
            vibration.vibrate(80); //Vibrate for 80 milliseconds
        }

        if
        (v == mSignInButton) { //if sign in button pressed
            signinfunction();   //perform signin function
            vibration.vibrate(80); //vibrate for 80 milliseconds
        }

        if
        (v == _back) { //if back image view pressed
            onBackPressed(); //perform on back pressed function
            vibration.vibrate(80); //vibrate for 80 milliseconds
        }
        if
        (v == changePassword) { //if change password pressed
            Intent myintent = new Intent(SignIn.this, ChangePassword.class);
            startActivity(myintent); //take user to changpassword activity and launch

        }
    }

    /**
     * Method to return hint for user
     */
    private void returnHint() {


        userID = firebaseAuthenti.getCurrentUser().getUid(); //get users current UID
        final DocumentReference documentReference = fDatasebase.collection("Users").document(userID); //Find if user ID is stored in database
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { //on Complete for validation

                if (task.isSuccessful()) { //if successful
                    DocumentSnapshot document = task.getResult(); //return document
                    if (document.exists()) {
                        String hint = (document.getString("hint")); //get string hint from database
                        String decrypt = null; //define a decrypt variable
                        try {
                            decrypt = EncryptDecrypt.decryptString(hint, EncryptDecrypt.Password); //decrypt the hint retrived from database
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace(); //catch any error and print them to stack trace
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                SignIn.this, R.style.AlertDialogTheme); //alert builder to display the retrived hint
                        builder.setTitle("Shared Secret Hint"); //set the title
                        builder.setMessage(decrypt); //set the message to the hint
                        builder.setPositiveButton("OK", //set button okay
                                new DialogInterface.OnClickListener() { //listen to clicks
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(getApplicationContext(), "Hint Closed", Toast.LENGTH_LONG).show();
                                        //On user click close hint and provide toast
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show(); //show the alert
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f); //defin size of button
                    }
                }
            }
        });
    }

    /**
     * Method for the user to sign into the application
     */
    private void signinfunction() {

        //retirve the text fields the user has entered
        final String email = mEmailEditText.getText().toString().trim();
        final String password = mPasswordEditText.getText().toString().trim();
        final String sharedSecretinput = mSharedSecretEditText.getText().toString().trim();

//
        userID = firebaseAuthenti.getCurrentUser().getUid(); //get current user ID
        final DocumentReference documentReference = fDatasebase.collection("Users").document(userID); //Check if the User ID matches one in collection
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult(); //if document exists get result
                        if (document.exists()) {
                            String sharedSecretDatabase = (document.getString("sharedSecret")); //get shared secret from document
                            String decryptsharedSecret = null; //define decrypt variable
                            try {
                                decryptsharedSecret = EncryptDecrypt.decryptString(sharedSecretDatabase, EncryptDecrypt.Password); //decrypt shared secrent
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                                e.printStackTrace(); //Catch any errors
                            }
                            if (!sharedSecretinput.equals(decryptsharedSecret)) { //perform validation and if no match give error to user
                                mSharedSecretEditText.setError("Shared secret does not match registered user");

                            } else {
                                mSharedSecretEditText.setError(null); //if no error do nothing

                                firebaseAuthenti.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //email and password verification
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


                                        if (task.isSuccessful()) { //if email and password match peform login to application
                                            Toast.makeText(SignIn.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                            Intent myintent = new Intent(SignIn.this, HomeScreen.class);
                                            startActivity(myintent); //launch the homescreen of app


                                        } else if (counter == 0) { //peform validation for number of user trys
                                            mSignInButton.setEnabled(false); //if equal to 0 diable log in button for five minutes
                                            Toast alert = Toast.makeText(SignIn.this, "Login Disabled for 5 mins", Toast.LENGTH_SHORT);
                                            alert.show();
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() //otherwise set the button to enabled and counter to 2
                                                {
                                                    mSignInButton.setEnabled(true);
                                                    counter = 2;
                                                }
                                            }, 300000);
                                        } else {
                                            Toast.makeText(SignIn.this, "Error logging in please check details", Toast.LENGTH_SHORT).show();
                                            counter--; //Give user an error message if login not successful and minus one from counter
                                        }

                                    }
                                });
                            }
                        }
                    }
                }
            }

        });


        if (sharedSecretinput.isEmpty()) { //validatation check if shared secrent input is empty
            mSharedSecretEditText.setError("Shared secret is required"); //give error

        } else {
            mSharedSecretEditText.setError(null); //do nothing
        }

        if (password.isEmpty()) { //check if password is empty
            mPasswordEditText.setError("Password is required"); //give error

        } else {
            mPasswordEditText.setError(null); //do nothing
        }

        if (email.isEmpty()) { //check if email is empty
            mEmailEditText.setError("Email is required"); //give error

        } else {
            mEmailEditText.setError(null); // do nothing
        }

    }


    /**
     * Method for user to return to initial screen
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed(); //if user presses back image take user back
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
    }

}


