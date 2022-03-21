import java.util.Scanner;

public class date {
    public static void main(String[]args) {
        String day = "";
        String month = "";
        String separator = "";
        String year = "";
        int leap = 0;
        int counter = 0;

        //read in input
        Scanner scan = new Scanner(System.in);
        String date = scan.nextLine();

        //check seperator 
        for(int i = 0; i < date.length(); i++){
            if(date.charAt(i) == '-'){
                separator = "-";
            }
            if(date.charAt(i) == '/'){
                separator = "/";
            }
            if(date.charAt(i) == ' '){
                separator = " ";
            }
        }

        //sort into sections
        for(int i = 0; i < date.length(); i++){
            if(date.charAt(i) == separator& counter == 0){
                day = date.substring(0, i - 1);
            }
            
            if(date.charAt(i) == separator && counter == 0){
                counter++;
                for(int x = 0; x < date.length(); x++){
                    if(date.charAt(x) == separator && counter == 1)
                    month = date.substring(i + 1, x - 1);
                }
            }
        
            if(date.charAt(i) == '-' && counter == 1){
                year = date.substring(i + 1, date.length() - 1);
            }
        }
        /** 
        //date changer
        switch(month){
            case 1:
            month = "Jan";
            break;
            case 2:
            month = "Feb";
            break;
            case 3:
            month = "Mar";
            break;
            case 4:
            month = "Apr";
            break;
            case 5:
            month = "May";
            break;
            case 6:
            month = "Jun";
            break;
            case 7:
            month = "Jul";
            break;
            case 8:
            month = "Aug";
            break;
            case 9:
            month = "Sep";
            break;
            case 10:
            month = "Oct";
            break;
            case 11:
            month = "Nov";
            break;
            case 12:
            month = "Dec";
            break;
            default:
        }

        switch(month){
            case "jan":
               month = "Jan";
            break;
            case "feb":
            month = "Feb";
            break;
            case "mar":
            month = "Mar";
            break;
            case "apr":
            month = "Apr";
            break;
            case "may":
            month = "May";
            break;
            case "jun":
            month = "Jun";
            break;
            case "jul":
            month = "Jul";
            break;
            case "aug":
            month = "Aug";
            break;
            case "sep":
            month = "Sep";
            break;
            case "oct":
            month = "Oct";
            break;
            case "nov":
            month = "Nov";
            break;
            case "dec":
            month = "Dec";
            break;
            default:
            }

        if(year.length() == 2){
            String 20 = "20";
            year = 20 + year;
        }

        //leap year
        if ((year % 4 == 0) && (year % 100!= 0)) || (year % 400 == 0) && month == "Feb" && Integer.parseInt(day) == 29){
            leap++;
        }
            

        //validation
        if(leap == 1){
            System.out.println(day + separator + month + separator + year");
        }else if(leap == 0){
            System.out.println(day + separator + month + separator + year + "Leap year is wrong");
        }else if(Integer.parseInt(year) < 1753 && Integer.parseInt(year) > 3000){
            System.out.println(day + separator + month + separator + year + " - INVALID: Year out of range.");
        }else{
            //if valid
            System.out.println(day + separator + month + separator + year);
        }
        */
        System.out.println(day);
        System.out.println(month);
        System.out.println(year);
    }
}