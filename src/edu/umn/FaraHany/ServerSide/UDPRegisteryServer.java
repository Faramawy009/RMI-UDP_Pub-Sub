package edu.umn.FaraHany.ServerSide;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

public class UDPRegisteryServer {
    public static final String REGISTRY_SERVER_IP = "128.101.37.120";
    public static final int REGISTRY_SERVER_PORT = 5104;
    private static final int BUFF_SIZE = 1024;
    private DatagramSocket sendSocket;
    private DatagramSocket receiveSocket;
    private DatagramSocket getListSocket;
    private InetAddress address;
    private InetAddress myAddress;
    private byte [] sendBuff;
    private byte [] receiveBuff;

    private static final String REGISTER_MSG
            =   "Register" + ";"
            +   ServerMain.PROTOCOL + ";"
            +   ServerMain.SERVER_IP + ";"
            +   ServerMain.SERVER_PORT + ";"
            +   ServerMain.BINDING_NAME + ";"
            +   ServerMain.RMI_PORT;

    private static final String DEREGISTER_MSG
            =   "Deregister" + ";"
            +   ServerMain.PROTOCOL + ";"
            +   ServerMain.SERVER_IP + ";"
            +   ServerMain.SERVER_PORT;

    private static final String GETLIST_MSG
            =   "GetList" + ";"
            +   ServerMain.PROTOCOL + ";"
            +   ServerMain.SERVER_IP + ";"
            +   ServerMain.SERVER_PORT;

    public UDPRegisteryServer() {
        sendBuff = new byte[BUFF_SIZE];
        receiveBuff = new byte[BUFF_SIZE];
        try {
            sendSocket = new DatagramSocket();
          //  receiveSocket = new DatagramSocket(ServerMain.SERVER_PORT);
          //  getListSocket = new DatagramSocket(ServerMain)
            address = InetAddress.getByName(REGISTRY_SERVER_IP);
            myAddress = InetAddress.getByName("localhost");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Register() {
        sendBuff = REGISTER_MSG.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuff, sendBuff.length, address, REGISTRY_SERVER_PORT);
        try {
            sendSocket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendBuff = new byte[BUFF_SIZE];
    }

    public void Deregister() {
        sendBuff = DEREGISTER_MSG.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuff, sendBuff.length, address, REGISTRY_SERVER_PORT);
        try {
            sendSocket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendBuff = new byte[BUFF_SIZE];

    }

    public void GetList() {
        sendBuff = GETLIST_MSG.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuff, sendBuff.length, address, REGISTRY_SERVER_PORT);
        try {
            sendSocket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendBuff = new byte[BUFF_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuff, receiveBuff.length, myAddress, ServerMain.SERVER_PORT);
        try {
            sendSocket.receive(receivePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String msg = new String (receivePacket.getData(), 0, receivePacket.getLength());
        String[] elements = msg.split(";");
        for (int i = 0; i < elements.length-1; i += 2) {
            System.out.println("\n" + elements[i] + ":" + elements[i+1]);
        }
    }
}
