import java.util.*;

public class RR extends Algorithm {

    // Ready queue for processes
    private final Queue<Process> readyQueue = new LinkedList<>();

    // Processes that have not yet arrived
    private final Queue<Process> processesToArrive;

    // Simulation clock
    private int now = 0;

    // Time quantum (adjust manually for schedule1.txt or schedule2.txt)
    private final int timeQuantum;

    public RR(List<Process> allProcesses) {
        super(allProcesses);
        processesToArrive = new LinkedList<>(allProcesses);
        this.timeQuantum = 5;  // default, use 10 when testing with schedule2.txt
    }

    @Override
    public void schedule() {
        System.out.println("Round Robin (quantum=" + timeQuantum + "):");

        // Initialize remaining times
        for (Process p : processesToArrive) {
            p.setRemainingTime(p.getBurstTime());
        }

        while (!readyQueue.isEmpty() || !processesToArrive.isEmpty()) {
            // If ready queue empty, jump forward in time to next process arrival
            if (readyQueue.isEmpty()) {
                Process process = processesToArrive.remove();
                if (now < process.getArrivalTime()) {
                    now = process.getArrivalTime();
                }
                readyQueue.add(process);
            }

            Process currentProcess = readyQueue.remove();

            int remaining = currentProcess.getRemainingTime();
            int runTime = Math.min(timeQuantum, remaining);

            // Execute for either quantum or remaining time
            System.out.print("At time " + now + ": ");
            CPU.run(currentProcess, runTime);

            now += runTime;
            currentProcess.setRemainingTime(remaining - runTime);

            // Add any newly arrived processes to ready queue
            while (!processesToArrive.isEmpty() &&
                    processesToArrive.peek().getArrivalTime() <= now) {
                readyQueue.add(processesToArrive.remove());
            }

            // If process still has remaining time, put it back in queue
            if (currentProcess.getRemainingTime() > 0) {
                readyQueue.add(currentProcess);
            } else {
                // Process completed
                currentProcess.setFinishTime(now);
            }
        }
    }
}
