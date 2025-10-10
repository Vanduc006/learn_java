package course.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("w3school",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("Visit web w3school");
        boolean found = matcher.find();
        if(found) {
            System.out.println("Found");
        }
        else {
            System.out.println("Not found");
        }
    }
}
