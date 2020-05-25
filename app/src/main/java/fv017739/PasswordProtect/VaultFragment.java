package fv017739.PasswordProtect;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import fonts.cairoButton;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Class to handle stored passwords
 */
public class VaultFragment extends Fragment {


    RecyclerViewFastScroller fastScroller; //define fastscroller
    static FirebaseFirestore fDatasebase; //define firebase database
    static FirebaseAuth firebaseAuthenti; //define firebase authentication
    private String userID; //define user ID
    private PasswordUpdateAdapter adapter = null; //Define password update adapter


    ArrayList<UpdatePassword> recyclerList = new ArrayList<>(); //define arraylist of passwords
    RecyclerView recyclerView; //define recycler view


    public VaultFragment() {

    }

    /**
     * Inflate layout with vault XML and initiate view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_update, container, false);

        //find item ID's from XML
        recyclerView = view.findViewById(R.id.recyclerPasswordUpdate);
        fastScroller = view.findViewById(R.id.fast_scroller);

        //Initialise firebase database and firebase authentication
        fDatasebase = FirebaseFirestore.getInstance();
        firebaseAuthenti = FirebaseAuth.getInstance();
        loadPasswords();//launch load passwords function

        return view; //launch view


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //on create view


    }

    @Override
    public void onStart() {
        super.onStart(); //on start method

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    /**
     * Method to load password from database
     */
    public void loadPasswords() {
        userID = firebaseAuthenti.getCurrentUser().getUid(); //get match UID in dtabase
        fDatasebase.collection("Users") //look in collection users
                .whereEqualTo("uid", userID) //where the field uid equals the current user ID
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) { //if successful
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData()); //LOG tag in stack trace

                        String title = (document.getString("title")); //get title from database
                        String user = (document.getString("username")); //get username from database
                        String pass = (document.getString("password")); //get password from database
                        String web = (document.getString("webAddress")); //get webaddress from database
                        String note = (document.getString("note")); //get note from database
                        String decrypt11 = null; //define a decrypt variable
                        String decrypt12 = null; //define a decrypt variable
                        String decrypt13 = null; //define a decrypt variable
                        String decrypt14 = null; //define a decrypt variable
                        String decrypt15 = null; //define a decrypt variable

                        try {
                            decrypt13 = EncryptDecrypt.decryptString(title, EncryptDecrypt.Password); //decypt title
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace(); //catch any errors
                        }
                        try {
                            decrypt14 = EncryptDecrypt.decryptString(user, EncryptDecrypt.Password); //decrpyt username
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException e) {
                            e.printStackTrace(); //catch any errors
                        }
                        try {
                            decrypt15 = EncryptDecrypt.decryptString(pass, EncryptDecrypt.Password); //decrpyt password
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace(); //catch any errors
                        }
                        try {
                            decrypt12 = EncryptDecrypt.decryptString(web, EncryptDecrypt.Password); //decrpyt webaddress
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace(); //catch any errors
                        }
                        try {
                            decrypt11 = EncryptDecrypt.decryptString(note, EncryptDecrypt.Password); //decrpyt note
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                            e.printStackTrace(); //catch any errors
                        }


                        UpdatePassword updatePassword = new UpdatePassword(decrypt13, decrypt14, decrypt15, decrypt12, decrypt11); //stpre decrypted values in updatepassword


                        recyclerList.add(updatePassword); //add update password to recycler list
                        Log.d(TAG, "onComplete2: " + updatePassword); //and log TAG to stack trace


                    }


                    Log.d(TAG, "onComplete: " + recyclerList); // add on complete to stack trace


                    // initiate recycler view by calling layout manager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    // show data in the recycler list
                    adapter = new PasswordUpdateAdapter(recyclerList, getContext());
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    Log.d(TAG, "No such document"); //throw error that no document exist
                }
            }

        });


    }


}



