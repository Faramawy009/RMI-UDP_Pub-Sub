package edu.umn.FaraHany.ServerSide;

/**
 * Created by elfar009 on 2/15/18.
 */
        import java.io.IOException;
        import java.net.*;
        import java.util.*;

public class HeartbeatEcho implements Runnable
{
    @Override
    public void run()
    {
        System.out.println("HeartBeat ONNN!!");
        DatagramSocket dsock = null;
        try {
            dsock = new DatagramSocket(ServerMain.SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte arr1[] = new byte[150];
        DatagramPacket dpack = new DatagramPacket(arr1, arr1.length );

        while(true)
        {
            try {
                dsock.receive(dpack);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte arr2[] = dpack.getData();
            int packSize = dpack.getLength();
            String s2 = new String(arr2, 0, packSize);

            System.out.println( "  " + dpack.getAddress( ) + " : " + dpack.getPort( ) + "-->"+ s2);
            try {
                dsock.send(dpack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}