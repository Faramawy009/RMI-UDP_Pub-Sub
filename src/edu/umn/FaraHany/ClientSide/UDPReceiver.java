package edu.umn.FaraHany.ClientSide;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPReceiver {

    private static final int BUFF_SIZE = 120;
    private String ip;
    private int port;
    private DatagramSocket socket;
    private InetAddress address;
    private byte [] buff;

    public UDPReceiver(String ip, int port) {
        this.ip = ip;
        this.port = port;
        buff= new byte[BUFF_SIZE];
        try {
            socket = new DatagramSocket(port);
            address = InetAddress.getByName(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String receiveMsg() {
        DatagramPacket packet = new DatagramPacket(buff, BUFF_SIZE, address, port);
        try {
            socket.receive(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String msg = new String (packet.getData(), 0, packet.getLength());
        return msg;

    }

    public void close() {
        socket.close();
    }
}
