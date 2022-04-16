import java.util.ArrayList;

public class Stops extends Crossing{
    protected ArrayList<Stops> stops;
    protected int number, distance, fuel;

    public void Stops(int number, int distance, int fuel){
        number = number;
        distance = distance;
        fuel = fuel;
    }

    public void addStops(){
        ArrayList<Stops> stops = new ArrayList<Stops>();
        Stops s1 = new Stops(1, 35, 0);
        Stops s2 = new Stops(2, 99, 0);
        Stops s3 = new Stops(3, 166, 0);
        Stops s4 = new Stops(4, 236, 0);
        Stops s5 = new Stops(5, 309, 0);
        Stops s6 = new Stops(6, 385, 0);
        Stops s7 = new Stops(7, 465, 0);
        Stops s8 = new Stops(8, 549, 0);
        Stops s9 = new Stops(9, 637, 0);
        Stops s10 = new Stops(10, 730, 0);
        Stops s11 = new Stops(11, 828, 0);
        Stops s12 = new Stops(12, 933, 0);
        Stops s13 = new Stops(13, 1045, 0);
        Stops s14 = new Stops(14, 1165, 0);
        Stops s15 = new Stops(15, 1294, 0);
        Stops s16 = new Stops(16, 1434, 0);
        Stops s17 = new Stops(17, 1586, 0);
        Stops s18 = new Stops(18, 1754, 0);
        Stops s19 = new Stops(19, 1940, 0);
        Stops s20 = new Stops(10, 2150, 0);
        Stops s21 = new Stops(21, 2390, 0);
        Stops s22 = new Stops(22, 2670, 0);
        Stops s23 = new Stops(23, 3006, 0);
        Stops s24 = new Stops(24 3426, 0);
        Stops s25 = new Stops(25, 3986, 0);

        stops.add(s1);
        stops.add(s2);
        stops.add(s3);
        stops.add(s4);
        stops.add(s5);
        stops.add(s6);
        stops.add(s7);
        stops.add(s8);
        stops.add(s9);
        stops.add(s10);
        stops.add(s11);
        stops.add(s12);
        stops.add(s13);
        stops.add(s14);
        stops.add(s15);
        stops.add(s16);
        stops.add(s17);
        stops.add(s18);
        stops.add(s19);
        stops.add(s20);
        stops.add(s21);
        stops.add(s22);
        stops.add(s23);
        stops.add(s24);
        stops.add(s25);
    }
}

