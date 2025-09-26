
import java.util.*;

public class SJF extends Algorithm {

    private final Queue<Process> priorityQueue = new PriorityQueue<>(new CustomProcessComparatorSJF());

    private final Queue<Process> processesToArrive;

    private int now = 0;

    public SJF(List<Process> allProcessList){
        super(allProcessList);
        processesToArrive = new LinkedList<>(allProcessList);
    }

    @Override
    public void schedule() {
        System.out.println("Shortest Job First: ");

        while (!priorityQueue.isEmpty() || !processesToArrive.isEmpty()) {
            if (priorityQueue.isEmpty()) {
                Process process = processesToArrive.remove();
                if (now <= process.getArrivalTime()) {
                    now = process.getArrivalTime();
                }
                priorityQueue.add(process);
            }

            Process currentProcess = priorityQueue.remove();
            int runTime = currentProcess.getBurstTime();

            System.out.print("At time " + now + ": ");
            CPU.run(currentProcess, runTime);

            now += runTime;

            currentProcess.setRemainingTime(0);
            currentProcess.setFinishTime(now);

            while(!processesToArrive.isEmpty() && processesToArrive.peek().getArrivalTime() <= now) {
                priorityQueue.add(processesToArrive.remove());
            }
        }
    }
}

class CustomProcessComparatorSJF implements Comparator<Process> {
    public int compare(Process p1, Process p2) {
        return p1.getBurstTime() < p2.getBurstTime() ? -1 : 1;
    }
}
