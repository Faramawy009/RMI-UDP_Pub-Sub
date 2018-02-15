package edu.umn.FaraHany.ServerSide;

public class JoinThread implements Runnable {
    private String ip;
    private int port;

    public JoinThread(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        SubscribersDB.join(this.ip, this.port);
    }
}
