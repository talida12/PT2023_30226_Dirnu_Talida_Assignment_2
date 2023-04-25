package controller;

import models.*;
import ui.SimulationFrame;

import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private double averageProcessingTime;
    private double averageWaitingTime;
    private int totalWaitingTime;
    private int completedTasks;
    private int totalProcessingTime;
    private int peakHour;
    private int maximumNumberOfClients;
    public SimulationManager(int timeLimit, int minArrivalTime, int maxArrivalTime, int maxProcessingTime, int minProcessingTime,
                             int numberOfServers, int numberOfClients, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.selectionPolicy = selectionPolicy;
        generatedTasks = new ArrayList<>();
        this.totalProcessingTime = 0;
        this.totalWaitingTime = 0;
        this.averageProcessingTime = 0;
        this.completedTasks = 0;
        this.peakHour = 0;
        this.maximumNumberOfClients = 0;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        for (int i = 0; i < numberOfServers; i++) {
            Server server = new Server();
            Thread myThread = new Thread(server);
            myThread.start();
        }
        generateNRandomTasks();
    }

    private void generateNRandomTasks() {
        for (int i = 0; i < numberOfClients; i++) {
            Random random = new Random();
            int randomProcessingTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            int randomArrivalTime = random.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            Task newTask = new Task(i, randomArrivalTime, randomProcessingTime);
            generatedTasks.add(newTask);
        }
        Collections.sort(generatedTasks, new ArrivalTimeComparator());
    }

    @Override
    public void run() {
            int currentNumberOfClients = 0;
            int currentTime = 0;
            while (currentTime <= timeLimit) {
                if (!Thread.currentThread().isInterrupted()) {
                for ( Server s : scheduler.getServers()) {
                    while (s.getFirst() != null && Math.max(s.getProcessingTime(), s.getFirst().getArrivalTime())
                            + s.getFirst().getProcessingTime() <= currentTime) {
                        Task completedTask = s.getFirst();
                        totalProcessingTime += completedTask.getProcessingTime();
                        totalWaitingTime += currentTime - completedTask.getArrivalTime() - completedTask.getProcessingTime();
                        completedTasks++;
                        currentNumberOfClients--;
                        averageProcessingTime = totalProcessingTime * 1d / completedTasks;
                        averageWaitingTime = totalWaitingTime * 1d / completedTasks;
                        s.removeFirst();
                    }
                }
                String waitingTasks = "Waiting tasks: ";
                for ( Task t : generatedTasks )
                        waitingTasks += "Task " + t.getID() + "(" + t.getProcessingTime() + ") | ";
                Iterator<Task> iterator = generatedTasks.iterator();
                while (iterator.hasNext()) {
                    Task task = iterator.next();
                    if ( task.getArrivalTime() == currentTime ) {
                        scheduler.dispatchTask(task);
                        currentNumberOfClients++;
                        iterator.remove();
                    }
                }
                if ( currentNumberOfClients > maximumNumberOfClients ) {
                        peakHour = currentTime;
                        maximumNumberOfClients = currentNumberOfClients;
                }
                //update UI frame
                String queuesSimulation = "";
                queuesSimulation += waitingTasks + "\n\n";
                int i = 1;
                for ( Server s : scheduler.getServers() ) {
                    queuesSimulation +=  "QUEUE " + i + ": " + s.toString() + "\n\n";
                    i++;
                }
                SimulationFrame.getQueuesDisplay().setText(queuesSimulation);
                SimulationFrame.getTime().setText(String.valueOf(currentTime));
                SimulationFrame.getAvgWaitValue().setText(String.format("%.2f", averageWaitingTime));
                SimulationFrame.getAvgServiceValue().setText(String.format("%.2f", averageProcessingTime));
                SimulationFrame.getPeakValue().setText(String.valueOf(peakHour));
                currentTime++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    SimulationFrame.getAvgWaitValue().setText(String.format("%.2f", averageWaitingTime));
                    SimulationFrame.getAvgServiceValue().setText(String.format("%.2f", averageProcessingTime));
                    SimulationFrame.getPeakValue().setText(String.valueOf(peakHour));
                }
        } }
    }

    public double getAverageProcessingTime() {
        return averageProcessingTime;
    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager(100,  1, 12, 10, 2, 3, 100, SelectionPolicy.SHORTEST_TIME);
        Thread t = new Thread(gen);
        t.start();
    }
}
