import java.util.List;

/**
 * Abstract base class for all CPU scheduling algorithms.
 *
 * Every scheduling algorithm should extend this class and implement the
 * schedule() method.
 *
 * (Do not modify this class.)
 */

public abstract class Algorithm {

    // The list of all processes in the test case
    // This list is used to calculate the waiting times at the end of the simulation,
    // and it should never be changed.
    protected final List<Process> allProcesses;

    public Algorithm(List<Process> allProcesses){
        this.allProcesses = allProcesses;
    }


    public void calculateWaitingTime(){
        int totalWait = 0;
        for(Process p : allProcesses){
            int wait = p.getFinishTime() - p.getArrivalTime() - p.getBurstTime();
            totalWait += wait;
            System.out.println(p.getName()+"'s waiting time is " + wait);
        }
        double averageWaitTime = totalWait / (double) allProcesses.size();
        System.out.println("Average wait time is " + averageWaitTime);
    }

    /**
     * Each algorithm must implement this abstract method to perform scheduling.
     * In your implementation, the finishTime should be set for each process
     */
   public abstract void schedule();
}
