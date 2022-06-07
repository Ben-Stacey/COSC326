import java.util.Scanner;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.io.*;
import java.util.StringTokenizer;
import java.io.BufferedReader;

import javax.swing.text.FieldView;
import javax.tools.DocumentationTool.Location;

/**
 * @author Ben Stacey 2157359
 *
 *  This program takes in input through stdin and converts it to GEO json output
 *  for maps
 */
public class Convert {
    private static ArrayList<String> store = new ArrayList<String>();
    private static ArrayList<String> lines = new ArrayList<String>();
    private static ArrayList<String> input = new ArrayList<String>();
    private static String latitude = "";
    private static String longitude = "";
    private static String location = "";
    private static int holding = 0;
    private static int counter = 0;
    private static int lineCount = 0;
    private static int index = 0;
    private static String line = "";

    public static void main(String[]args){
        input();
    }

    public static void reset(){
            latitude = "";
            location = "";
            latitude = "";
            holding = 0;
            counter = 0;
            store.clear();
    }

    /** Gets in the input from stdin */
    public static void input(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter coordinates: ");
        System.out.println("");
        System.out.println("Example: ");
        System.out.println("45.9 s, 170.5 E Dunedin");
        System.out.println("");

        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            input.add(line);
            lineCount++;
        }
        try{
            for(String line: input){
                reset();
                index++;
                if(isAltDMS(line)){
                    StringTokenizer str = new StringTokenizer(line);
                    while(str.hasMoreTokens()){
                        store.add(str.nextToken());
                        holding++;
                    }
                    if(holding == 2){
                        altDMSProcess(line);
                    }else if(holding == 3){
                        altDMSProcessLocation(line);
                    }
                }else if(isDMS(line)){
                    DMSProcess(line);
                }else if(isDMM(line)){
                    DMMProcess(line);
                } else if(isNonDMS(line) && !(isDMM(line)) && !(isDMS(line))){
                    nonDMSProcess(line);
                }
                /** If non of the first statements are triggered then it is assumed the entry is either invalid 
                 * or in standard form. The else statements sorts the input and if it passes the checks then adds
                 * it to the json file or it exits once it finds the input is invalid
                 */
                else if(!(isNonDMS(line)) && !(isDMM(line)) && !(isDMS(line))){
                    StringTokenizer str = new StringTokenizer(line);
                    while(str.hasMoreTokens()){
                        store.add(str.nextToken());
                        counter++;
                    }

                    if(counter < 2){
                        System.out.println(line + " - Invalid Input");
                    }else if(counter >= 10 && counter <= 13){
                        autisticDMS();
                    }else{
                        standard();
                    }
                }else{
                    System.out.println(line + " - Invalid Input");
                }
                //ans();
            writeToFile();
            }
            
        }catch(NumberFormatException e){
            System.out.println(line + " - Invalid Input");
        }catch(StringIndexOutOfBoundsException e){
            System.out.println(line + " - Invalid Input");
        }
    }

     /** sorts for standard form */
     public static void standard(){
        String first = "";
        String second = "";
        String test = "";
        Boolean check = false;

        for(int i = 0; i < counter; i++){
            String temp = store.get(i);
            if(temp.indexOf(",") == temp.length() - 1){
                temp = temp.substring(0, temp.length() - 1);
            }
        
            if(isDouble(temp)){
                if(checkLatitude(temp)){
                    latitude = temp;
                }else if(checkLongitude(temp)){
                    longitude = second + temp;
                }else{
                    System.out.println(line + " - Invalid Input");
                }
            }else if(isString(temp) && !isDouble(temp)){
                if(checkLocation(temp)){
                    location = temp;
                }
            }else{
                System.out.println(line + " - Invalid Input");
            }

            if(check == false){
                if(temp.equals("S") || temp.equals("W")){
                    longitude = "-" + longitude;
                }else if(temp.equals("N") || temp.equals("E")){
                    test += "+";
                }
                check = true;
            }else if(check == true){
                if(temp.equals("S") || temp.equals("W")){
                    latitude = "-" + latitude;
                }else if(temp.equals("N") || temp.equals("E")){
                    test += "+";
                }
            }
        }
        //ans();
        add();
    }

    /** */
    public static void autisticDMS(){
        double countOne = 0;
        double countTwo = 0;
        String first = "";
        String second = "";
        String test = "";
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        Boolean check = false;

        for(int i = 0; i < counter; i++){
            String temp = store.get(i);
            if(temp.contains(",")){
                temp = temp.substring(0, temp.length() - 1);
                check = true;
            }

            //first half
            if(i == 0 && isInt(temp) || isDouble(temp) && one == ""){
                one = temp;
            }

            if(i == 2 && isInt(temp) || isDouble(temp) && two == ""){
                two = temp;
            }

            if(i == 4 && three == ""){
                temp = temp.substring(0, temp.length() - 1);
                if(isDouble(temp)){
                    three = temp;
                }
            }

            //second half
            if(check == true){
                if(i == 6 && (isInt(temp) || isDouble(temp))){
                    four = temp;
                }

                if(isInt(temp) || isDouble(temp) && i == 8){
                    five = temp;
                }

                if(i == 10 && five != "" && six == ""){
                    temp = temp.substring(0, temp.length() - 1);
                    if(isDouble(temp)){
                        six = temp;
                    }
                }

                if(isString(store.get(counter - 1))){
                    if(!(temp.equals("S") || temp.equals("E") || temp.equals("N") || temp.equals("W"))){
                        location = store.get(counter - 1);
                    }
                }
            }
            
            if(check == false){
                if(temp.contains("S") || temp.contains("W")){
                    first += "-";
                }else if(temp.contains("N") || temp.contains("E")){
                    test += "+";
                }
            }else if(check == true){
                if(temp.contains("S") || temp.contains("W")){
                    second += "-";
                }else if(temp.contains("N") || temp.contains("E")){
                    test += "+";
                }
            }
        }

        countOne += Double.parseDouble(one);
        countOne += Double.parseDouble(two) / 60.0;
        countOne += Double.parseDouble(three) / 3600.0;

        countTwo += Double.parseDouble(four);
        countTwo += Double.parseDouble(five) / 60.0;
        countTwo += Double.parseDouble(six) / 3600.0;

        latitude = second + countOne;
        longitude = first + countTwo;

        if(latitude.contains(",")){
            latitude.substring(0, latitude.length() - 1);
        }

        if(longitude.contains(",")){
            longitude.substring(0, longitude.length() - 1);
        }

        if(checkLatitude(latitude) && checkLongitude(longitude)){
            add();
        }else{
            System.out.println(line + " - Invalid Input");
        }
    }

    /** This process DMS with spaces and that has a location attached */
    public static void altDMSProcessLocation(String line){
        String[] temp = line.split(" ");
        String x = temp[0];
        String y = temp[1];
        String z = temp[2];
        double countOne = 0.0;
        double countTwo = 0.0;
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        String first = "";
        String second = "";
        String test = "";

        if(x.contains("S") || x.contains("W")){
            first += "-";
        }else if(x.contains("N") || x.contains("E")){
            test += "+";
        }

        if(y.contains("S") || y.contains("W")){
            second += "-";
        }else if(y.contains("N") || y.contains("E")){
            test += "+";
        }

        if(x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S'){
            x = x.substring(0, x.length() - 1);
            one = x.substring(0, x.indexOf("\u00B0"));
            two = x.substring(x.indexOf("\u00B0") + 1, x.indexOf("'"));
            three = x.substring(x.indexOf("'") + 1, x.length() - 1);
        }else if(x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S'){
            x = x.substring(0, x.length() - 1);
            one = x.substring(0, x.indexOf("\u00B0"));
            two = x.substring(x.indexOf("\u00B0") + 1, x.indexOf("'"));
            three = x.substring(x.indexOf("'") + 1, x.length() - 1);
        }else{
            one = x.substring(0, x.indexOf("\u00B0"));
            two = x.substring(x.indexOf("\u00B0") + 1, x.indexOf("'"));
            three = x.substring(x.indexOf("'") + 1, x.length() - 1);
        }

        //y = y.substring(1, x.length() - 1);

        if(y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S'){
            y = y.substring(0, y.length() - 1);
            four = y.substring(0, y.indexOf("\u00B0"));
            five = y.substring(y.indexOf("\u00B0") + 1, y.indexOf("'"));
            six = y.substring(y.indexOf("'") + 1, y.length() - 2); 
        }else if(y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S'){
            y = y.substring(0, y.length() - 1);
            four = y.substring(0, y.indexOf("\u00B0"));
            five = y.substring(y.indexOf("\u00B0") + 1, y.indexOf("'"));
            six = y.substring(y.indexOf("'") + 1, y.length() - 2); 
        }else{
            four = y.substring(0, y.indexOf("\u00B0"));
            five = y.substring(y.indexOf("\u00B0") + 1, y.indexOf("'"));
            six = y.substring(y.indexOf("'") + 1, y.length() - 2); 
        }

        countOne += Double.parseDouble(one);
        countOne += Double.parseDouble(two) / 60.0;
        countOne += Double.parseDouble(three) / 3600.0;

        countTwo += Double.parseDouble(four);
        countTwo += Double.parseDouble(five) / 60.0;
        countTwo += Double.parseDouble(six) / 3600.0;

        latitude = first + countOne;
        longitude = second + countTwo;
        location = z;

        if(checkLatitude(latitude) && checkLongitude(longitude)){
            add();
        }else{
            System.out.println(line + " - Invalid Input");
        }
    }

    /** If the input contains the values of DMS but there is no spaces in the input
     * string then this processes it 
    */
    public static void altDMSProcess(String line){
        String[] temp = line.split(" ");
        String x = temp[0];
        String y = temp[1];
        double countOne = 0.0;
        double countTwo = 0.0;
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        String first = "";
        String second = "";
        String test = "";

        if(x.contains("S") || x.contains("W")){
            first += "-";
        }else if(x.contains("N") || x.contains("E")){
            test += "+";
        }

        if(y.contains("S") || y.contains("W")){
            second += "-";
        }else if(y.contains("N") || y.contains("E")){
            test += "+";
        }

        if(x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S'){
            x = x.substring(0, x.length() - 1);
            one = x.substring(0, x.indexOf("\u00B0"));
            two = x.substring(x.indexOf("\u00B0") + 1, x.indexOf("'"));
            three = x.substring(x.indexOf("'") + 1, x.length() - 1);
        }else if(x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S' || x.charAt(x.length() - 1) == 'S'){
            x = x.substring(0, x.length() - 1);
            one = x.substring(0, x.indexOf("\u00B0"));
            two = x.substring(x.indexOf("\u00B0") + 1, x.indexOf("'"));
            three = x.substring(x.indexOf("'") + 1, x.length() - 1);
        }else{
            one = x.substring(0, x.indexOf("\u00B0"));
            two = x.substring(x.indexOf("\u00B0") + 1, x.indexOf("'"));
            three = x.substring(x.indexOf("'") + 1, x.length() - 1);
        }

        //y = y.substring(1, x.length() - 1);

        if(y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S'){
            y = y.substring(0, y.length() - 1);
            four = y.substring(0, y.indexOf("\u00B0"));
            five = y.substring(y.indexOf("\u00B0") + 1, y.indexOf("'"));
            six = y.substring(y.indexOf("'") + 1, y.length() - 2); 
        }else if(y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S' || y.charAt(y.length() - 1) == 'S'){
            y = y.substring(0, y.length() - 1);
            four = y.substring(0, y.indexOf("\u00B0"));
            five = y.substring(y.indexOf("\u00B0") + 1, y.indexOf("'"));
            six = y.substring(y.indexOf("'") + 1, y.length() - 2); 
        }else{
            four = y.substring(0, y.indexOf("\u00B0"));
            five = y.substring(y.indexOf("\u00B0") + 1, y.indexOf("'"));
            six = y.substring(y.indexOf("'") + 1, y.length() - 2); 
        }

        countOne += Double.parseDouble(one);
        countOne += Double.parseDouble(two) / 60.0;
        countOne += Double.parseDouble(three) / 3600.0;

        countTwo += Double.parseDouble(four);
        countTwo += Double.parseDouble(five) / 60.0;
        countTwo += Double.parseDouble(six) / 3600.0;

        latitude = first + countOne;
        longitude = second + countTwo;

        if(checkLatitude(latitude) && checkLongitude(longitude)){
            add();
        }else{
            System.out.println(line + " - Invalid Input");
        }
    }   

    /** If the input is in the format of Degree Minute Second without the standard markers then this statement is triggered 
     * and then it sorts the entry and calculates the latitude and longitude
     */
    public static void nonDMSProcess(String line){
        String[] temp = line.split(" ");
        String first = "";
        String second = "";
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        String z = "";
        String y = "";
        String test = "";
        double countOne = 0;
        double countTwo = 0;
        int err = 0;

        one = temp[0];
        two = temp[1];
        three = temp[2];
        four = temp[4];
        five = temp[5];
        six = temp[6];

        z = temp[3];
        y = temp[7];

        if(temp.equals("S") || temp.equals("W")){
            first += "-";
        }else if(temp.equals("N") || temp.equals("E")){
            test += "+";
        }

        if(temp.equals("S") || temp.equals("W")){
            second += "-";
        }else if(temp.equals("N") || temp.equals("E")){
            test += "+";
        }

        countOne += Double.parseDouble(one);
        countOne += Double.parseDouble(two) / 60.0;
        countOne += Double.parseDouble(three) / 3600.0;

        countTwo += Double.parseDouble(four);
        countTwo += Double.parseDouble(five) / 60.0;
        countTwo += Double.parseDouble(six) / 3600.0;

        latitude = first + countOne;
        longitude = second + countTwo;

        if(checkLatitude(latitude) && checkLongitude(longitude)){
            add();
        }else{
            System.out.println(line + " - Invalid Input");
        }
    }

    /** If the input is in the format of Degree and Decimal Minutes then this statement is triggered 
     * and then it sorts the entry and calculates the latitude and longitude
     */
    public static void DMMProcess(String line){
        String[] temp = line.split(" ");
        String first = "";
        String second = "";
        String one = "";
        String two = "";
        String four = "";
        String five = "";
        String test = "";
        String z = "";
        String y = "";
        double countOne = 0.0;
        double countTwo = 0.0;

        z = temp[2];
        y = temp[5];

        if(temp.equals("S") || temp.equals("W")){
            first += "-";
        }else if(temp.equals("N") || temp.equals("E")){
            test += "+";
        }

        if(temp.equals("S") || temp.equals("W")){
            second += "-";
        }else if(temp.equals("N") || temp.equals("E")){
            test += "+";
        }

        if(temp[0].contains("\u00B0")){
            String x = temp[0];
            x = x.substring(0, x.length() - 1);
            one = x;
        }

        if(temp[1].contains("'")){
            String x = temp[1];
            x = x.substring(0, x.length() - 1);
            two = x;
        }

        if(temp[3].contains("\u00B0")){
            String x = temp[3];
            x = x.substring(0, x.length() - 1);
            four = x;
        }

        if(temp[4].contains("'")){
            String x = temp[4];
            x = x.substring(0, x.length() - 1);
            five = x;
        }

        countOne += Double.parseDouble(one);
        countOne += Double.parseDouble(two) / 60.0;

        countTwo += Double.parseDouble(four);
        countTwo += Double.parseDouble(five) / 60.0;

        latitude = first + countOne;
        longitude = second + countTwo;

        if(checkLatitude(latitude) && checkLongitude(longitude)){
            add();
        }else{
            System.out.println(line + " - Invalid Input");
        }
    }

    /** If the input is in the format of Degree Minute Second then this statement is triggered 
     * and then it sorts the entry and calculates the latitude and longitude
     */
    public static void DMSProcess(String line){
        String[] temp = line.split(" ");
        String first = "";
        String second = "";
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        String z = "";
        String y = "";
        String test = "";
        double countOne = 0.0;
        double countTwo = 0.0;
        char c = '"';

        z = temp[3];
        y = temp[7];

        if(temp.equals("S") || temp.equals("W")){
            first += "-";
        }else if(temp.equals("N") || temp.equals("E")){
            test += "+";
        }

        if(temp.equals("S") || temp.equals("W")){
            second += "-";
        }else if(temp.equals("N") || temp.equals("E")){
            test += "+";
        }

        if(temp[0].contains("\u00B0")){
            String x = temp[0];
            x = x.substring(0, x.length() - 1);
            one = x;
        }

        if(temp[1].contains("'")){
            String x = temp[1];
            x = x.substring(0, x.length() - 1);
            two = x;
        }

        if(temp[2].contains(Character.toString(c))){
            String x = temp[2];
            x = x.substring(0, x.length() - 1);
            three = x;
        }

        if(temp[4].contains("\u00B0")){
            String x = temp[4];
            x = x.substring(0, x.length() - 1);
            four = x;
        }

        if(temp[5].contains("'")){
            String x = temp[5];
            x = x.substring(0, x.length() - 1);
            five = x;
        }

        if(temp[6].contains(Character.toString(c))){
            String x = temp[6];
            x = x.substring(0, x.length() - 1);
            six = x;
        }
        countOne += Double.parseDouble(one);
        countOne += Double.parseDouble(two) / 60.0;
        countOne += Double.parseDouble(three) / 3600.0;

        countTwo += Double.parseDouble(four);
        countTwo += Double.parseDouble(five) / 60.0;
        countTwo += Double.parseDouble(six) / 3600.0;

        latitude = first + countOne;
        longitude = second + countTwo;

        //ans();
        if(checkLatitude(latitude) && checkLongitude(longitude)){
            add();
        }else{
            System.out.println(line + " - Invalid Input");
        }
    }

    /** adds the json structure to the arraylist to be 
     * inserted into the json file
     */
    public static void add(){
        locationCheck();
        if(lineCount == index){
            lines.add("{\n" +
                    "      \"type\": \"Feature\",\n" +
                    "      \"geometry\": {\n" +
                    "          \"type\": \"Point\",\n" +
                    "          \"coordinates\": [" + longitude + ", " + latitude + "]\n" +
                    "      },\n" +
                    "      \"properties\": {\n" +
                    "          \"name\": \"" + location + "\"\n" +
                    "      }\n" +
                    "   }\n");
        }else{
            lines.add("{\n" +
                    "      \"type\": \"Feature\",\n" +
                    "      \"geometry\": {\n" +
                    "          \"type\": \"Point\",\n" +
                    "          \"coordinates\": [" + longitude + ", " + latitude + "]\n" +
                    "      },\n" +
                    "      \"properties\": {\n" +
                    "          \"name\": \"" + location + "\"\n" +
                    "      }\n" +
                    "   },\n");
        }
        location = "";
    }

    /** Checks to see if the input is in Degrees Minutes Seconds format */
    public static Boolean isDMS(String token){
        char c = '"';
        String[] temp = token.split(" ");
        if(temp[0].contains("\u00B0") && temp[1].contains("'") && temp[2].contains(Character.toString(c))){
            return true;
        }
        return false;
    }

    /** Checks to see if the input is in Degrees and Decimal Minutes */
    public static Boolean isDMM(String token){
        String[] temp = token.split(" ");
        if(temp[0].contains("\u00B0") && temp[1].contains("'")){
            return true;
        }
        return false;
    }

    /** checks for DMS without spaces */
    public static Boolean isAltDMS(String line){
        char c = '"';
        if(line.contains("\u00B0") && line.contains("'") && line.contains(Character.toString(c))){
            return true;
        }
        return false;
    }

    public static void locationCheck(){
        if(location == "S" || location == "W" || location == "E" || location == "N"){
            System.out.println("made it");
            location = "";
        }else if(location == "s" || location == "n" || location == "e" || location == "w"){
            location = "";
        }
    }

    /** Checks to see if the input is in Degrees Minutes Seconds format without the standard markers */
    public static Boolean isNonDMS(String token){
        char c = '"';
        String[] temp = token.split(" ");
        if(!(temp[0].contains("\u00B0") && temp[1].contains("'") && temp[2].contains(Character.toString(c)))){
            String x = "";
            String y = "";
            String z = "";
            String a = "";
            String b = "";
            String i = "";
            int counter = 0;

            try{
                x = temp[0];
                y = temp[1];
                z = temp[2];
                a = temp[4];
                b = temp[5];
                i = temp[6];

                if(isDouble(x)){
                    counter++;
                }
                if(isDouble(y)){
                    counter++;
                }
                if(isDouble(z)){
                    counter++;
                }
                if(isDouble(a)){
                    counter++;
                }
                if(isDouble(b)){
                    counter++;
                }
                if(isDouble(i)){
                    counter++;
                }

            }catch(NumberFormatException e){
                return false;
            }catch(IndexOutOfBoundsException e){
                return false;
            }

            if(counter == 6){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /** Checks if a value is a String */
    public static Boolean isString(String x){
        if(x == null){
            return false;
        }
        try{
            String d = (String) x;
        }catch (NumberFormatException e) {
            return false;
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    /** Checks if a value is a int */
    public static Boolean isInt(String x){
        if(x == null){
            return false;
        }
        try{
            int d = Integer.parseInt(x);
        }catch (NumberFormatException e) {
            return false;
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    /** Checks if a value is a double */
    public static Boolean isDouble(String x){
        if(x == null){
            return false;
        }
        try{
            double d = Double.parseDouble(x);
        }catch (NumberFormatException e) {
            return false;
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    /** checks see if the token is valid to be the location */
    public static Boolean checkLocation(String token){
        if(token.contains(",")){
            return false;
        } else if(token.contains(".")){
            return false;
        } else if(token.contains("/")){
            return false;
        } else if(token.contains("'")){
            return false;
        } else if(token.contains(";")){
            return false;
        } else if(token.contains(":")){
            return false;
        } else if(token.contains("[")){
            return false;
        } else if(token.contains("]")){
            return false;
        } else if(token.contains("{")){
            return false;
        } else if(token.contains("}")){
            return false;
        } else if(token.contains("-")){
            return false;
        } else if(token.contains("=")){
            return false;
        } else if(token.contains("+")){
            return false;
        } else if(token.contains("_")){
            return false;
        }
        if(token.contains("S") || token.contains("E") || token.contains("N") || token.contains("W")){
            return false;
        }
        return true;
    }

    /** Finds the lattidude values in the program */
    public static Boolean checkLatitude(String token){
        try{
            Double temp = Double.parseDouble(token);
            if(temp >= -90.000000 && temp <= 90.000000 && token.charAt(0) != '0'){
                return true;
            }else{
                return false;
            }
        }catch(NumberFormatException e){
            System.out.println(line + " - Invalid Input");
            return false;
        }catch(NullPointerException e){
            System.out.println(line + " - Invalid Input");
            return false;
        }
    }

    /** Finds the longitude values in the program */
    public static Boolean checkLongitude(String token){
        try{
            Double temp = Double.parseDouble(token);
            if(temp >= -180.000000 && temp <= 180.000000 && token.charAt(0) != '0') {
                return true;
            }else{
                return false;
            }
        }catch(NumberFormatException e){
            System.out.println(line + " - Invalid Input");
            return false;
        }catch(NullPointerException e){
            System.out.println(line + " - Invalid Input");
            return false;
        }
    }

    /** displays helpful information */
    public static void ans(){
        System.out.println("");
        System.out.println("Latitude " + latitude);
        System.out.println("Longitude " + longitude);
        System.out.println("Location " + location);
        System.out.println("");
    }

    /** writes the coordinates to the json file */
    public static void writeToFile(){
        try {
            File file = new File("output.txt");
            if(file.createNewFile()){
                System.out.println("File created: " + file.getName());
            }else{
                System.out.println("File already exists");
            }
        }catch(IOException e){
            System.out.println("Error making a file");
        }

        try(
                FileWriter fileWriter = new FileWriter("output.txt");
                BufferedWriter writer = new BufferedWriter(fileWriter)){

            writer.write("{\n" +
                    "  \"type\": \"FeatureCollection\",\n" +
                    "  \"features\": [\n");

            for (String out: lines) {
                writer.write(out);
            }

            writer.write("]\n" +
                    "}");

            System.out.println("Successfully written to file");
        }catch(IOException e){
            System.out.println("Error writing to the file");
        }
    }
}