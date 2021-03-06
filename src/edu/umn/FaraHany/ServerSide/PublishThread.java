package edu.umn.FaraHany.ServerSide;

public class PublishThread implements Runnable{
    private String ip;
    private int port;
    private String article;

    public PublishThread(String ip, int port, String article) {
        this.ip = ip;
        this.port = port;
        this.article = article;
    }

    @Override
    public void run() {
        SubscribersDB.publishOrSubscribe(this.ip, this.port, this.article, SubscribersDB.IS_PUBLISHER);
    }
}
