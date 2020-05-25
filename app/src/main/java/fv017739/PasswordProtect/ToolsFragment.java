package fv017739.PasswordProtect;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.SwitchCompat;
//import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import fonts.cairoButton;


/**
 *
 */
public class ToolsFragment extends Fragment implements View.OnClickListener {

    private SeekBar seekBar; //Define seekbar
    private TextView passwordlength; //Define textview
    private SwitchCompat lowercase; //Define switch sets for password creation
    private SwitchCompat uppercase;
    private SwitchCompat numbers;
    private SwitchCompat characters;
    private cairoButton newPass, copyPassword; //define buttons

    TextView result; //Define result of password creation


    /**
     * Inflate fragmenta and define layout to display
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        //Find views in XML files
        seekBar = view.findViewById(R.id.seekBar);
        passwordlength = view.findViewById(R.id.passwordLength);
        (lowercase = view.findViewById(R.id.switchLowercase)).setOnCheckedChangeListener(multiListener); //set on clicklistener for each switch to listen to user choice
        (uppercase = view.findViewById(R.id.switchUppercase)).setOnCheckedChangeListener(multiListener);
        ;
        (numbers = view.findViewById(R.id.switchNumbers)).setOnCheckedChangeListener(multiListener);
        ;
        (characters = view.findViewById(R.id.switchCharacters)).setOnCheckedChangeListener(multiListener);
        ;
        newPass = view.findViewById(R.id.createPass);
        result = view.findViewById(R.id.generatedPassword);
        copyPassword = view.findViewById(R.id.copyPass);
        //set buttons to listen to click
        copyPassword.setOnClickListener(this);
        newPass.setOnClickListener(this);

        //find legth of password and define from progress bar value
        passwordlength.setText(String.format(getResources().getString(R.string.length), String.valueOf(seekBar.getProgress())));

        //set seekbar to listen to change
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Method to handle seek bar length
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                passwordlength.setText(String.format(getResources().getString(R.string.length), String.valueOf(progress))); //set text size dependent on value of seekbar length

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view; //return the view

    }


    CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() { //multi listener for all switches

        public void onCheckedChanged(CompoundButton v, boolean isChecked) { //see if switch is checked on or off
            switch (v.getId()) {
                case R.id.lowercaseContainer: //set switch to not checked
                    lowercase.setChecked(!lowercase.isChecked());
                    break;

                case R.id.uppercaseContainer: //set switch to not checked
                    uppercase.setChecked(!uppercase.isChecked());
                    break;

                case R.id.numberContainer: //set switch to not checked
                    numbers.setChecked(!numbers.isChecked());
                    break;

                case R.id.charactersContainer: //set switch to not checked
                    characters.setChecked(!characters.isChecked());
                    break;
            }
        }
    };


    /**
     * Method to handle on click of buttons
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createPass) { //create password generate new password dependent on user defined choice
            result.setText(getPasswordGenerator().generate(seekBar.getProgress()));
        }
        if (v.getId() == R.id.copyPass) { //copy password to clip board
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE); //get clipboard service
            ClipData clip = ClipData.newPlainText("label", "" + result.getText().toString()); // get text and copy to clip boarrd
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Password copied to clip board", Toast.LENGTH_SHORT).show(); //user notification
        }
    }

    /**
     * @return generated password
     */
    private PasswordGenerator getPasswordGenerator() {
        return new PasswordGenerator.PasswordGeneratorBuilder()
                .useLowercase(lowercase.isChecked()) //set switch on
                .useUppercase(uppercase.isChecked()) //set switch on
                .useCharacters(numbers.isChecked()) //set switch on
                .useNumbers(characters.isChecked()) // set switch on
                .build(); //build new password
    }



}
