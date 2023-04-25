package models;

public class Task {
    private int ID;
    private int arrivalTime;
    private int processingTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.processingTime = serviceTime;
    }
    public int getID() {
        return ID;
    }
    public int getProcessingTime() {
        return processingTime;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
}
