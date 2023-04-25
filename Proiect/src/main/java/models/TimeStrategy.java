package models;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        //find minumum waiting time in servers list
        AtomicInteger minTime = servers.get(0).getWaitingPeriod();
        int index = 0, minIndex = 0;
        for (Server s : servers) {
            if (s.getWaitingPeriod().get() < minTime.get()) {
                minIndex = index;
                minTime = s.getWaitingPeriod();
            }
            index ++;
            //put the task to the server with the minimum waiting time
        }
        servers.get(minIndex).addTask(t);
    }
}


