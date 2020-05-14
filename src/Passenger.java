public class Passenger extends Thread {

    public volatile boolean isWaiting;
    public static long time = System.currentTimeMillis();
    final String threadName = "Passenger";
    public int seatNumber;

    Passenger(int id){
        setName(threadName +"-"+id);
        seatNumber = -1;
        start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 12000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msg("Has entered the Airport");
        while(!Main.getInLine(this)); //Those not able to stay on the line are asked to busy wait
        msg("Has entered the line.");
        isWaiting = true;
        while(isWaiting);       // wait for boarding pass
        rushToTheSecurityLine();
        isWaiting = true;
        while(isWaiting);
        Main.boardPlane(this);
        msg("boards the plane");
        goToSleep();
        //isWaiting = true;
        //while(isWaiting);
//        try {
//            DisperseAndGoToDestination();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void msg(String m) {
        System.out.println("[" + (System.currentTimeMillis()-time) + "] " + getName() + ": " + m);
    }

    public void goToSleep() {
        try{
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            msg(" has been woken up!");
        }
    }

    public void sendHome(){
        msg("Arrived late and was sent home");
    }

    public void setSeat(int num){
        seatNumber = num;
        msg( "was assigned seat " + seatNumber + " zone " + Main.zone(num) );
        isWaiting = false;
    }

    public void rushToTheSecurityLine() {
        msg("Rushing to the security line.");
        int prevPriority = getPriority();
        setPriority(prevPriority); //increasing priority
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        Main.enterWaitingArea(this);
        msg("has arrived at the Gate.");
        setPriority(prevPriority);
    }

    public void DisperseAndGoToDestination() throws InterruptedException {

        msg("gets off the plane and goes home.");

        if(this.isAlive()) {
            this.join();
        }
    }
}
