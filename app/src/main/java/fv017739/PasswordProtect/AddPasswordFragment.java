package fv017739.PasswordProtect;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import fonts.cairoButton;
import fonts.cairoEditText;

/**
 * Class to handle functionality of adding a password
 */
public class AddPasswordFragment extends Fragment implements View.OnClickListener {

    private cairoEditText title, username, passwordE, webaddress, note; //Private variable for Edit text fields
    private cairoButton save, cancle, generate; //Private variables for Buttons
    private FirebaseAuth firebaseAuth; //Firebase authentication
    private FirebaseFirestore fDatasebase; //Link to the firebase database
    private static final String TAG = "NewPasswword"; //TAG defined to provide Stack trace
    private String password = "OldwalkingBoot1234569"; //Declare password for encryption/decryption

    /**
     * On Create method used to handle initialising variables and ensuring buttons can register user clicks
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_password, container, false); //Declare the layout linked to the fragment
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance(); //Get current instance of firebase
        fDatasebase = FirebaseFirestore.getInstance(); //Get current instance of firbase database
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //Adjust keyboard to go bellow edit text and not cover any
        title = view.findViewById(R.id.title_entry); //Find the linked ID for title entry in relivent XML layout file
        username = view.findViewById(R.id.username_entry); //Find the linked ID for username entry in relivent XML layout file
        passwordE = view.findViewById(R.id.password_entry); //Find the linked ID for password entry in relivent XML layout file
        webaddress = view.findViewById(R.id.address_entry); //Find the linked ID for address entry in relivent XML layout file
        note = view.findViewById(R.id.note_entry); //Find the linked ID for note entry in relivent XML layout file
        save = view.findViewById(R.id.save); //Find the linked ID for save button in relivent XML layout file
        save.setOnClickListener(this); // Make the save button start listening to clicks
        cancle = view.findViewById(R.id.cancel); ////Find the linked ID for cancel button in relivent XML layout file
        cancle.setOnClickListener(this); // make the cancle button start listening to clicks
        generate = view.findViewById(R.id.generate); //Find the linked ID for generate new password button in relivent XML layout file
        generate.setOnClickListener(this); // Make the generate button start listening to clicks

        return view; // return the view
    }

    /**
     * On click method for all buttons to work in layout
     *
     * @param v
     */
    public void onClick(View v) {

        {
            if (v == save) { //If user clicks save perform below

                storePassword(); //Store password function
                clearText(); //Clear text function

            }
            if (v == cancle) { //If user click cancle take user back to home fragment
                HomeFragment nextFrag = new HomeFragment();  //Declare this as requested fragment
                getActivity().getSupportFragmentManager().beginTransaction()    //begin taking user to next fragment
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment") //Replace current fragement containter with new one
                        .addToBackStack(null) //Add to back stack
                        .commit(); //commit fragment change
            }
            if (v == generate) {
                ToolsFragment nextFrag = new ToolsFragment();  //Declare this as requested fragment
                getActivity().getSupportFragmentManager().beginTransaction() //begin taking user to next fragment
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment") //Replace current fragement containter with new one
                        .addToBackStack(null) //Add to back stack
                        .commit(); //commit fragment change

            }
        }
    }

    /**
     * Function to store the password in firebase database
     */
    public void storePassword() {
        final String title_entry = title.getText().toString().trim(); //Get the user input from EditText box
        final String username_entry = username.getText().toString().trim(); //Get the user input from EditText box
        final String password_entry = passwordE.getText().toString().trim(); //Get the user input from EditText box
        final String webaddress_entry = webaddress.getText().toString().trim(); //Get the user input from EditText box
        final String note_entry = note.getText().toString().trim(); //Get the user input from EditText box
        String uid = firebaseAuth.getCurrentUser().getUid(); //Get the current UserID

        if (title_entry.isEmpty()) {
            title.setError("Title is required");   //If Title is empty report error

        } else {
            title.setError(null); //set no error
        }
        if (username_entry.isEmpty()) {
            username.setError("Username is required"); // If Username is empty report error

        } else {
            title.setError(null); //set no error
        }
        if (password_entry.isEmpty()) {
            passwordE.setError("Password is required"); //If password is empty report error

        } else {
            title.setError(null); //set no error
        }
        if (webaddress_entry.isEmpty()) {
            webaddress.setError("Webaddress is required"); //If address is empty report error

        } else {
            webaddress.setError(null); //set no error
        }
        if (note_entry.isEmpty()) {
            note.setError("Note is required"); //If note is empty report error

        } else {
            note.setError(null); //set no error
        }

        Map<String, Object> user = new HashMap<>(); //create a map with an array of strings and objects

        //Encrypt entry with password perform try, catch incase any errors
        try {
            user.put("title", EncryptDecrypt.encryptString(title_entry, password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        //Encrypt entry with password perform try, catch incase any errors
        try {
            user.put("username", EncryptDecrypt.encryptString(username_entry, password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        //Encrypt entry with password perform try, catch incase any errors
        try {
            user.put("password", EncryptDecrypt.encryptString(password_entry, password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        //Encrypt entry with password perform try, catch incase any errors
        try {
            user.put("webAddress", EncryptDecrypt.encryptString(webaddress_entry, password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        //Encrypt entry with password perform try, catch incase any errors
        try {
            user.put("note", EncryptDecrypt.encryptString(note_entry, password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        user.put("uid", uid); //put the current userID into the uid column in firebase database

        fDatasebase.collection("Users") //find collection users in firebase
                .add(user)  //add current users new password
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {  //success listener providing feedback on successful or not
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: Password added");
                        Toast.makeText(getContext(), "Password Added to vault", Toast.LENGTH_SHORT).show(); //If successful add password to vault and inform user
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "on Failure" + e.toString());
                Toast.makeText(getContext(), "Error Adding Password", Toast.LENGTH_SHORT).show(); //if unsuccessful inform user
            }
        });
    }

    /**
     * Function to clear user entrys after being sent to the vault or cancled
     */
    public void clearText() {
        title.getText().clear(); //clear title entry
        username.getText().clear(); //clear username entry
        passwordE.getText().clear(); //clear password entry
        webaddress.getText().clear(); //clear webadress entry
        note.getText().clear(); //clear note entry
    }

}




