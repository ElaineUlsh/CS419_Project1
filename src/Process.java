/**
 * This class represents a single process
 *
 * The name, arrivalTime, and burstTime fields are obtained from the test case file
 * and should not be changed.
 *
 * The finishTime field should be set by each scheduling algorithm
 *
 * The remainingTime field is only relevant to preemptive algorithms.
 *
 */

public class Process
{
    private final String name;

    private final int arrivalTime;

    //CPU burst length
    private final int burstTime;

    //Needed for preemptive algorithms;
    //tracks the remaining CPU burst time if a process has been preempted.
    private int remainingTime;

    // Initialized to -1 to indicate "not finished yet"
    // Must be set by the scheduling algorithms
    private int finishTime = -1;


    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }


    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() { return remainingTime;}

    public void setRemainingTime(int t) { remainingTime = t;}

    public int getFinishTime() { return finishTime;}

    public void setFinishTime(int t) { finishTime = t;}


     // Two process objects are considered the same if they have the same name.
    @Override
    public boolean equals(Object other) {
         //if the two are the same object, then they are equal
        if (other == this) return true;
        if (!(other instanceof Process)) return false;
        //If the two processes have the same name, then they are equal
        Process p = (Process) other;
        return (this.name.equals(p.name));
    }

    // Also override hashCode() to be consistent.
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                '}';
    }
}
