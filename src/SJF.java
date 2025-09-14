
import java.util.*;

/**
 * TODO: Implement the non-preemptive SJF (Shortest-Job First) scheduling algorithm.
 *       Non-preemptive SJF: check the lengths of all the jobs in the ready queue
 *          and lets the one with the shortest path process first.
 */

public class SJF extends Algorithm {

    private final Queue<Process> priorityQueue = new PriorityQueue<>(new CustomProcessComparator());

    private final Queue<Process> processesToArrive;

    private int now = 0;

    public SJF(List<Process> allProcessList){
        super(allProcessList);
        processesToArrive = new LinkedList<>(allProcessList);
    }

    // TODO: The issue currently is that it is returning negative waiting times, which makes
    //      no sense whatsoever.

    @Override
    public void schedule() {
        System.out.println("Shortest Job First: ");

        while (priorityQueue.isEmpty() || !processesToArrive.isEmpty()) {
            Process process = processesToArrive.remove();
            if (now <= process.getArrivalTime()) {
                now = process.getArrivalTime();
            }
            priorityQueue.add(process);
        }

        Process currentProcess = priorityQueue.remove();
        int runTime = currentProcess.getBurstTime();

        System.out.println("At time " + now + ": ");
        CPU.run(currentProcess, runTime);

        now += runTime;

        currentProcess.setRemainingTime(0);
        currentProcess.setFinishTime(now);

        while(!processesToArrive.isEmpty() && processesToArrive.peek().getArrivalTime() <= now) {
            priorityQueue.add(processesToArrive.remove());
        }
    }
}

class CustomProcessComparator implements Comparator<Process> {
    public int compare(Process p1, Process p2) {
        return p1.getBurstTime() < p2.getBurstTime() ? -1 : 1;
    }
}
