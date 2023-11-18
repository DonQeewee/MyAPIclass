package Validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PwordValidator {

    public static boolean isPwordValid(String pw){


        if (!regEx(pw)){
            return false;
        }else{
            return true;
        }
    }

    public static boolean regEx(String txt){
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,15}$");
        Matcher m = p.matcher(txt);
        return m.matches();
    }
}
