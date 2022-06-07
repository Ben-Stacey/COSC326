/**
* Ben Stacey - 2157359
* Harry Pusey - 
*
* @param args
*/
public class CountingUp {
    public static void main(String[]args){
        String[] input = ("0,0,3,1,4,4,5,2,10,0,10,10,10,4,9,7,70,8,295,100," +
                "34,88,-2,7,9,-1,90,0,90,1,90,2,90,3,90,8,90,24,4337,6").split(",");
        for (int i = 0; i < input.length; i += 2) {
            int n = Integer.valueOf(input[i]);
            int r = Integer.valueOf(input[i + 1]);
            System.out.printf("Combination (%d,%d) = ", n, r);
            try {
                System.out.println(checkBit(n, r));
            } catch (Exception e) {
                System.out.println("Arguments are not acceptable");
            }
        }
    }

    /** */
    public static long checkBit(int n, int r) {
        long[][] temp = new long[n + 1][n + 1];
        if (n < 0 || r > n) {
            throw new IllegalArgumentException("Arguements are not acceptable");
        }
        return countUp(n, r, temp);
    }

    /** */
    private static long countUp(int n, int r, long[][] temp) {
        if (r == 0 || r == n) {
            return temp[n][r] = 1;
        } else if (temp[n][r] != 0) {
            return temp[n][r];
        } else {
            temp[n][r] = countUp(n-1, r-1, temp) + countUp(n-1, r, temp);
            if (temp[n][r] < 0) {
                throw new RuntimeException("Integers are bigger than 64 bits");
            }
            return temp[n][r];
        }
    }
}