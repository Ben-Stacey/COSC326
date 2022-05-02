import java.util.ArrayList;
import java.util.Scanner;

public class date {
    static String day = "";
    static String month = "";
    static char separator = 'a';
    static String year = "";
    static int sepErr = 0;
    static int counter = 0;
    static int index = 0;
    static int check = 0;
    static boolean yearLength = false;
    static boolean leapY = false;
    static boolean valid = true;
    static boolean dayErr = false;
    static boolean dateCheck = false;
    static boolean monthErr = false;
    static boolean yearRange = true;
    static boolean sep = false;
    static ArrayList<String> dates = new ArrayList<String>();
    static String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    /** Main method, this runs the program */
    public static void main(String[]args) {
        input();
    }

    /** Imput method takes the stdIn date */
    public static void input(){
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println("Enter a date:");
        try{
            while(scan.hasNextLine()){
                String date = scan.nextLine();
                dates.add(date);
                counter++;
            }
        }catch(NumberFormatException e){
            System.out.println("INVALID but unknown");
            reset();
            input();
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("INVALID but unknown");
            reset();
            input();
        }
        start();
    }

    /** starts the program */
    public static void start(){
        reset();
        for(int i = 0; i < counter; i++){
            reset();
            checkSeperator(dates.get(i));
        }
    }

    /** resets the variables for each time */
    public static void reset(){
        sepErr = 0;
        index = 0;
        check = 0;
        yearLength = false;
        valid = true;
        dayErr = false;
        dateCheck = false;
        monthErr = false;
        yearRange = true;
        leapY = false;
        sep = false;
    }

    /** checks and stores the seperator of the date */
    public static void checkSeperator(String date){
        for(int i = 0; i < date.length(); i++){
            if(date.charAt(i) == '-'){
                separator = '-';
            }
            if(date.charAt(i) == '/'){
                separator = '/';
            }
            if(date.charAt(i) == ' '){
                separator = ' ';
            }
        }

        try{
            if(date.charAt(date.length() - 1) == '-' || date.charAt(date.length() - 1) == '/' || date.charAt(date.length() - 1) == ' '){
                sep = true;
            }
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("INVALID but unknown");
        }

        for(int i = 0; i < date.length(); i++){
            char c = date.charAt(i);
            if(c == separator){
                index++;
                if(index == 1 && date.charAt(i) != separator){
                    sepErr++;
                }
            }
        }
        stringSplit(date);
    }

    /** splits the dates into the the day, month, year sections */
    public static void stringSplit(String date){
        for(int i = 0; i < date.length(); i++){
            int start;

            if(date.charAt(i) == separator){
                start = i;
                day = date.substring(0, date.indexOf(separator));
                month = date.substring(date.indexOf(separator), date.indexOf(separator, start));
                year = date.substring(date.indexOf(separator, start) + 1, date.length());
            }
        }

        if(Integer.parseInt(day) == 0 && Integer.parseInt(day) < 31){
            dayErr = true;
        }

        for(int x = 0; x < date.length(); x++){
            if(date.charAt(x) == separator){
                check++;
            }
        }

        month = month.substring(1,month.length());

        if(year.length() == 2){
            plusYear();
        }

        if(day.length() == 1){
            plusDay();
        }

        dayCheck();
        negCheck();
        checkYearLength();
        monthValid();
        checkRange();

        int dayx = Integer.parseInt(day);
        if(dayx == 29 && month.equals("FEB")){
            if(!leapYearCheck()){
                leapY = true;
            }
        }
        validation();
    }

    /** checks for negatives in the string*/
    public static void negCheck(){
        if(day.charAt(0) == '-'){
            dayErr = true;
        }else if(year.charAt(0) == '-'){
            yearLength = false;
        }else if(month.charAt(0) == '-'){
            monthErr = true;
        }
    }

    /** adds 20 to the start of the date if length is 2 */
    public static void plusYear(){
        if(Integer.parseInt(year) <= 50){
            year = "20" + year;
        }else if(Integer.parseInt(year) > 50){
            year = "19" + year;
        }
    }

    /**Adds the 0 to the start of a day if it is in format d */
    public static void plusDay(){
        day = "0" + day;
    }

    /** checks the year length is 2 or 4 */
    public static void checkYearLength(){
        if(year.length() != 2 || year.length() != 4){
            yearLength = true;
        }
    }

    /** checks day constrainst */
    public static void dayCheck(){
        if(day.length() > 2){
            dayErr = true;
        }

        if(!(Integer.parseInt(day) < 31 && Integer.parseInt(day) > 0)){
            dayErr = true;
        }
    }

    /**checks the year is within the correct range */
    public static void checkRange(){
        if(Integer.parseInt(year) > 3000 || Integer.parseInt(year) < 1753) yearRange = false;
    }

    /** checks if the month is a number */
    public static boolean isNumeric(String month) {
        if (month == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(month);
        } catch (NumberFormatException e) {
            return false;
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    public static void monthValid(){
        if(monthCheck(month)){
            monthErr = false;
        }else if(!monthCheck(month)){
            monthErr = true;
        }
    }

    /** checks if the month is the correct string */
    public static Boolean monthCheck(String month){
        for(int i = 0; i < monthList.length; i++){
            String low = monthList[i].toLowerCase();
            String high = monthList[i].toUpperCase();
            if(high.equals(month) || monthList[i].equals(month) || low.equals(month)){
                month = month.toUpperCase();
                return true;
            }else if(isNumeric(month)){
                monthConvert();
                return true;
            }
        }
        return false;
    }

    /** converts numbers to the word */
    public static void monthConvert(){
        try{
            int numMonth = Integer.parseInt(month);

            if(numMonth == 0){
                monthErr = true;
            }

            if(!(numMonth <= 12 && numMonth >= 1)){
                monthErr = true;
            }

            if(month.charAt(0) == '0'){
                month = month.substring(1);
            }
    
            for(int x = 0; x < monthList.length; x++){
                if(numMonth - 1 == x){
                    month = monthList[x];
                }
            }

            month = month.toUpperCase(); 

        }catch(NumberFormatException e){
            System.out.println("INVALID but unknown");
        }               
    }

    /** check if the year is a leap year */
    public static boolean leapYearCheck(){
        if(Integer.parseInt(year) % 100 == 0){
            if(Integer.parseInt(year) % 400 == 0) return true;
        }else{
            if(Integer.parseInt(year) % 4 == 0) return true;
        }
        return false;
    }
    
    /** does the validation and outputs the errors or if it passes */
    public static void validation(){
        if(dayErr == true){
            System.out.println(day + separator + month + separator + year + " - INVALID: Day is wrong.");
            valid = false;
        }
        if(!yearLength){
            System.out.println(day + separator + month + separator + year + " - INVALID: Year is wrong.");
            valid = false; 
        }
        if(leapY){
            System.out.println(day + separator + month + separator + year + " - INVALID: Year is wrong.");
            valid = false; 
        }
        if(sepErr == 1 || sep == true){
            System.out.println(day + separator + month + separator + year + " - INVALID: Seperators are wrong.");
            valid = false;
        }
        if(check > 2){
            System.out.println(day + separator + month + separator + year + " - INVALID: More than 2 seperators.");
            valid = false;
        }
        if(monthErr == true){
            System.out.println(day + separator + month + separator + year + " - INVALID: Month is wrong.");
            valid = false;
        }
        if(yearRange == false){
            System.out.println(day + separator + month + separator + year + " - INVALID: Year is out of range.");
            valid = false;
        }
        if(valid){
            //if valid
            System.out.println("Valid date: " + day + " " + month + " " + year);
        }
        System.out.println("");
    }
}