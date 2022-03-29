import java.util.*;

public class SyllableSlam{
    private static String count = 0;
    public static void main(String[]args){
        syllableCounter(); // start the counter
    }

    public static void syllableCounter(){
        Scanner input = new Scanner(System.in);
        while(input.hasNextLine()){
            Scanner word = new Scanner(input.nextLine());
            while(word.hasNext()){
                word = word.next();
                process(word);   
            }
            System.out.println(count);
            count = 0;
        }
        if(count == 0){ count = 1; }
    }

    public static void process(String word) {
        for(int i=0; i<word.length(); i++){
            if(isVowel(word.charAt(word.length()-1)) && i == word.length()-1){ count++; }
            else if(isVowel(word.charAt(i)) && !isVowel(word.charAt(i+1)) ){ count++; }
        }

        if( word.length() > 1 && word.charAt(word.length()-2) == 'e' && word.charAt(word.length()-1) == 'd'){
            if(word.charAt(word.length()-3) == 'e') { continue; }
            else  if(word.charAt(word.length()-3) != 'd' || 
                     word.charAt(word.length()-3) != 't' ) { count--; }
        }

        if(word.length() > 1 && word.charAt(word.length()-1) == 'e'){ count--; }
        if(word.length() > 1 && word.charAt(word.length()-2) == 'l' && word.charAt(word.length()-1) == 'e'){ count++; }
        if(word.length() >= 4 && word.substring(word.length()-4, word.length()).equals("ing")){ count++; }
    }

    public static boolean isVowel(char c){
        if(c.toUpperCase() == 'A') {return true;}
        if(c.toUpperCase() == 'E') {return true;}
        if(c.toUpperCase() == 'I') {return true;}
        if(c.toUpperCase() == 'O') {return true;}
        if(c.toUpperCase() == 'U') {return true;}
        if(c.toUpperCase() == 'Y') {return true;} // "Y is mostly a vowel"       
        return false;
    }
}
