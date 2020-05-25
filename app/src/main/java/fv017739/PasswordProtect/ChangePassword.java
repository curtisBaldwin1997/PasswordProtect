package fv017739.PasswordProtect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import fonts.cairoButton;
import fonts.cairoEditText;

/**
 * Class to change users password or if they have forgotten their password
 */
public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    cairoEditText EmailEditText; //Define edit text to link to edit text in XML
    cairoButton SendEmail; //Define button to link to button in XML
    ImageView _back; //Define back button to link to button in XML
    FirebaseAuth firebaseAuth; //Call firebase authentication to authenticate user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password); //Set layout file change password

        EmailEditText = findViewById(R.id.EmailEditText1); //Find the edit text view in XML
        SendEmail = findViewById(R.id.SendButton); //Find the button view in XML
        _back = findViewById(R.id.id_back_ImageView); // Find the image view in XML
        firebaseAuth = FirebaseAuth.getInstance(); //Get authetication instance
        _back.setOnClickListener(this); //Let the image view listen to click

        SendEmail.setOnClickListener(new View.OnClickListener() { //On click listener for email button
            @Override
            public void onClick(View view) {
                firebaseAuth.sendPasswordResetEmail(EmailEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() { //Use firbase send password reset functionality
                    @Override
                    //Retrieve the user email from edit text field and set oncomplete listener
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) { //If sending the email is successful
                            Toast.makeText(getApplicationContext(), "Password change sent to your email", Toast.LENGTH_LONG).show(); //inform user that a change of password has been set the users email
                            clearText(); //Call emthod clear text to remove users email after button press
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show(); //If unsuccessful display error message
                        }
                    }
                });
            }
        });
    }

    /**
     * Method to clear text from edit text field
     */
    public void clearText() {
        EmailEditText.getText().clear(); //Get the contents of editText and clear it
    }

    /**
     * Method for the user to return to previous page
     */
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out); //On image view press return to previous page
    }

    /**
     * Method to handle on click of image view
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        final Vibrator vibration = (Vibrator) //Make the image view vibrate on touch
                ChangePassword.this.getSystemService(Context.VIBRATOR_SERVICE); //Call vibrate functionality
        if
        (view == _back) { //If view equals the back ID carry out the following
            onBackPressed(); //perform back pressed method
            vibration.vibrate(80); //vibrate for 80milliseconds
        }
    }
}
