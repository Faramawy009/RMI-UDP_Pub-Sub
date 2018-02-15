package edu.umn.FaraHany.ServerSide;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSenderThread implements Runnable {

    private static final int BUFF_SIZE = 120;
    private String ip;
    private int port;
    private DatagramSocket socket;
    private InetAddress address;
    private byte [] buff;
    private String msg;

    public UDPSenderThread(String ip, int port, String msg) {
        this.ip = ip;
        this.port = port;
        this.msg = msg;
        buff = new byte[BUFF_SIZE];
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMsg() {
        buff = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buff, buff.length, address, port);
        try {
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }

    @Override
    public void run() {
        sendMsg();
    }
}
