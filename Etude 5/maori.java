import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

/**
 * Etude 5, Maori translation
 * @author Ben Stacey, 2157359
 * @author CGi, 9355607
 */

public class maori{
    private static Map<String, String> tense_dict = new HashMap<String, String>();
    private static Map<String, String> past_verb_dict = new HashMap<String, String>();
    private static Map<String, String> present_verb_dict = new HashMap<String, String>();
    private static Map<String, String> future_verb_dict = new HashMap<String, String>();
    private static Map<String, String> incl_dict = new HashMap<String, String>();
    private static Map<String, String> excl_list_dict = new HashMap<String, String>();
    private static Map<String, String> excl_speak_dict = new HashMap<String, String>();
    private static Map<String, String> no_speak_dict = new HashMap<String, String>();
    private static Map<String, String> singular_dict = new HashMap<String, String>();

    /** 
     * The main method adds the dictionaries to hashmaps, takes input strings line by line
     * and process the input line as sentence for translation
     */
    public static void main(String[]args){
        dictionary();
        System.out.println("Enter a sentence: ");
        String sentence; 
        Scanner input = new Scanner(System.in);        
        while ( input.hasNextLine() ) {
            sentence = input.nextLine();
            process(sentence);
        }
    }     

    // the process for converting / splitting the 'sentence' and display processed line/sentence
    public static void process(String sentence) {
        sentence = sentence.toLowerCase();        
        String sorting_sentence = sorting_sentence(sentence);
        String tense = find_tense(sentence); // short test for tense      
        String noBrackets = sentence.replaceAll("\\(.*\\) ", "");
        String[] words = noBrackets.split(" ");
        String verb = "";
        String newSentence = tense_dict.get(tense);

        /**
        *  if verb form not found, show invalid verb found else 
        *  go and search for words
        */
        if(tense.equals("Invalid")) {
            System.out.println("Invalid verb found");
        } else if(sorting_sentence != null && sorting_sentence.equals("Invalid")) {
            System.out.println("Invalid sentence");
        } else {
            if(tense.equals("past")) {
                for (String word : words){
                    if(past_verb_dict.get(word) != null) {
                        verb = past_verb_dict.get(word);
                    }
                }
            }

            if(tense.equals("present")) {
                for (String word : words){
                    if(present_verb_dict.get(word) != null) {
                        verb = present_verb_dict.get(word);
                    }
                }
            }

            if(tense.equals("future")) {
                for (String word : words){
                    if(future_verb_dict.get(word) != null) {
                        verb = future_verb_dict.get(word);
                    }
                }
            }

            // check for errors and either present invalid sentence or correct output
            if(newSentence == null || verb == null || sorting_sentence == null){
                System.out.println("Invalid sentence");
            }else{
                System.out.println(newSentence + " " + verb + " " + sorting_sentence);
            }
        }
    }

    /** 
    *  Finds tense of sentence by defining the verb's tense
    *  @returns tense form as word or gives back invalid if
    *           verb not found
    */
    public static String find_tense(String str){
        String noBrackets = str.replaceAll("\\(.*\\) ", "");
        String[] words = noBrackets.split(" ");
    
        for (String word : words){
            if(past_verb_dict.get(word) != null) {
                return "past";
            }
            if(present_verb_dict.get(word) != null) {
                return "present";
            }
            if(future_verb_dict.get(word) != null) {
                return "future";
            }
        }
        return "invalid";
    }

