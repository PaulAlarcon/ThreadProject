public class Clock extends Thread {

    public static long time = System.currentTimeMillis();


    Clock(int id){
        setName("Clock-"+id);
        start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(12000);  //
        }
        catch (Exception e){
            System.out.println(e);
        }
        // announce
        msg("~~~ It is time for boarding ~~~ ");
        Main.startBoarding();

        try {
            Thread.sleep(12000);  //
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // announce
        msg("~~~ It is time for takeoff ~~~");
        Main.endBoarding();
        msg("Done");
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+ getName() +": "+m);
    }

}
