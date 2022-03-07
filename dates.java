import java.text.ParseException;
import java.text.SimpleDateFormat;

public class dates{

    public static void main(String[]args){
        System.out.println(isValidDate("20-01-2014"));
        System.out.println(isValidDate("11-04-2015"));
        System.out.println(isValidDate("32476347656435"));
    }

    public static boolean isValidDate(String inDate) {
        String[] dateFormats = {"dd-mm-yyyy", "0d-mm-yyyy", "d-mm-yyyy", "0d-0m-yyyy", "dd-0m-yyyy", "d-0m-yyyy",
            "d-m-yyyy", "0d-m-yyyy", "dd-m-yyyy", "dd-mm-yy", "0d-mm-yy", "d-mm-yy", "0d-0m-yy", "dd-0m-yy", "d-0m-yy", 
            "d-m-yy", "0d-m-yy", "dd-m-yy", "dd/mm/yyyy", "0d/mm/yyyy", "d/mm/yyyy", "0d/0m/yyyy", "dd/0m/yyyy", "d/0m/yyyy", 
            "d/m/yyyy", "0d/m/yyyy", "dd/m/yyyy", "dd/mm/yy", "0d/mm/yy", "d/m/yy", "0d/0m/yy", "dd/0m/yy", "d/0m/yy", "d/m/yy", "0d-m/yy", 
            "dd/m/yy", "dd mm yyyy", "0d mm yyyy ", "d mm yyyy", "0d 0m yyyy", "dd 0m yyyy", "d 0m yyyy", 
            "d m yyyy", "0d m yyyy", "dd m yyyy", "dd mm yy", "0d mm yy", "d mm yy", "0d 0m yy", "dd 0m yy", "d 0m yy", "d m yy", "0d m yy", 
            "dd m yy"};
        for(int i = 0; i < dateFormats.length; i++){
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormats[i]);
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(inDate.trim());
            } catch (ParseException pe) {
                System.out.println("err");
            }
            return true;
        }
      return true;
    }
}