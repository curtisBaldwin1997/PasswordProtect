package fv017739.PasswordProtect;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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

/**
 * Class used to retrive a users profile from firebase
 */
public class ProfileFragment extends Fragment {
    private TextView userName; //Text view variable
    private TextView surname; //Text view variable
    private TextView mobileNumber; //Text view variable
    private TextView emailAdd; //Text view variable
    private TextView Birthday; //Text view variable
    private FirebaseFirestore fDatasebase; //initiate firebase databse
    private FirebaseAuth firebaseAuthenti; //Initiae firebase authentication
    private String userID; //Get userID

    /**
     * @param inflater XML layout file
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false); //Inflate the profile fragment container

        userName = view.findViewById(R.id.userName_Database); //Find username ID in XML
        surname = view.findViewById(R.id.surName_Database); //Find surname ID in XML
        mobileNumber = view.findViewById(R.id.mobile_Database); //Find mobile number ID in XML
        emailAdd = view.findViewById(R.id.email_Database); //Find email ID in XML
        Birthday = view.findViewById(R.id.birthday_Database); //Find birthday ID in XML
        fDatasebase = FirebaseFirestore.getInstance(); //Get firestore instance
        firebaseAuthenti = FirebaseAuth.getInstance(); //Get firebase authentication instance
        profilInfo(); //Load profile method

        return view; //Return the view
    }

    /**
     *Method to retrieve profile infomation of a user from database
     */
    private void profilInfo() {
        userID = firebaseAuthenti.getCurrentUser().getUid(); //Get current user ID
        final DocumentReference documentReference = fDatasebase.collection("Users").document(userID); //Find document reference in firebase
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { //On complete method for validation
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult(); //Retrive document if there is one
                    if (document.exists()) {
                        String firstName = (document.getString("fName")); //get fields from document
                        String surName = (document.getString("sName")); //get fields from document
                        String DOB = (document.getString("birthday")); //get fields from document
                        String mobileNum = (document.getString("mobile")); //get fields from document
                        String emailAddress = (document.getString("email")); //get fields from document
                        String decrypt = null; //Create decrypt variable
                        String decrypt2 = null; //Create decrypt variable
                        String decrypt3 = null; //Create decrypt variable
                        String decrypt4 = null; //Create decrypt variable
                        String decrypt5 = null; //Create decrypt variable
                        try {
                            decrypt3 = EncryptDecrypt.decryptString(DOB, EncryptDecrypt.Password); //Decrpyt brithday, catch any errors
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace();
                        }
                        try {
                            decrypt4 = EncryptDecrypt.decryptString(mobileNum, EncryptDecrypt.Password); //Decrypt mobile, catch any errors
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException e) {
                            e.printStackTrace();
                        }
                        try {
                            decrypt5 = EncryptDecrypt.decryptString(emailAddress, EncryptDecrypt.Password); //Decrypt email, catch any errors
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace();
                        }
                        try {
                            decrypt2 = EncryptDecrypt.decryptString(surName, EncryptDecrypt.Password); //Decrypt surname, catch any errors
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace();
                        }
                        try {
                            decrypt = EncryptDecrypt.decryptString(firstName, EncryptDecrypt.Password); //Decrypt firstname, catch any errors
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace();
                        }
                        userName.setText(decrypt); //assign decrypted value to text view
                        surname.setText(decrypt2); //assign decrypted value to text view
                        Birthday.setText(decrypt3); //assign decrypted value to text view
                        mobileNumber.setText(decrypt4); //assign decrypted value to text view
                        emailAdd.setText(decrypt5); //assign decrypted value to text view

                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() { //add failure listener
                    @Override
                    public void onFailure(@NonNull Exception e) { //if failure catch exception
                    }
                });
    }
}

