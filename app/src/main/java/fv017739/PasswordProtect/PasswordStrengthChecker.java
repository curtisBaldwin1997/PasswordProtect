package fv017739.PasswordProtect;

import android.graphics.Color;

/**
 * Class to measure the strength of password
 */
public enum PasswordStrengthChecker {

    WEAK(R.string.strengthWeak, Color.RED), //Define colour of weak password
    FAIR(R.string.strengthPoor, Color.argb(255, 225, 165, 0)), //Define colour of poor password
    GOOD(R.string.strengthGood, Color.argb(255, 65, 105, 225)), //Define colour of good password
    STRONG(R.string.strengthStrong, Color.argb(255, 0, 0, 128)); //Define colour of strong password


    static int MINUMUMLENGTH = 8; //Password minimum size of 8
    static int MAXLENGTH = 40; //Password maximum size of 40
    static boolean CHARACTERS = true; //Characters set to true
    static boolean DIGITS = true; //Digits set to true
    static boolean LOWERCASE = true; //Lowercase set to true
    static boolean UPPERCASE = false; //Uppercase set to false

    int resId; //The ID
    int color; //The colour

    /**
     * Method to check the strength depending on ID and colour
     *
     * @param resId
     * @param color
     */
    PasswordStrengthChecker(int resId, int color) {
        this.resId = resId; //ID
        this.color = color; //Colour
    }

    /**
     * Get sequence of ID's
     *
     * @param ctx
     * @return
     */
    public CharSequence getText(android.content.Context ctx) {
        return ctx.getText(resId); //Return ID
    }

    /**
     * @return Colour of password
     */
    public int getColor() {
        return color; //Return the colour of ID
    }

    /**
     * @param password checking the strength of passwword
     * @return Return result
     */
    public static PasswordStrengthChecker checkStrength(String password) {
        int Score = 0; //set score to 0
        boolean Upper = false; //all variables set to false
        boolean Lower = false;
        boolean Digit = false;
        boolean Char = false;

        //For loop checking the strength of entered password
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            //Add 1 onto score if character has a letter or digit
            if (!Char && !Character.isLetterOrDigit(c)) {
                Score += 1;
                Char = true;
            } else {
                if (!Digit && Character.isDigit(c)) { //Add 1 onto score if character is digit
                    Score += 1;
                    Digit = true;
                } else {
                    if (!Upper || !Lower) {
                        if (Character.isUpperCase(c))
                            Upper = true;
                        else
                            Lower = true;
                        if (Upper && Lower)
                            Score += 1;  //Add 1 onto score if character is upper or lower case
                    }
                }
            }

        }
        //if password is greater than 8 score + 1
        if (password.length() > MINUMUMLENGTH) {
            if ((CHARACTERS && !Char)
                    || (UPPERCASE && !Upper)
                    || (LOWERCASE && !Lower)
                    || (DIGITS && !Digit)) {
                Score = 1;
            } else {
                Score = 2; //otherwise score is 2
                if (password.length() > MAXLENGTH) {  //if password greater than max length score is 3
                    Score = 3;
                }
            }
        } else {
            Score = 0; //Otherwise score is 0
        }

        switch (Score) {
            case 0:
                return WEAK; //return weak
            case 1:
                return FAIR; //return fair
            case 2:
                return GOOD; //return good
            case 3:
                return STRONG; //return strong
            default:
        }

        return STRONG; //default return strong
    }

}




