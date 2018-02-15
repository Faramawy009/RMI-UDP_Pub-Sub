package edu.umn.FaraHany.ClientSide;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import edu.umn.FaraHany.Communicate;

import static java.lang.System.exit;
import static java.lang.System.out;

public class ClientMain {
//
//    private void runTest(){
//        try {
//            // fire to localhost port 1099
//            Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1", 1399);
//
//            // search for myMessage service
//             Communicate comu = (Communicate) myRegistry.lookup("pubSub");
//
//            // call server's method
//            if(comu.Ping()) {
//                System.out.println("Sever Online!");
//            } else {
//                System.out.println("Sever Offline!");
//            }
//
//            System.out.println("Message Sent");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws RemoteException{
//        ClientMain main = new ClientMain();
//        main.runTest();
        System.out.print("Enter your ip: ");
        Scanner sc = new Scanner(System.in);
        String ip = sc.nextLine();
        System.out.print("\nEnter your port: ");
        int port = sc.nextInt();
        Thread t1 = new Thread(new Runnable() {
            UDPReceiver udpr = new UDPReceiver(ip,port);
            @Override
            public void run() {
                while(true) {
                    String receivedMsg = udpr.receiveMsg();
                    System.out.println(receivedMsg);
                }
            }

            @Override
            protected void finalize() throws Throwable {
                try
                {
                    udpr.close();
                } finally {
                    super.finalize();
                }
            }
        });
        t1.start();

        Registry myRegistry = null;
        Communicate comu = null;
        try {
            // fire to localhost port 1099
            myRegistry = LocateRegistry.getRegistry("localhost", 6666);

            // search for myMessage service
            comu = (Communicate) myRegistry.lookup("pubSub");

//            // call server's method
//            if(comu.Ping()) {
//                System.out.println("Sever Online!");
//            } else {
//                System.out.println("Sever Offline!");
//            }
//
//            System.out.println("Message Sent");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("\n Registering...");
        comu.Join(ip, port);
        //start ping thread
        Thread pingThread = new Thread (new ClientPingThread(comu));
        pingThread.start();
        while (true) {
            System.out.print("\n please select an option: ");
            System.out.print("\n (s)ubscribe, (u)nsubscribe, (p)ublish, (l)eave: ");
            char option = sc.next().charAt(0);
            if (option  == 'l') {
                try{
                    comu.Leave(ip, port);
                    exit(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("\n please enter article in the following format");
            System.out.print("\n type;originator;org;contents: ");
            sc.nextLine();
            String article = sc.nextLine();
            switch(option) {

                case('s'): case('S'):
                    comu.Subscribe(ip, port,article);
                    break;
                case('u'): case('U'):
                    comu.Unsubscribe(ip, port,article);
                    break;
                case('p'): case('P'):
                    String [] input = article.split(";");
                    if(input.length >= 4)
                        comu.Publish(article,ip, port);
                    else
                        System.out.print("\n Please enter a content if you want to publish");
                    break;
                default:
                    System.out.println("\nInvalid Option!!");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
//
//    @Override
//    public void run() {
//        //receive udp
//        //print articles
//    }
}
