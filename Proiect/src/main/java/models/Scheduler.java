package models;

import ui.SimulationFrame;

import java.util.ArrayList;
import java.util.List;
public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<>();

        for (int i = 0; i < maxNoServers; i++) {
            Server newServer = new Server();
            servers.add(newServer);
            Thread serverThread = new Thread(newServer);
            serverThread.start();
        }
    }
    public void changeStrategy(SelectionPolicy policy) {
        if ( policy == SelectionPolicy.SHORTEST_QUEUE )
            strategy = new ShortestQueueStrategy();
        if ( policy == SelectionPolicy.SHORTEST_TIME )
            strategy = new TimeStrategy();
    }

    public void dispatchTask(Task t) {
        changeStrategy(SimulationFrame.getStrategy());
        strategy.addTask(servers, t);
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }
    public int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }
    public List<Server> getServers() {
        return servers;
    }


}
