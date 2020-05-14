import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    static volatile Vector<Passenger> q1, q2;
    static int numPassengers = 30 ;
    static int groupNum = 4;
    static int counterNum = 3;
    public static volatile long time;  // make public getter
    static volatile Vector<Passenger> waiting, plane;
    static KioskClerk clerk1, clerk2;
    static FlightAttendant flightAttendant;

    static Clock clock;

    public static void boardPlane(Passenger passenger) {
        plane.add(passenger);
    }

    public static void wakeUpPassengers() {
        for (Passenger passenger : plane) {
            passenger.interrupt();
        }
        flightAttendant.isWaiting = false;
    }

    public enum Status {ARRIVAL, BOARDING, FLIGHT, LANDING};
    public volatile static Status status;

    public static void main(String args[]) {
        time = System.currentTimeMillis();
        q1 = new Vector<>();
        q2 = new Vector<>();
        waiting = new Vector<>();
        status = Status.ARRIVAL;
        plane = new Vector<>();

        clerk1 = new KioskClerk(1, q1);
        clerk2 = new  KioskClerk(2, q2);
        clock = new Clock(1);

        for(int i = 0; i < numPassengers; i++) new Passenger(i + 1);

        flightAttendant = new FlightAttendant(1);
    }

    public static boolean getInLine(Passenger p) {
        if (q1.size() == counterNum && q2.size() == counterNum) //check for max number in each line
            return false;
        if (q1.size() <= q2.size()) q1.add(p);
        else  q2.add(p);
        return true;
    }

    public static void startBoarding() {
        status = Status.BOARDING;
        flightAttendant.isWaiting = false;
    }

    public static void endBoarding() {
        status = Status.FLIGHT;
//        System.out.println("Plane size: " + plane.size());
//        System.out.println("Waiting size: " + waiting.size());
//        System.out.println("Lost size: " + (numPassengers - plane.size() - waiting.size()));
//        plane.stream().map(p -> Integer.parseInt(p.getName().replace("Passenger-", ""))).sorted().forEach(System.out::println);
    }

    public static int zone(int seat){
        return seat < 11 ? 1 : seat < 21 ? 2 : 3;
    }

    public static void enterWaitingArea(Passenger passenger) {
        waiting.add(passenger);
    }

    public static void askPassengertoDisembark() throws InterruptedException {
        for (Passenger p : plane) {
            p.DisperseAndGoToDestination();
        }
    }


}
