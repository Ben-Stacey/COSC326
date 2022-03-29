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
    static int dayErr = 0;

    public static void main(String[]args) {
        input();
    }

    public static void input(){
        dayErr = 0;
        leap = 0;
        counter = 0;
        sepErr = 0;
        index = 0;
        check = 0;
        yearLength = false;
        valid = true;
        
        System.out.println("Enter a date:");
        Scanner scan = new Scanner(System.in);
        String date = scan.nextLine();
        checkSeperator(date);
    }

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
            dayErr++;
        }

        for(int x = 0; x < date.length(); x++){
            if(date.charAt(x) == separator){
                check++;
                System.out.println(check);
            }
        }

        month = month.substring(1,month.length());

        if(year.length() == 2){
            plusYear();
        }

        checkYearLength();
        monthConvert();
        leapYearCheck();
        validation();
    }

    public static void plusYear(){
        year = "20" + year;
    }

    public static void checkYearLength(){
        if(year.length() != 2 || year.length() != 4){
            yearLength = true;
        }
    }

    public static void monthConvert(){
        String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if(month.length() == 3){
            return; 
        }

        int numMonth = Integer.parseInt(month);

        if(month.charAt(0) == '0'){
            month = month.substring(1);
        }
        System.out.println(month);

        for(int x = 0; x < monthList.length; x++){
            if(numMonth - 1 == x){
                month = monthList[x];
            }
        }

        month = month.toUpperCase();
    }

    public static void leapYearCheck(){
        if ((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100!= 0) || (Integer.parseInt(year) % 400 == 0) && month == "Feb" && Integer.parseInt(day) == 29){
            leap++;
        }
    }
        
    public static void validation(){
        if(leap == 1){
            System.out.println(day + separator + month + separator + year + " - Is a Leap year.");
            valid = false;
            
        }
        if(dayErr == 1){
            System.out.println(day + separator + month + separator + year + " - INVALID: Day is wrong.");
            valid = false;
        }
        if(Integer.parseInt(year) < 1753 && Integer.parseInt(year) > 3000){
            System.out.println(day + separator + month + separator + year + " - INVALID: Year is wrong.");
            valid = false;
        }
        if(!yearLength){
            System.out.println(day + separator + month + separator + year + " - INVALID: Length of year is wrong.");
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
        if(valid){
            //if valid
            System.out.println("Valid date: " + day + separator + month + separator + year);
            
        }
        input();
    }
}