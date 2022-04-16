public class desert {
    static int x = 0;
    static int c = 1680;
    static int d = 4826;
    static int frac = 2;
    static int count = 26;
    static int tank = 0;

    public static void main(String[]args){
        while(x < d){
            trip();
        }
    }

    public static void trip(){
        if(c / frac > d){
            d -= d;
        }else{
            d -= c / frac;
        }
        count--;
        System.out.println("Step: " + count);
        System.out.println("Distance of stop: " + d);
        System.out.println("Current denomenator: " + frac);
        frac += 1;
        System.out.println("");
    }
}
