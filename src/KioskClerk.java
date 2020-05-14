import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KioskClerk extends Thread {

    static volatile int numHelped = 0 ;
    static volatile List<Integer> seatNums;
    static{
        seatNums = IntStream.range(1, 31).boxed().collect(Collectors.toList());
        Collections.shuffle(seatNums);
    }

    public volatile Vector<Passenger> queue;
    public static long time = System.currentTimeMillis(); // get time from main instead in all classes
    final String threadName = "KioskClerk";

    KioskClerk(int id, Vector<Passenger> queue){
        setName(threadName+"-"+id);
        this.queue = queue;
        msg("Has started their shift");
        start();
    }

    @Override
    public void run() {
        while(Main.status != Main.status.FLIGHT ) {
            if (!queue.isEmpty()) {
                Passenger p = queue.remove(0);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (numHelped != Main.numPassengers) {
                    helpPassenger(p);
                }
            }
        }
        msg("Done for the day, ready to go home");
    }

    public void helpPassenger(Passenger p) {
        numHelped++;
        if (Main.status == Main.Status.FLIGHT)
            p.sendHome();
        else
            p.setSeat(seatNums.remove(0));
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }


}
