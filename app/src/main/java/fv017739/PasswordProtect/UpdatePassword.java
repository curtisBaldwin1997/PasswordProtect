package fv017739.PasswordProtect;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.PropertyName;

import fonts.cairoButton;

/**
 * Class for updating password into recycle view
 */
public class UpdatePassword {

    private String title; //define string title
    private String username; //define username
    private String password; //define password
    private String webAddress; //define webaddress
    private String note; // define note


    public UpdatePassword() {


    }

    /**
     * Method to set values of password
     *
     * @param title
     * @param username
     * @param password
     * @param webAddress
     * @param note
     */
    public UpdatePassword(String title, String username, String password, String webAddress, String note) {
        this.title = title; //set title
        this.username = username; //set username
        this.password = password; //set password
        this.webAddress = webAddress; //set webaddress
        this.note = note; //set note
    }


    public String getTitle() {
        return title;
    } //get title

    public String getUsername() {
        return username;
    } //get username

    public String getPassword() {
        return password;
    } //get password

    public String getWebAddress() {
        return webAddress;
    } //get webaddress

    public String getNote() {
        return note;
    } //get note


    public void setTitle(String title) {
        this.title = title;
    } //set title

    public void setUsername(String username) {
        this.username = username;
    } //set username

    public void setPassword(String password) {
        this.password = password;
    } //set password

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    } //set webaddress

    public void setNote(String note) {
        this.note = note;
    } //set note


}
