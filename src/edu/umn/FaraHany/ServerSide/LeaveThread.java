package edu.umn.FaraHany.ServerSide;

public class LeaveThread implements Runnable{
    private String ip;
    private int port;

    public LeaveThread(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        SubscribersDB.leave(this.ip, this.port);
    }
}
