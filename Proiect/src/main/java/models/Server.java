package models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int processingTime;

    public Server() {
        this.tasks = new ArrayBlockingQueue<>(1000);
        this.waitingPeriod = new AtomicInteger(0);
        this.processingTime = 0;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask); //add new task to the queue
        waitingPeriod.set(waitingPeriod.get() + newTask.getProcessingTime()); //update the waiting period
    }
    public void run() {
        while(!tasks.isEmpty()) {
            //take next task from queue
            int currentTaskProcessingTime = tasks.remove().getProcessingTime();
            System.out.println(currentTaskProcessingTime);
            try {
                Thread.sleep(currentTaskProcessingTime);
            } catch (InterruptedException e) { }
            waitingPeriod.set(waitingPeriod.get() - currentTaskProcessingTime); //update the waiting period
        }
    }
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public Task getFirst() {
        return tasks.peek();
    }

    public void removeFirst() {
        processingTime += tasks.remove().getProcessingTime();
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public String toString() {
        String s = "";
        for ( Task t : tasks ) {
           s += "Task " + t.getID() + " (" + t.getProcessingTime() +  "s) | " ;
        }
        return s;
    }
}
