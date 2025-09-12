import java.util.*;
import java.io.*;

/**
 * Driver.java
 * 
 * Drives the simulation of CPU scheduling.
 * 
 * Usage:
 *  java Driver <algorithm> <test case file>
 * where algorithm = [FCFS, SJF, RR, SRTF] (not case-sensitive)
 *
 *  In intelliJ, specify those two arguments in "Run Configuration"
 *
 *  Two simplifying assumptions for test cases:
 *  - Processes are sorted by their arrival times.
 *  - No two processes arrive at the same time.
 *
 *  (Do not modify this file.)
 */

public class Driver {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java Driver <algorithm> <test_case>");
            System.exit(1);
        }

        // The list of all processes. This list should not be modified.
        // Algorithms should copy this list and not operate on it directly.
        final List<Process> allProcesses = new LinkedList<>();

        // Expected file format (one per line): name, arrivalTime, burstTime
        try(BufferedReader inFile = new BufferedReader(new FileReader(args[1]))) {
            String line;
            while ((line = inFile.readLine()) != null) {
                String[] params = line.split(",\\s*");
                allProcesses.add(new Process(
                        params[0],
                        Integer.parseInt(params[1]),
                        Integer.parseInt(params[2])
                ));
            }
        }catch (FileNotFoundException e) {
            System.out.println("Could not open test case file " + args[1]);
            System.exit(1);
        }
        
        Algorithm algo = null;
        String choice = args[0].toUpperCase();
        switch(choice) {
            case "FCFS":
                algo = new FCFS(allProcesses);
                break;
            case "SJF":
                algo = new SJF(allProcesses);
                break;
            case "RR":
                algo = new RR(allProcesses);
                break;
            case "SRTF":
                algo = new SRTF(allProcesses);
                break;
            default:
                System.err.println("Invalid algorithm "+ args[0]);
                System.exit(1);
        }

        // start the scheduler
        algo.schedule();
        // print the result
        algo.calculateWaitingTime();
    }
}
