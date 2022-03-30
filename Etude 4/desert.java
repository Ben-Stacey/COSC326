import java.util.ArrayList;

public class desert {
    float can = 20;
    static float distancePerLitre = 12;
    static float trips = 0;
    static float endDistance = 2413 * 2;
    static float newTank = 140;
    static ArrayList<Float> list = new ArrayList<Float>();
    static float startDistance = newTank * distancePerLitre;

    public static void main(String[]args){
        float distanceTravelled = 0;
        check(distanceTravelled);
    }

    public static void check(float distanceTravelled){
        if(distanceTravelled >= endDistance){
            System.out.println("Fuel used to cross the desert was " + distanceTravelled/distancePerLitre + " litres");
            return;
        }

        distanceTravelled = ;
        list.add((trips - 1)/trips);
        newTank -= distanceTravelled/distancePerLitre;
        newTank += 1/(2*trips);

        distanceTravelled += startDistance;
        startDistance = startDistance/2;

        System.out.println("Fuel used so far " + distanceTravelled/distancePerLitre + " litres");
        trips++;
        check(distanceTravelled);
        return;
    }
}
