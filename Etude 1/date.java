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
    static int thirdCheck = 0;
    static int yearLength = 0;

    public static void main(String[]args) {
        input();
    }

    public static void input(){
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
            int second;

            if(date.charAt(i) == separator){
                start = i;
                day = date.substring(0, date.indexOf(separator));
                month = date.substring(date.indexOf(separator), date.indexOf(separator, start));
                year = date.substring(date.indexOf(separator, start) + 1, date.length());
                second = date.indexOf(separator, start);
                thirdCheck = date.indexOf(separator, second);
            }
        }
        month = month.substring(1,month.length());

        if(year.length() == 2){
            plusYear(year);
        }

        checkYearLength();
        monthConvert();
        leapYearCheck();
        validation();
    }

    public static void plusYear(String year){
        year = "20" + year;
    }

    public static void checkYearLength(){
        if(year.length() != 2 || year.length() != 4){
            System.out.println(year.length());
            yearLength++;
        }
    }

    public static void monthConvert(){
        String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int numMonth = 0;
        for(int x = 0; x < monthList.length; x++){
            numMonth = Integer.parseInt(month);
            if(numMonth == x){
                month = monthList[x];
            }
        }

        String[] lowerMonthList = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
        for(int j = 0; j < lowerMonthList.length; j++){
            if(month == lowerMonthList[j]){
                month = monthList[j];
            }
        }
    }

    public static void leapYearCheck(){
        if ((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100!= 0) || (Integer.parseInt(year) % 400 == 0) && month == "Feb" && Integer.parseInt(day) == 29){
            leap++;
        }
    }
        
    public static void validation(){
        if(leap == 1){
            System.out.println(day + separator + month + separator + year + " - Is a Leap year.");
        }else if(Integer.parseInt(year) < 1753 && Integer.parseInt(year) > 3000){
            System.out.println(day + separator + month + separator + year + " - INVALID: Year is wrong.");
        }else if(yearLength == 1){
            System.out.println("hello");
        }else if(sepErr == 1 || thirdCheck > 0){
            System.out.println(day + separator + month + separator + year + " - INVALID: Seperators are wrong.");
        }else{
            //if valid
            System.out.println("Date: " + day + separator + month + separator + year);
        }
    }
}