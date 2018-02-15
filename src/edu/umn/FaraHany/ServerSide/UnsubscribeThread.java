package edu.umn.FaraHany.ServerSide;

public class UnsubscribeThread implements Runnable{
    private String ip;
    private int port;
    private String article;

    public UnsubscribeThread(String ip, int port, String article) {
        this.ip = ip;
        this.port = port;
        this.article = article;
    }

    @Override
    public void run() {
        SubscribersDB.unsubscribe(this.ip, this.port, this.article);
    }
}
