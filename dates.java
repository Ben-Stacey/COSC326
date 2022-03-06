import java.util.Scanner;

public class dates{
    int key  = 0;
public static void main(String[]args){
    input();
}

public static String input(){
    Scanner scan = new Scanner(System.in);
    String sc = scan.nextLine();
    return sc;
}

public check(){
    int total = 0;
    String string = input();

    //Check Day
    if(){
        total++;
    }else if(){
        total++;
    }else if(){
        total++;
    }else{
        errorCheck();
    }
    //Check Month
    if(){
        total++;
    }else if(){
        total++;
    }else if(){
        total++;
    }else{
        errorCheck();
    }
    //Check Year
    if(){
        total++;
    }else if(){
        total++;
    }else{
        errorCheck();
    }
    //Check Separator
    if(){
        total++;
    }else if(){
        total++;
    }else if(){
        total++;
    }else{
        errorCheck();
    }

    //Checker
    if(total == 4){
        return string;
    }else{
        errorCheck();
    }
}

public errorCheck(){

}
}