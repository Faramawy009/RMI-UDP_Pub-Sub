package edu.umn.FaraHany.ServerSide;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class HeartbeatListener {

    private static final int BUFF_SIZE = 120;
    private int target_port;
    private DatagramSocket socket;
    private InetAddress my_address;
    private InetAddress target_address;
    private byte [] buff;

    public HeartbeatListener() {
        buff= new byte[BUFF_SIZE];
        try {
            this.socket = new DatagramSocket();
            my_address = InetAddress.getByName(ServerMain.SERVER_IP);
            target_address = InetAddress.getByName(UDPRegisteryServer.REGISTRY_SERVER_IP);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void receiveMsg() {
        buff = new byte[BUFF_SIZE];

        DatagramPacket packet = new DatagramPacket(buff, BUFF_SIZE, my_address, ServerMain.SERVER_PORT);
        try {
            socket.receive(packet);
        } catch (Exception e) {
            e.printStackTrace();

        }
        target_port = packet.getPort();
        System.out.print("zebyyyyyyyy");
    }

    public void sendMsg() {
        DatagramPacket packet = new DatagramPacket(buff, BUFF_SIZE, target_address, target_port);
        try {
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }
}