    /** 
    *  Finds subjects of a sentence (eg speaker/listener)
    *  @returns Maori translation of subbject
    */
    public static String sorting_sentence(String sentence){
        String[] words = sentence.split(" ");
        String temp = "";
        String bracketContent = "";

        // short and easy to get the content of brackets separated
        if(sentence.contains("(") && sentence.contains(")")) {
            bracketContent = "(" + sentence.substring(sentence.indexOf("(") + 1, sentence.indexOf(")")) + ")";
        }

        if(bracketContent != "") {
            if(!(bracketContent.equals("(1 excl)") || bracketContent.equals("(1 incl)") || 
                bracketContent.equals("(2 incl)") || bracketContent.equals("(3 incl)") || 
                bracketContent.equals("(2 excl)") || bracketContent.equals("(3 excl)"))){
                bracketContent = (bracketContent.contains("excl")) ? "(3 excl)" : "(3 incl)";
            }

            if((words[0].equals("we") || 
                words[0].equals("us")) && bracketContent.equals("(2 incl)")) {
                temp = incl_dict.get("(2 incl)");
            }
            //incl 3
            else if((words[0].equals("we") || 
                    words[0].equals("us")) && bracketContent.equals("(3 incl)")) {
                temp = incl_dict.get("(3 incl)");
            }

            //excl 2
            else if(words[0].equals("we") && bracketContent.equals("(2 excl)")) {
                temp = excl_list_dict.get("(2 excl)");
            }

            else if(words[0].equals("us") && bracketContent.equals("(2 incl)")) {
                temp = excl_speak_dict.get("(2 excl)");
            }

            //excl 3
            else if(words[0].equals("we") && bracketContent.equals("(3 excl)")) {
                temp = excl_list_dict.get("(3 excl)");
            }

            //excl speaker
            else if(words[0].equals("you two") && bracketContent.equals("(2 excl)")) {
                temp = excl_list_dict.get("(2 excl)");
            }

            else if(words[0].equals("you") && bracketContent.equals("(2 incl)")) {
                temp = excl_speak_dict.get("(2 excl)");
            }


            else if(words[0].equals("you") && bracketContent.equals("(3 incl)")) {
                temp = excl_speak_dict.get("(3 excl)");
            }


            else if(words[0].equals("you") && bracketContent.equals("(2 excl)")) {
                temp = excl_speak_dict.get("(2 excl)");                
            }

            else if(words[0].equals("you") && bracketContent.equals("(3 excl)")) {
                temp = excl_speak_dict.get("(3 excl)");
            }

            //neither speaker or listener
            else if((words[0].equals("they") || 
                    words[0].equals("them")) && bracketContent.equals("(2 excl)")) {
                temp = no_speak_dict.get("(2 excl)");
            }

            else if((words[0].equals("they") || 
                    words[0].equals("them")) && bracketContent.equals("(3 excl)")){
                temp = no_speak_dict.get("(3 excl)");
            }  
        
            // singular, change from else if & single request to one else.
            else {
                temp = singular_dict.get(words[0]);
            }
        }
        return temp; 
    }

    /** Adds all the translation words into the dictionaries */
    public static void dictionary(){
        tense_dict.put("past", "I");
        tense_dict.put("present", "Kei te");
        tense_dict.put("future", "Ka");

        past_verb_dict.put("asked", "p\u0101tai");
        past_verb_dict.put("called", "karanga");
        past_verb_dict.put("made", "hanga");
        past_verb_dict.put("learnt", "ako");
        past_verb_dict.put("read", "p\u0101nui");
        past_verb_dict.put("saw", "kite");
        past_verb_dict.put("went", "haere");
        past_verb_dict.put("wanted", "hiahia");

        present_verb_dict.put("asking", "p\u0101tai");
        present_verb_dict.put("calling", "karanga");
        present_verb_dict.put("going", "haere");
        present_verb_dict.put("making", "hanga");
        present_verb_dict.put("learning", "ako");
        present_verb_dict.put("reading", "p\u0101nui");
        present_verb_dict.put("seeing", "kite");
        present_verb_dict.put("wanting", "hiahia");

        future_verb_dict.put("ask", "p\u0101tai");
        future_verb_dict.put("call", "karanga");
        future_verb_dict.put("go", "haere");
        future_verb_dict.put("make", "hanga");
        future_verb_dict.put("learn", "ako");
        future_verb_dict.put("read", "p\u0101nui");
        future_verb_dict.put("see", "kite");
        future_verb_dict.put("want", "hiahia");
        future_verb_dict.put("will", "karekau");

        excl_list_dict.put("(2 excl)", "m\u0101ua");
        excl_list_dict.put("(3 excl)", "m\u0101tou");

        excl_speak_dict.put("(2 excl)", "k\u014Drua");
        excl_speak_dict.put("(3 excl)", "koutou");

        incl_dict.put("(2 incl)", "t\u0101ua");
        incl_dict.put("(3 incl)", "t\u0101tou");

        no_speak_dict.put("(2 excl)", "r\u0101ua");
        no_speak_dict.put("(3 excl)", "r\u0101tou");

        singular_dict.put("i", "au");
        singular_dict.put("you", "koe");
        singular_dict.put("he", "ia");
        singular_dict.put("she", "ia");
        singular_dict.put("him", "ia");
        singular_dict.put("her", "ia");
    }
}