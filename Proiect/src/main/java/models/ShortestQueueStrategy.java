package models;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        //find the shortest queue to add the task
        int shortest = servers.get(0).getTasks().size(); //initialize shortest  to the size of the first queue
        int index = 0;
        int shortestIndex = 0;
        for ( Server s : servers ) {
            if (s.getTasks().size() < shortest) {
                shortest = s.getTasks().size();
                shortestIndex = index;
            }
            index++;
        }
        servers.get(shortestIndex).addTask(t);
    }
}
