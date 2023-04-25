package models;

import java.util.Comparator;

public class ArrivalTimeComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        return task1.getArrivalTime() - task2.getArrivalTime();
    }
}
