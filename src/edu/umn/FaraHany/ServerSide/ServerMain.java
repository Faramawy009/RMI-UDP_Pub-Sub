package edu.umn.FaraHany.ServerSide;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.Scanner;

import static java.lang.System.exit;

public class ServerMain {
    public static String SERVER_IP;
    public static final int RMI_PORT = 6666;
    public static final int SERVER_PORT = 7777;
    public static final int BEAT_PORT = 8888;
    public static final String BINDING_NAME = "pubSub";
    public static final String PROTOCOL = "RMI";

    private void runServer() {
        try {
            // create registry on port 1399
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            registry.rebind(BINDING_NAME, new CommunicateImpl());
//            Naming.rebind("pubSub", new CommunicateImpl());
            System.out.println("Server is connected and ready for operation.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void run() {
//        //receive udp
//        //print articles
//    }

    public static void main (String [] args)
    {
        if(args.length != 1) {
            System.out.println("Usage: java ServerMain Ip_Address");
            exit(1);
        }
        SERVER_IP = args[0];

        Scanner sc = new Scanner(System.in);

        Thread registryServerThread = new Thread(new Runnable() {
            UDPRegisteryServer udpRegisteryServer = new UDPRegisteryServer();

            @Override
            public void run() {
                while (true) {
                    System.out.print("\n\n please select an option: ");
                    System.out.print("\n\n (r)egister, (d)eregister, (g)etlist: ");
                    char option = sc.next().charAt(0);
                    switch(option) {

                        case('r'): case('R'):
                            udpRegisteryServer.Register();
                            break;
                        case('d'): case('D'):
                            udpRegisteryServer.Deregister();
                            break;
                        case('g'): case('G'):
                            udpRegisteryServer.GetList();
                            break;
                        default:
                            System.out.println("\nInvalid Option!!");
                    }
                }
            }
        });
        registryServerThread.start();
//        Thread HeartbeatThread = new Thread(new Runnable() {
//            HeartbeatListener hb = new HeartbeatListener();
//
//            @Override
//            public void run() {
//                System.out.print("\n\n Heartbeat is on...");
//                while (true) {
//                    hb.receiveMsg();
//                    hb.sendMsg();
//                }
//            }
//        });
//        HeartbeatThread.start();
        Thread t = new Thread(new HeartbeatEcho());
        t.start();
        ServerMain sm = new ServerMain();
        sm.runServer();
//
//        UDPSender udps = new UDPSender("127.0.0.1",3542);
//        udps.sendMsg("Tez Tarek Kbera");
//        udps.close();
    }
}
