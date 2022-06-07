public import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Inputs program must accept...
 *
 * Standard Form:
 * 90.000000, -180.000000 Dunedin
 * -90.000000, 180.000000
 *
 * Standard form except the number of decimal points != 6:
 * 90.0, -180.0 Dunedin
 * -90.0, 180.0
 *
 * Standard form except the comma is missing:
 * 90.000000 -180.000000 Dunedin
 * -90.000000 180.000000
 *
 * Standard form except the numbers are non-negative and are followed by N/S or E/W (possibly have NS/EW in the wrong order):
 * 45.3 S, 70.7 W Dunedin
 * 45.3 W, 70.7 S
 *
 * "Degrees, minutes, seconds" form, with or without decimal points on the seconds and with or without standard marker for degrees/minutes/seconds:
 * 32° 18' 23.1" Dunedin
 * 32° 18' 23.1"
 *
 * Degrees and decimal minutes form:
 * 32° 18.385' Dunedin
 * 32° 18.385'
 *
 * Output Template:
 *
 * {
 *     "type": "Feature",
 *     "geometry": {
 *         "type": "Point",
 *         "coordinates": [125.6, 10.1]
 *     },
 *     "properties": {
 *         "name": "Dinagat Islands"
 *     }
 * }
 *
 */

public class Where {
    private ArrayList<String> lines;
    private ArrayList<String> tokens;
    private ArrayList<String> output;

    private int lineCount;
    private int tokenCount;
    private int partCount;

    private String latitude;
    private String longitude;
    private String label;

    private boolean dms;

    public static void main(String[] args){
        new Where().start();
    }

    public void start(){
        System.out.println("Enter latitude and longitude: \n");
        Scanner scanner = new Scanner(System.in);

        lines = null;
        lines = new ArrayList<String>();
        output = null;
        output = new ArrayList<String>();

        lineCount = 0;
        tokenCount = 0;
        partCount = 0;

        while (scanner.hasNextLine()){
            String temp = scanner.nextLine();
            lines.add(temp);
            lineCount++;
        }

        //for each line of input
        for (int z = 0; z < lines.size(); z++){
            latitude = "";
            longitude = "";
            label = "";
            tokens = null;
            tokens = new ArrayList<String>();
            dms = false;

            String line = lines.get(z);
            Scanner lineScanner = new Scanner(line);

            while (lineScanner.hasNext()){
                String temp = lineScanner.next();
                tokens.add(temp);
                tokenCount++;
            }

            //for each token/word in the line
            for (int i = 0; i < tokens.size(); i++){
                String temp = tokens.get(i);
                char c = temp.charAt(temp.length() - 1);
                if(c == '°' || c == '\'' || c == '\"'){
                    temp = temp.substring(0, temp.length() - 1);
                    dms = true;
                }else if (c == ','){
                    temp = temp.substring(0, temp.length() - 1);
                }

                if (i == 0){
                    latitude = temp;
                }else if(i == 1){
                    if (temp.equals("N") || temp.equals("S") || temp.equals("E") || temp.equals("W")){

                    }else{
                        longitude = temp;
                    }
                }else if(i == 2 && !dms){
                    if (temp.equals("N") || temp.equals("S") || temp.equals("E") || temp.equals("W")){

                    }else{
                        try{
                            double longi = Double.parseDouble(temp);
                            longitude = String.valueOf(longi);
                        }catch(Exception e){
                            label = temp;
                        }
                    }
                }else if(i == 3){
                    if (temp.equals("N") || temp.equals("S") || temp.equals("E") || temp.equals("W")){

                    }else{
                        label = temp;
                    }
                }else if(i == 4){
                    if (temp.equals("N") || temp.equals("S") || temp.equals("E") || temp.equals("W")){

                    }else{
                        label = temp;
                    }
                }
            }


            if (z == (lines.size() - 1)) {
                output.add("{\n" +
                        "      \"type\": \"Feature\",\n" +
                        "      \"geometry\": {\n" +
                        "          \"type\": \"Point\",\n" +
                        "          \"coordinates\": [" + latitude + ", " + longitude + "]\n" +
                        "      },\n" +
                        "      \"properties\": {\n" +
                        "          \"name\": \"" + label + "\"\n" +
                        "      }\n" +
                        "   }\n");
            }else{
                output.add("{\n" +
                        "      \"type\": \"Feature\",\n" +
                        "      \"geometry\": {\n" +
                        "          \"type\": \"Point\",\n" +
                        "          \"coordinates\": [" + latitude + ", " + longitude + "]\n" +
                        "      },\n" +
                        "      \"properties\": {\n" +
                        "          \"name\": \"" + label + "\"\n" +
                        "      }\n" +
                        "   },\n");
            }
        }

        try {
            File outputFile = new File("output.txt");
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred creating the file.");
            e.printStackTrace();

        }

        try (FileWriter fileStream = new FileWriter("output.txt");
             BufferedWriter writer = new BufferedWriter(fileStream)) {

            writer.write("{\n" +
                    "  \"type\": \"FeatureCollection\",\n" +
                    "  \"features\": [\n");

            for (String out: output) {
                writer.write(out);
            }

            writer.write("]\n" +
                    "}");
            System.out.println("\n\nSuccessfully wrote to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class where {
    
}
