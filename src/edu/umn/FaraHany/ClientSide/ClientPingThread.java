package edu.umn.FaraHany.ClientSide;

import edu.umn.FaraHany.Communicate;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class ClientPingThread implements  Runnable {
    private Communicate commu;

    public ClientPingThread(Communicate commu) {
        this.commu = commu;
    }
    @Override
    public void run() {
        while (true) {
            try {
                commu.Ping();
            } catch (RemoteException e) {
                System.out.println("\n\n\nServer Disconnected...terminating...");
                exit(1);
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
