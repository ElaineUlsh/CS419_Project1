import java.util.*;

public class RR extends Algorithm {

    private final Queue<Process> readyQueue = new LinkedList<>();
    private final Queue<Process> processesToArrive;
    private int now = 0;
    private final int timeQuantum;

    public RR(List<Process> allProcesses) {
        super(allProcesses);
        this.processesToArrive = new LinkedList<>(allProcesses);
        this.timeQuantum = 5; // manually change to 10 for schedule2.txt
    }

    @Override
    public void schedule() {
        System.out.println("Round Robin (quantum=" + timeQuantum + "):");

        // Initialize remaining time for all processes
        for (Process p : allProcesses) {
            p.setRemainingTime(p.getBurstTime());
        }

        while (!readyQueue.isEmpty() || !processesToArrive.isEmpty()) {

            // If ready queue is empty, fast-forward to next arrival
            if (readyQueue.isEmpty()) {
                Process next = processesToArrive.remove();
                now = Math.max(now, next.getArrivalTime());
                readyQueue.add(next);
            }

            Process current = readyQueue.remove();

            int runTime = Math.min(timeQuantum, current.getRemainingTime());

            System.out.print("At time " + now + ": ");
            CPU.run(current, runTime);

            now += runTime;
            current.setRemainingTime(current.getRemainingTime() - runTime);

            while (!processesToArrive.isEmpty() &&
                    processesToArrive.peek().getArrivalTime() <= now) {
                readyQueue.add(processesToArrive.remove());
            }

            if (current.getRemainingTime() > 0) {
                readyQueue.add(current);
            } else {
                current.setFinishTime(now);
            }
        }
    }
}
