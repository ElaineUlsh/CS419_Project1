/**
 * The simulated CPU
 */
 
public class CPU {
    //Simulate running the specified task for the specified slice of time.
    public static void run(Process process, int duration) {
        if(duration <= 0){
            System.out.println("Invalid execution duration " + duration + " for process " + process.getName());
            System.exit(1);
        }
        System.out.println("CPU runs " + process + " for duration "+duration);
    }

}
