package com.example.filmapp.Entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean mailValidation(String mail)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    public static boolean passwordValidation(String pass)
    {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})").matcher(pass);
        return matcher.matches();
    }

    public static boolean entryNullException(String pass)
    {
        if (pass != null && pass != "")
            return true;
        else
            return false;
    }

    public static boolean passwordConfirmation(String pass, String passValue)
    {
        if (passValue.equals(pass)  && passValue != null)
            return true;
        else
            return false;
    }

}
