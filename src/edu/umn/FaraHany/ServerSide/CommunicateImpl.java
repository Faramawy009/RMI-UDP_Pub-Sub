package edu.umn.FaraHany.ServerSide;

import edu.umn.FaraHany.Communicate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CommunicateImpl extends UnicastRemoteObject implements edu.umn.FaraHany.Communicate{

    public CommunicateImpl() throws RemoteException { }
//    Thread joinHandler = new Thread(new Runnable() {
//        @Override
//        public void run() {
//
//        }
//    });

    Thread leaveHandler = new Thread(new Runnable() {
        @Override
        public void run() {

        }
    });
    @Override
    public boolean JoinServer(String IP, int Port) throws RemoteException {
        return false;
    }

    @Override
    public boolean LeaveServer(String IP, int Port) throws RemoteException {
        return false;
    }

    @Override
    public boolean Join(String IP, int Port) throws RemoteException {
//        return SubscribersDB.join(IP, Port);
        Thread t = new Thread(new JoinThread(IP,Port));
        t.run();
        return true;
    }

    @Override
    public boolean Leave(String IP, int Port) throws RemoteException {
        Thread t = new Thread(new LeaveThread(IP, Port));
        t.run();
        return true;
    }

    @Override
    public boolean Subscribe(String IP, int Port, String Article) throws RemoteException {
        //return SubscribersDB.publishOrSubscribe(IP, Port, Article, SubscribersDB.IS_SUBSCRIBER);
        Thread t = new Thread(new SubscribeThread(IP,Port, Article));
        t.run();
        return true;
    }

    @Override
    public boolean Unsubscribe(String IP, int Port, String Article) throws RemoteException {
        //return SubscribersDB.unsubscribe(IP, Port, Article);
        Thread t = new Thread(new UnsubscribeThread(IP,Port, Article));
        t.run();
        return true;
    }

    @Override
    public boolean Publish(String Article, String IP, int Port) throws RemoteException {
        //return SubscribersDB.publishOrSubscribe(IP, Port, Article, SubscribersDB.IS_PUBLISHER);
        Thread t = new Thread(new PublishThread(IP,Port, Article));
        t.run();
        return true;
    }

    @Override
    public boolean PublishServer(String Article, String IP, int Port) throws RemoteException {
        return false;
    }

    @Override
    public boolean Ping() throws RemoteException {
        return true;
    }
}
