import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.StringTokenizer;
import java.io.BufferedWriter;

/**
 * @author 
 *
 *  This program takes in input through stdin and converts it to GEO JSON format
 */
public class Etude07 {
    private static ArrayList<String> store = new ArrayList<String>();
    private static ArrayList<String> lines = new ArrayList<String>();
    private static ArrayList<String> input = new ArrayList<String>();
    private static String latitude = "";
    private static String longitude = "";
    private static String location = "";
    private static String first = "";
    private static String second = "";
    private static int holding = 0;
    private static int counter = 0;
    private static int lineCount = 0;
    private static int index = 0;
    private static String line = "";

    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter coordinates: ");
        System.out.println("");

        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            input.add(line);
            lineCount++;
        }
        try{
            for(String line: input){
                clear();
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
                } else if(isDMSspaced(line) && !(isDMM(line)) && !(isDMS(line))){
                    nonDMSProcess(line);
                } else if(!(isDMSspaced(line)) && !(isDMM(line)) && !(isDMS(line))){
                    StringTokenizer str = new StringTokenizer(line);
                    while(str.hasMoreTokens()){
                        store.add(str.nextToken());
                        counter++;
                    }

                    if(counter < 2){
                        System.out.println(line + " - Invalid Input");
                    }else if(counter >= 10 && counter <= 13){
                        spacedDMS();
                    }else{
                        standardForm();
                    }
                }else{
                    System.out.println(line + " - Invalid Input");
                }
            writeToFile();
            }
        }catch(NumberFormatException e){
            System.out.println(line + " - Invalid Input");
        }catch(StringIndexOutOfBoundsException e){
            System.out.println(line + " - Invalid Input");
        }
    }

     /** Deals with standard form */
     public static void standardForm(){
        String first = "";
        String second = "";
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
                }
                check = true;
            }else if(check == true){
                if(temp.equals("S") || temp.equals("W")){
                    latitude = "-" + latitude;
                }
            }
        }
        add();
    }

    /**
     * Deals with DMS format that is x d x m x.x s
     */
    public static void spacedDMS(){
        String f = "";
        String s = "";
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
                    f += "-";
                }
            }else if(check == true){
                if(temp.contains("S") || temp.contains("W")){
                    s += "-";
                }
            }
        }

        latLongCreator(f, s, one, two, three, four, five, six);
    }

    /** This process DMS with spaces and that has a location attached 
     *
     * @param line - input
     */
    public static void altDMSProcessLocation(String line){
        String[] temp = line.split(" ");
        String x = temp[0];
        String y = temp[1];
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";

        findCompassDirection(x, y);

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

        latLongCreator(first, second, one, two, three, four, five, six);
    }

    /** If the input contains the values of DMS but there is no spaces in the input
     * string then this processes it 
     * 
     * @param line - input
     */
    public static void altDMSProcess(String line){
        String[] temp = line.split(" ");
        String x = temp[0];
        String y = temp[1];
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        String f = "";
        String s = "";

        if(x.contains("S") || x.contains("W")){
            f += "-";
        }

        if(y.contains("S") || y.contains("W")){
            s += "-";
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

        latLongCreator(f, s, one, two, three, four, five, six);
    }   

    /** If the input is in the format of Degree Minute Second without the standard markers then this statement is triggered 
     * and then it sorts the entry and calculates the latitude and longitude
    * 
     * @param line - input
     */
    public static void nonDMSProcess(String line){
        String[] temp = line.split(" ");
        String one = temp[0];
        String two = temp[1];
        String three = temp[2];
        String four = temp[4];
        String five = temp[5];
        String six = temp[6];
        String z = temp[3];
        String y = temp[7];

        findCompassDirection(z, y);

        latLongCreator(first, second, one, two, three, four, five, six);
    }

    /** If the input is in the format of Degree and Decimal Minutes then this statement is triggered 
     * and then it sorts the entry and calculates the latitude and longitude
     */
    public static void DMMProcess(String line){
        String[] temp = line.split(" ");
        String one = "";
        String two = "";
        String four = "";
        String five = "";
        String z = temp[2];
        String y = temp[5];
        double countOne = 0.0;
        double countTwo = 0.0;

        findCompassDirection(z, y);

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
     * 
     * @param line - input
     */
    public static void DMSProcess(String line){
        String[] temp = line.split(" ");
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        String z = temp[3];
        String y = temp[7];
        char c = '"';

        findCompassDirection(z, y);

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

        latLongCreator(first, second, one, two, three, four, five, six);
    }

    /**
     * Checks to see if the input is in Degrees Minutes Seconds format
     * 
     * @param token - input
     * @return - output
     */
    public static Boolean isDMS(String token){
        char c = '"';
        String[] temp = token.split(" ");
        if(temp[0].contains("\u00B0") && temp[1].contains("'") && temp[2].contains(Character.toString(c))){
            return true;
        }
        return false;
    }

    /** 
     * Checks to see if the input is in Degrees and Decimal Minutes
     * 
     * @param token - input
     * @return - output
     */
    public static Boolean isDMM(String token){
        String[] temp = token.split(" ");
        if(temp[0].contains("\u00B0") && temp[1].contains("'")){
            return true;
        }
        return false;
    }

    /**
     * Checks to see if it is the DMS form without spaces between 
     * 
     * @param line - input
     * @return - output
     */
    public static Boolean isAltDMS(String line){
        char c = '"';
        if(line.contains("\u00B0") && line.contains("'") && line.contains(Character.toString(c))){
            return true;
        }
        return false;
    }

    /** Checks to see if Location is a comapass direction and then clears it if it is */
    public static void emptyLocation(){
        if(location == "S" || location == "W" || location == "E" || location == "N"){
            location = "";
        }else if(location == "s" || location == "n" || location == "e" || location == "w"){
            location = "";
        }
    }

    /**
     * Checks to see if the input is in Degrees Minutes Seconds format without the standard markers
     * 
     * @param token - input
     * @return - output
     */
    public static Boolean isDMSspaced(String token){
        char c = '"';
        String[] temp = token.split(" ");
        if(!(temp[0].contains("\u00B0") && temp[1].contains("'") && temp[2].contains(Character.toString(c)))){
            String x = temp[0];
            String y = temp[1];
            String z = temp[2];
            String a = temp[4];
            String b = temp[5];
            String i = temp[6];
            int counter = 0;

            try{
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

    /**
     * Checks if value is a string 
     * 
     * @param x - input
     * @return - output
     */
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

   /**
     * Checks if value is a int 
     * 
     * @param x - input
     * @return - output
     */
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

    /**
     * Checks if value is a double 
     * 
     * @param x - input
     * @return - output
     */
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
    
    /**
     * Finds the compass directions that cause negative values
     * 
     * @param z - first section
     * @param y - second section
     */
    public static void findCompassDirection(String z, String y){
        if(z.equals("S") || z.equals("W")){
            first = "-";
        }

        if(y.equals("S") || y.equals("W")){
            second = "-";
        }
    }

    /** Creates lattitudes and longitudes from input values
     * 
     * @param first - input
     * @param second - input
     * @param one - input
     * @param two - input
     * @param three - input
     * @param four - input
     * @param five - input
     * @param six - input
     */
    public static void latLongCreator(String first, String second, String one, String two, String three, String four, String five, String six){
        Double x = 0.0;
        Double y = 0.0;
        x += Double.parseDouble(one);
        x += Double.parseDouble(two) / 60.0;
        x += Double.parseDouble(three) / 3600.0;

        y += Double.parseDouble(four);
        y += Double.parseDouble(five) / 60.0;
        y += Double.parseDouble(six) / 3600.0;

        latitude = second + x;
        longitude = first + y;

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

    /** Checks to see if input string is a the location 
     * 
     * @param token - input
     * @return - output 
     */
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

    /** Checks if input string is latitude
     * 
     * @param token - input
     * @return - output
     */
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

    /** Checks if input string is longitude 
     * 
     * @param token - input
     * @return - output
     */
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

    /** Adds JSON structure to the Arraylist */
    public static void add(){
        emptyLocation();
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

    /** Gets JSON format from Arraylist and adds it to the output file. Output file is create if it doesnt already exist */
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

    /** Clears variables for when the next line of input is read */
    public static void clear(){
        latitude = "";
        location = "";
        latitude = "";
        first = "";
        second = "";
        holding = 0;
        counter = 0;
        store.clear();
    }   
}