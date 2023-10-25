package Util;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Constant {
	static public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static public String DBUserName = "root";
    static public String DBPassword = "root";
    public static String Url = "jdbc:mysql://localhost:3306/ratemyex";

    static public String namePattern = "^[ A-Za-z]+$";
    static public String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";

}
