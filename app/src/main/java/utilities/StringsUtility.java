package utilities;

public class StringsUtility {

    public final static int NUMBER_ASCII_OFFSET = 48;
    public final static int A_MIN_ASCII = 97;
    public final static int Z_MIN_ASCII = 122;
    public final static int A_MAX_ASCII = 65;
    public final static int Z_MAX_ASCII = 90;
    public final static String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static String flush(String string) {
        if (string != null) {
            if (string.length() > 0) {
                while (string.contains("  ")) {
                    string = string.replace("  ", " ");
                }
                int i = 0;
                try {
                    while (string.charAt(i) == ' ') {
                        i++;
                    }
                    int j = string.length() - 1;
                    while (string.charAt(j) == ' ') {
                        j--;
                    }
                    string = string.substring(i, j + 1);
                }
                catch (StringIndexOutOfBoundsException exception) {
                    return "";
                }
            }
            return string;
        }
        return "";
    }

    public static boolean isEmailAddress(String estimatedMail) {
        if (estimatedMail != null) {
            return estimatedMail.matches(StringsUtility.EMAIL_REGEX);
        }
        return false;
    }

}
