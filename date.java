import java.text.ParseException;
import java.text.SimpleDateFormat;

public class date {
    public static void main(String[] args) {

        System.out.println(isValidDate("20-01-2014"));
        System.out.println(isValidDate("11-04-2015 22:01:33:023"));

        System.out.println(isValidDate("32476347656435"));
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}