package fv017739.PasswordProtect;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import fonts.cairoButton;
import fonts.cairoEditText;
import fonts.cairoTextView;

/**
 * Class to handle a new user signing up
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    //Define Edit text values
    private cairoEditText firstName, surname, birthday, mobileNumber, emailAddress, massterPass, confirmPass, sharedSecret, confirmSharedSecret, sharedSecretHint;
    //Define text view values
    private cairoTextView requirementsTextview;
    //Define button
    private cairoButton signUpButton;

    private FirebaseAuth firebaseAuth; //Define firebase authentication
    private FirebaseFirestore fDatasebase; //Defin firebase database
    private String userID; //Define the user ID
    private static final String TAG = "SignUp"; //Defin TAG for stack trace
    ImageView _back; //Define imageview
    boolean checkFormat; //Value to check format
    private String Password = "OldwalkingBoot1234569"; //Encrypt /. decrypt password
    SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy"); //Date format validation

    /**
     * Initialise view and XML layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); //Retrive XML layout

        //initialise firebase auth and database
        firebaseAuth = FirebaseAuth.getInstance();
        fDatasebase = FirebaseFirestore.getInstance();

        //Find requirments view ID and listen to user clicks
        requirementsTextview = findViewById(R.id.id_PasswordRequirements);
        requirementsTextview.setOnClickListener(this);

        //Find edit text fields ID's
        firstName = (cairoEditText) findViewById(R.id.firstNameEntry);
        surname = (cairoEditText) findViewById(R.id.surnameEntry);
        birthday = (cairoEditText) findViewById(R.id.birthdayEntry);
        mobileNumber = (cairoEditText) findViewById(R.id.numberEntry);
        emailAddress = (cairoEditText) findViewById(R.id.emailEntry);

        //Find master pass ID and listen to text change
        massterPass = (cairoEditText) findViewById(R.id.passwordEntry);
        massterPass.addTextChangedListener(this);

        //Find edit text view ID's
        confirmPass = (cairoEditText) findViewById(R.id.passwordConfirmEntry);
        sharedSecret = (cairoEditText) findViewById(R.id.sharedSecret);
        confirmSharedSecret = (cairoEditText) findViewById(R.id.ConfirmSharedSecretEntry);
        sharedSecretHint = (cairoEditText) findViewById(R.id.SharedSecretEntryHint);

        //Find sign up button ID and listen to user clicks
        signUpButton = (cairoButton) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        //Find back image view ID and listen to clicks
        _back = findViewById(R.id.backGround);
        _back.setOnClickListener(this);


    }


    /**
     * Method to handle button clicks
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        {
            final Vibrator vibration = (Vibrator) //Call virbate function
                    SignUp.this.getSystemService(Context.VIBRATOR_SERVICE);
            if (v == signUpButton) { //if sign up button click perform sign up function
                signupfunction();

                vibration.vibrate(80); //vibrate for 80 milliseconds

            }
            if (v == _back) { //if back pressed perform back pressed function
                onBackPressed();
                vibration.vibrate(80); //vibrate for 80 milliseconds
            }
            if (v == requirementsTextview) { //if requirements pressed bring up dialog
                dialog();
                vibration.vibrate(80); //vibrate for 80 milliseconds
            }

        }
    }

    /**
     * Method to bring up a dialog on password requirments
     */
    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder( //build a dialog box
                SignUp.this, R.style.AlertDialogTheme); //for the sign up activity
        builder.setTitle("Password Requirements"); //set title
        builder.setMessage("1.Password length 8 to 40 Characters" + "\n" + //display message
                "2. Use Uppercase and Lowercase letters" + "\n" +
                "3. Use at least one Number" + "\n" +
                "4. Use at least one Special Character");
        builder.setPositiveButton("OK", //set button possitive
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(), "Requirements Closed", Toast.LENGTH_LONG).show();
                        //when pressed close dialog and inform user

                    }
                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f); //set button size
    }


    /**
     * Method to perform back process
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed(); //on user press take user back to previous page
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
    }

    /**
     * Method to sign user up and store + encrpyt information in database
     */
    private void signupfunction() {

        //Retreive all edit text values user has entered
        final String password = massterPass.getText().toString().trim();
        final String confirmPassword = confirmPass.getText().toString().trim();
        final String firstname = firstName.getText().toString().trim();
        final String surnameinput = surname.getText().toString().trim();
        final String email = emailAddress.getText().toString().trim();
        final String mobile = mobileNumber.getText().toString().trim();
        final String birthdayinput = birthday.getText().toString().trim();
        final String sharedSecretinput = sharedSecret.getText().toString().trim();
        final String confirmSharedSecretinput = confirmSharedSecret.getText().toString().trim();
        final String SharedSecretHint = sharedSecretHint.getText().toString().trim();


        if (confirmSharedSecretinput.isEmpty()) { //check if confirm shared secret is empty
            confirmSharedSecret.setError("Confirm shared secret is required"); //error

        } else {
            confirmSharedSecret.setError(null); //do nothing
        }

        if (sharedSecretinput.isEmpty()) { //check if shared secret is empty
            sharedSecret.setError("Shared secret is required"); //error

        } else {
            sharedSecret.setError(null); //do nothing
        }

        if (confirmPassword.isEmpty()) { //check if confirm password is empty
            confirmPass.setError("Confirm password is required"); // error

        } else {
            confirmPass.setError(null); //do nothing
        }

        if (password.isEmpty()) { //check if password is empty
            massterPass.setError("Password is required"); //error

        } else {
            massterPass.setError(null); //do nothing
        }

        if (email.isEmpty()) { //check if email is empty
            emailAddress.setError("Email is required"); //error

        } else {
            emailAddress.setError(null); //do nothing
        }
        if (mobile.isEmpty() || mobile.length() < 11) { //check if mobile is empty and length is 11 character
            mobileNumber.setError("A valid mobile number is required"); //error

        } else {
            mobileNumber.setError(null); //do nothing
        }

        if (birthdayinput.isEmpty()) { //check if birthday is empty
            birthday.setError("Birthday is required"); //error

        } else {
            birthday.setError(null); //do nothing

        }
        if (birthdayinput.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) { //check if birthday matches format
            checkFormat = true; //if true do nothing
        } else {
            checkFormat = false; //if fase
            Toast.makeText(SignUp.this, "Please Enter birthday in required format (DD/MM/YYYY)", Toast.LENGTH_LONG).show();
        } //show error

        if (surnameinput.isEmpty()) { //check if surname is empty
            surname.setError("Surname is required"); //error

        } else {
            surname.setError(null); //do nothing
        }

        if (firstname.isEmpty()) { //check if first name is empty
            firstName.setError("First name is required"); //error

        } else {
            firstName.setError(null); //do nothing
        }


        if (confirmPassword.equals(password)) { //check if password and confirm password match
            massterPass.setError(null); //if they do, do nothing
        } else {
            massterPass.setError("Passwords do not match"); //otherwise error
        }


        if (confirmSharedSecretinput.equals(sharedSecretinput)) { //check shared secret match
            sharedSecret.setError(null); //if they do, do nothing
        } else {
            sharedSecret.setError("Shared Secret does not match"); //if not error
        }
        if (password.length() < 8 || password.length() > 40) { //check password length between 8 - 40
            massterPass.setError("Please enter valid password between 8 and 40 characters"); //error
            return;
        }

        //authentication with email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) { //if successful

                            Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show(); //create user
                            userID = firebaseAuth.getCurrentUser().getUid(); //give user UID
                            DocumentReference documentReference = fDatasebase.collection("Users").document(userID); //store in Users collection in database
                            Map<String, Object> user = new HashMap<>();
                            try {
                                user.put("fName", EncryptDecrypt.encryptString(firstname, Password)); //encrypt firstname and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace(); //catch errors and print
                            }
                            try {
                                user.put("sName", EncryptDecrypt.encryptString(surnameinput, Password)); //encrypt surname and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace();//catch errors and print
                            }
                            try {
                                user.put("birthday", EncryptDecrypt.encryptString(birthdayinput, EncryptDecrypt.Password)); //encrypt birthday and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace();//catch errors and print
                            }
                            try {
                                user.put("mobile", EncryptDecrypt.encryptString(mobile, EncryptDecrypt.Password)); //encrypt mobile and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace();//catch errors and print
                            }
                            try {
                                user.put("email", EncryptDecrypt.encryptString(email, EncryptDecrypt.Password)); //encrypt email and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace();//catch errors and print
                            }
                            try {
                                user.put("password", EncryptDecrypt.encryptString(password, EncryptDecrypt.Password)); //encrypt password and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace();//catch errors and print
                            }
                            try {
                                user.put("sharedSecret", EncryptDecrypt.encryptString(sharedSecretinput, EncryptDecrypt.Password)); //encrypt sharedsecret and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace();//catch errors and print
                            }
                            try {
                                user.put("hint", EncryptDecrypt.encryptString(SharedSecretHint, EncryptDecrypt.Password)); //encrypt hint and store in database
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                                e.printStackTrace();//catch errors and print
                            }
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for " + userID); //if task succsful add to log user created
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "on Failure" + e.toString()); //and to log if on failure and reason
                                }
                            });

                            Intent myintent = new Intent(SignUp.this, PhoneActivity.class); //if successful take user to mobile authentication
                            myintent.putExtra("mobile", mobile); //send mobile entry to next page
                            startActivity(myintent); //start new activity

                        } else
                            Toast.makeText(SignUp.this, "error registering user", Toast.LENGTH_SHORT).show(); //if unsuccessful inform user of error

                    }
                });

    }


    /**
     * Method for password analysis
     *
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    /**
     * Method for password analysis
     *
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        update(charSequence.toString()); //update string for password analysis
    }

    /**
     * Method for password analysis
     *
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * Method to measure password strength
     *
     * @param massterPassword
     */
    private void update(String massterPassword) {
        //Find progress bar and text view ID's in XML
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView strengthView = (TextView) findViewById(R.id.strength);

        if (TextView.VISIBLE != strengthView.getVisibility()) //Return strength view
            return;

        if (massterPassword.isEmpty()) { //if password empty
            strengthView.setText(""); //set text to nothing
            progressBar.setProgress(0); //and progress bar to 0
            return;
        }
        //get strength checker, set text and colour
        PasswordStrengthChecker str = PasswordStrengthChecker.checkStrength(massterPassword);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) { //set progress bar and text
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Poor")) { //set progress bar and text
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Good")) { //set progress bar and text
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100); //set progress bar
        }
    }
}
