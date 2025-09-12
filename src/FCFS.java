/**
 * This is the complete implementation of the FCFS (First-Come First-Served)
 * scheduling algorithm.
 *
 * It is provided as an example; use it to guild your own implementations of
 * other algorithms.
 *
 * Do not modify this file.
 */
 
import java.util.*;

public class FCFS extends Algorithm {

    // The ready queue
    private final Queue<Process> readyQueue = new LinkedList<>();

    // Processes that have not yet arrived
    private final Queue<Process> processesToArrive;

    // The simulation clock
    private int now = 0;

    public FCFS(List<Process> allProcessList) {
        super(allProcessList);
        processesToArrive = new LinkedList<>(allProcessList);
    }

    @Override
    public void schedule() {
        System.out.println("First-Come, First-Served:");

        while (!readyQueue.isEmpty() || !processesToArrive.isEmpty()) {
            if (readyQueue.isEmpty()) {
                Process process = processesToArrive.remove();
                if (now < process.getArrivalTime()) {
                    //advance the simulation clock to the next process's arrival time
                    now = process.getArrivalTime();
                }
                readyQueue.add(process);
            }

            // With FCFS scheduling, the next process to schedule is the one
            // at the head of the ready queue
            Process currentProcess = readyQueue.remove();

            // With FCFS there is no preemption, so a thread can always run
            // to completion.
            int runTime = currentProcess.getBurstTime();

            // Execute the selected process
            System.out.print("At time " + now + ": ");
            CPU.run(currentProcess, runTime);

            // Advance the simulation clock
            now += runTime;

            // Mark this process as completed
            currentProcess.setRemainingTime(0);
            currentProcess.setFinishTime(now);

            // If any processes have arrived by 'now', add them to ready queue
            while(!processesToArrive.isEmpty() &&
                    processesToArrive.peek().getArrivalTime()<=now){
                readyQueue.add(processesToArrive.remove());
            }
        }

    }
}
