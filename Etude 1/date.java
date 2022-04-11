import java.io.*;
import java.util.Scanner;

public class date {
    static String day = "";
    static String month = "";
    static char separator = 'a';
    static String year = "";
    static int leap = 0;
    static int counter = 0;
    static int sepErr = 0;
    static int index = 0;
    static int check = 0;
    static boolean yearLength = false;
    static boolean valid = true;
    static boolean dayErr = false;
    static boolean dateCheck = false;
    static boolean monthErr = false;
    static boolean yearRange = true;

    /** Main method, this runs the program */
    public static void main(String[]args) {
        input();
    }

    /** Imput method takes the stdIn date */
    public static void input(){
        try{
           // File file = new File("input.txt");
            Scanner scan = new Scanner(System.in);
            //while(scan.hasNextLine()){
                System.out.println("");
                System.out.println("Enter a date:");
                String date = scan.nextLine();
                checkSeperator(date);
            //}
            /** 
            scan.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            **/
        }catch(NumberFormatException e){
            System.out.println("Number format exception");
            input();
        }
        
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

        negCheck();
        checkYearLength();
        monthConvert();
        leapYearCheck();
        checkRange();
        validation();
    }

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

    /**checks the year is within the correct range */
    public static void checkRange(){
        if(Integer.parseInt(year) > 3000 || Integer.parseInt(year) < 1753) yearRange = false;
    }

    /** converts the month into the required word */
    public static void monthConvert(){
        String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
        if(month.length() >= 3 && month.charAt(0) == '0'){
            month = month.substring(1,month.length());
        }else if(month.length() == 3){
            return; 
        }

        int numMonth = Integer.parseInt(month);

        if(numMonth == 0){
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
    }

    /** check if the year is a leap year */
    public static void leapYearCheck(){
        if ((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100!= 0) || (Integer.parseInt(year) % 400 == 0) && month == "Feb" || Integer.parseInt(day) == 28){
            day = "29";
        }
    }
    
    /** does the validation and outputs the errors or if it passes */
    public static void validation(){
        if(leap == 1){
            System.out.println("Valid date: " + day + separator + month + separator + year);
            valid = false;
        }
        if(dayErr == true){
            System.out.println(day + separator + month + separator + year + " - INVALID: Day is wrong.");
            valid = false;
        }
        if(!yearLength){
            System.out.println(day + separator + month + separator + year + " - INVALID: Year is wrong.");
            valid = false; 
        }
        if(sepErr == 1){
            System.out.println(day + separator + month + separator + year + " - INVALID: Seperators are different.");
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
        leap = 0;
        counter = 0;
        sepErr = 0;
        index = 0;
        check = 0;
        yearLength = false;
        valid = true;
        dayErr = false;
        dateCheck = false;
        monthErr = false;
        yearRange = true;
        System.out.println("");
        input();
    }
}