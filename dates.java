import java.text.ParseException;
import java.text.SimpleDateFormat;

public class dates{

    public static void main(String[]args){
        System.out.println(isValidDate("20-01-2014"));
        System.out.println(isValidDate("11-04-2015"));
        System.out.println(isValidDate("32476347656435"));
    }

    public static boolean isValidDate(String inDate) {
        String[] dateFormats = {"dd-mm-yyyy", "0d-0m-yyyy", "dd-mm-yyyy", "d-m-yyyy", "dd-mm-yyyy", "d-m-yyyy",};
        for(int i = 0; i < dateFormats.length; i++){
            SimpleDateFormat dateFormatOne = new SimpleDateFormat(dateFormats[i]);
            dateFormatOne.setLenient(false);
            try {
                dateFormatOne.parse(inDate.trim());
            } catch (ParseException pe) {
                errorCheck(error);
            }
            return true;
        }
    }

    public errorCheck(error){
        if(error == notLeap){

        }else if(error == outOfRange){

        }else if(error == invalidDay){

        }else if(error == invalidYear){

        }else if(error == invalidSeparator){

        }else if(error == unknown){
            System.out.println("Invalid, error unknown");
        }
    }
}