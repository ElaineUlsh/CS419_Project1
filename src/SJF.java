
import java.util.*;

/**
 * TODO: Implement the non-preemptive SJF (Shortest-Job First) scheduling algorithm.
 *       Non-preemptive SJF: check the lengths of all the jobs in the ready queue
 *          and lets the one with the shortest path process first.
 */

public class SJF extends Algorithm {

    private final Queue<Process> readyQueue = new LinkedList<>();

    private final Queue<Process> processesToArrive;

    public SJF(List<Process> allProcessList){
        super(allProcessList);
        processesToArrive = new LinkedList<>(allProcessList);
    }

    @Override
    public void schedule() {
    }
}
