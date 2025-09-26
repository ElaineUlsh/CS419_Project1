import java.util.*;

public class SRTF extends Algorithm{

    private final Queue<Process> priorityQueue = new PriorityQueue<>(new CustomProcessComparatorSRTF());

    private final Queue<Process> processesToArrive;

    private int now = 0;

    public SRTF(List<Process> allProcessesList){
        super(allProcessesList);
        processesToArrive = new LinkedList<>(allProcessesList);
    }

    @Override
    public void schedule(){
        System.out.println("Shortest Remaining Time First: ");

        while (!priorityQueue.isEmpty() || !processesToArrive.isEmpty()) {
            if (priorityQueue.isEmpty()) {
                Process process = processesToArrive.remove();
                if (now <= process.getArrivalTime()) {
                    now = process.getArrivalTime();
                }
                priorityQueue.add(process);
            }

            Process currentProcess = priorityQueue.remove();
            int runTime = currentProcess.getRemainingTime();
            int processingTime = runTime;
            int remainingTime = 0;
            boolean willFinish = true;

            if (priorityQueue.isEmpty()) {
                if (!processesToArrive.isEmpty()) {
                    Iterator<Process> iterator = processesToArrive.iterator();
                    while (iterator.hasNext()) {
                        Process process = iterator.next();
                        int arrivalTime = process.getArrivalTime();
                        int finishingTime = now + runTime;

                        if (arrivalTime < finishingTime && process.getBurstTime() < runTime - arrivalTime) {
                            processingTime = arrivalTime - now;
                            remainingTime = runTime - processingTime;
                            willFinish = false;
                            break;
                        }
                    }
                }
            } else { // could have processes that have already processed a bit
                Process nextProcess = priorityQueue.peek();
                int nextArrivalTime = nextProcess.getArrivalTime();

                if ((nextArrivalTime < now + runTime || nextProcess.getBurstTime() != nextProcess.getRemainingTime())
                        && nextProcess.getRemainingTime() < runTime) {
                    processingTime = nextArrivalTime - now;
                    remainingTime = runTime - processingTime;
                    willFinish = false;
                    System.out.println(nextArrivalTime);
                }
            }

            System.out.print("At time " + now + ": ");
            CPU.run(currentProcess, processingTime);

            now += processingTime;

            currentProcess.setRemainingTime(remainingTime);
            if (willFinish) {
                currentProcess.setFinishTime(now);
            } else {
                priorityQueue.add(currentProcess);
            }

            while(!processesToArrive.isEmpty() && processesToArrive.peek().getArrivalTime() <= now) {
                priorityQueue.add(processesToArrive.remove());
            }
        }
    }

}

class CustomProcessComparatorSRTF implements Comparator<Process> {
    public int compare(Process p1, Process p2) {
        return p1.getRemainingTime() < p2.getRemainingTime() ? -1 : 1;
    }
}