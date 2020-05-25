package fv017739.PasswordProtect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import fonts.cairoButton;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class to handle optional setting of the app
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    private SwitchCompat fingerAuthSwitch; //define switch
    private SharedPreferences sharedPreferences; //define shared preferences
    public static final String OnOff = "Switch"; //on Off string
    private cairoButton logoutUser; //Button for user to log out
    private cairoButton ChangeMasterPass; //Button to change master password

    /**
     * Method to inflate relative XML file and handle fragment view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false); //Inflate and find settings XML

        //Find logout button ID in XML and handle user clicks
        logoutUser = view.findViewById(R.id.Logout);
        logoutUser.setOnClickListener(this);

        //Find change password button ID in XML and handle user clicks
        ChangeMasterPass = view.findViewById(R.id.ChangePassword);
        ChangeMasterPass.setOnClickListener(this);

        fingerAuthSwitch = (SwitchCompat) view.findViewById(R.id.switchFingerPrint); //Find switch ID in XML
        sharedPreferences = this.getActivity().getSharedPreferences("checkBox", MODE_PRIVATE); //Store user check in shared preferences
        final SharedPreferences.Editor editor = sharedPreferences.edit(); //Store in shared preferences
        fingerAuthSwitch.setChecked(sharedPreferences.getBoolean(OnOff, false)); //Set switch to be off on deafult
        fingerAuthSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //Check to see if switch on or ogg
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean(OnOff, true); //If on change OnOff to true
                } else {
                    editor.putBoolean(OnOff, false); //If off chanfe OnOff to false
                }
                editor.commit(); //Commit to shared preferences
            }
        });


        return view; //Return the view
    }

    /**
     * Method to set the title
     */
    public void onResume() {
        super.onResume();
        getActivity().setTitle("SETTINGS"); //Set title to settings
    }

    /**
     * Method to handle user clicks on buttons
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == logoutUser) {
            Intent myintent = new Intent(getActivity(), MainSignActivity.class);
            startActivity(myintent); //if user clicks logout take user out of app and to mainSignactivity
        }
        if (v == ChangeMasterPass) {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent); //If user wishes to change password take user to relevant activity
        }
    }
}
