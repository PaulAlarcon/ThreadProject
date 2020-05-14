import java.util.List;
import java.util.Vector;

public class FlightAttendant extends Thread {

    public String name;
    public static long time = System.currentTimeMillis();
    final String threadName = "FlightAttendant";
    volatile boolean isWaiting;

    FlightAttendant(int id){
        setName(threadName + "-" + id);
        msg("has started their shift.");
        isWaiting = true;
        start();
    }

    @Override
    public void run() {
        while (isWaiting);
        while(Main.status == Main.Status.BOARDING)
            if (!Main.waiting.isEmpty()) {
                Passenger p = Main.waiting.remove(0);
                p.yield();
                p.yield();
                board(p);
            }
        msg("Closing Doors");
        try {  // flight
            msg("Flight Time");
            Thread.sleep(10000);  // not random
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msg("Attention passengers we are about to reach Wonderland, please Wake Up and make sure to fast your seat bealts");
        Main.wakeUpPassengers();
//        isWaiting = true;
//        while(isWaiting); //waits for all passenger to wake up
        try {
            Main.askPassengertoDisembark();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            msg("Cleaning the Airplane");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        msg("Done for the day");
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+ getName() +": "+m);
    }

    public void board(Passenger passenger){
        passenger.isWaiting = false;
    }

}
