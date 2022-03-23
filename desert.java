public class desert {
    float can = 20;
    static float distancePerLitre = 12;
    static float counter = 0;
    static float endDistance = 2413 * 2;
    static float newTank = 140;
    static float startDistance = newTank * distancePerLitre;

    public static void main(String[]args){
        float distanceTravelled = 0;
        
        //car holds 60 litres
        //4 cans hold 80 litres
        //1 can is 20 litres    

        check(distanceTravelled);
    }

    public static void check(float distanceTravelled){
        if(distanceTravelled >= endDistance){
            System.out.println("Fuel used to cross the desert was " + distanceTravelled/distancePerLitre + " litres");
            return;
        }


        distanceTravelled += startDistance;
        startDistance = startDistance/2;
        System.out.println(distanceTravelled);
        System.out.println(startDistance);

        System.out.println("Fuel used so far " + distanceTravelled/distancePerLitre + " litres");
        counter++;
        check(distanceTravelled);
        return;
    }
}
