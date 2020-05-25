package fv017739.PasswordProtect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to generate random passwords
 */
public class PasswordGenerator {

    private static final String Lowercase = "abcdefghijklmnopqrstuvwxyz"; //Alphabet in lowercase
    private static final String Uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //Alphabet in upercase
    private static final String Numbers = "0123456789"; //List of number
    private static final String Characters = "!@#$%&*()_+-=[]|,./?><"; //List of special character
    private boolean useLowercase; //Boolean for lowercase
    private boolean useUppercase; //Boolean for upercase
    private boolean useNumbers; //Boolean for numbers
    private boolean useCharacters; //Boolean for characters


    private PasswordGenerator() {
        throw new UnsupportedOperationException("Empty constructor is not supported."); //Throw exception if password generation is empty
    }

    /**
     * Build password generatior using strings
     * @param builder
     */
    private PasswordGenerator(PasswordGeneratorBuilder builder) {
        this.useLowercase = builder.useLowercase; //Build lowercase
        this.useUppercase = builder.useUppercase; //Build upercase
        this.useNumbers = builder.useNumbers; //Build numbers
        this.useCharacters = builder.useCharacters; //Build characters
    }

    /**
     * Class for builder password generator
     */
    public static class PasswordGeneratorBuilder {

        private boolean useLowercase; //Boolean for lowercase
        private boolean useUppercase; //Boolean for upercase
        private boolean useNumbers; //Boolean for numbers
        private boolean useCharacters; //Boolean for characters

        /**
         * Pre define use is set to fales
         */
        public PasswordGeneratorBuilder() {
            this.useLowercase = false; //Set to false initially
            this.useUppercase = false; //Set to false initially
            this.useNumbers = false; //Set to false initially
            this.useCharacters= false; //Set to false initially
        }

        /**
         *
         * @param useLower Initialy set to false for user to decide course of action
         * @return builder
         */
        public PasswordGeneratorBuilder useLowercase(boolean useLower) {
            this.useLowercase = useLower;
            return this;
        }

        /**
         *
         * @param useUppercase Initialy set to false for user to decide course of action
         *  @return builder
         */
        public PasswordGeneratorBuilder useUppercase(boolean useUppercase) {
            this.useUppercase = useUppercase;
            return this;
        }

        /**
         *
         * @param useNumbers Initialy set to false for user to decide course of action
         * @return builder

         */
        public PasswordGeneratorBuilder useNumbers(boolean useNumbers) {
            this.useNumbers = useNumbers;
            return this;
        }

        /**
         *
         * @param useCharacters Initialy set to false for user to decide course of action
         * @return builder

         */
        public PasswordGeneratorBuilder useCharacters(boolean useCharacters) {
            this.useCharacters = useCharacters;
            return this;
        }

        /**
         *
         * @return new password generator
         */
        public PasswordGenerator build() {

            return new PasswordGenerator(this); //Return the new password
        }
    }

    /**
     * Method to generate new password dependent on defined properties
     *
     * @param length the length of the password.
     * @return a password that uses the categories defined
     */
    public String generate(int length) {

        if (length <= 0) {
            return "";      //If length is less than or equal to 0 return validation
        }


        StringBuilder password = new StringBuilder(length); //Length of password variable
        Random random = new Random(System.nanoTime()); //Random variable

      //Which arguments to use
        List<String> charCategories = new ArrayList<>(4);
        if (useLowercase) {
            charCategories.add(Lowercase);
        }
        if (useUppercase) {
            charCategories.add(Uppercase);
        }
        if (useNumbers) {
            charCategories.add(Numbers);
        }
        if (useCharacters) {
            charCategories.add(Characters);
        }

        //Create new password based on the argument above
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }
}

