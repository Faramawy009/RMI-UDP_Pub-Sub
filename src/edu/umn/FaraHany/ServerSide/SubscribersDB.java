package edu.umn.FaraHany.ServerSide;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SubscribersDB {
    static private final int TYPE_INDEX = 0;
    static private final int ORIGNIATOR_INDEX = 1;
    static private final int ORGANIZATION_INDEX = 2;
    static private final int CONTENT_INDEX = 3;
    static public final boolean IS_SUBSCRIBER = true;
    static public final boolean IS_PUBLISHER = false;
    static private final int FIELDS_NUMBER = 3;

    static private Hashtable<String,Set<Client>> [] Fields;
    static private Hashtable<Client, Client> AllClients;

    static
    {
        Fields = new Hashtable[3] ;
        Fields[TYPE_INDEX] = new Hashtable<String, Set<Client>>();
        Fields[ORIGNIATOR_INDEX] = new Hashtable<String, Set<Client>>();
        Fields[ORGANIZATION_INDEX] = new Hashtable<String, Set<Client>>();
        AllClients = new Hashtable<>();
    }


    public static boolean join(String ip, int port) {
        return AllClients.put(new Client(ip,port), new Client(ip,port)) != null;
    }

    /*
    This method works for both subscribing and publishing depending on the last boolean flag
     */

    public static boolean publishOrSubscribe(String ip, int port, String article, boolean pubOrSub) {
        Client sender = new Client(ip, port);
        if (!AllClients.contains(sender)) {
            return false;
        }

        String[] msgFields = article.split(";", -1);
        //in case of incorrect format
        if (msgFields.length != 4)
            return false;

        String content = msgFields[CONTENT_INDEX];


        for (int fieldIndex = 0; fieldIndex < FIELDS_NUMBER; fieldIndex++) {
            if (!msgFields[fieldIndex].equals("")) {
                if (!Fields[fieldIndex].containsKey(msgFields[fieldIndex])) {
                    Fields[fieldIndex].put(msgFields[fieldIndex], Collections.synchronizedSet(new HashSet<>()));
                }
                if (pubOrSub == IS_SUBSCRIBER) {
                    Fields[fieldIndex].get(msgFields[fieldIndex]).add(sender);
                } else{ //if (pubOrSub == IS_PUBLISHER)
                    sendToSubscribers(article, msgFields[fieldIndex], fieldIndex);
                }
            }
        }
        return true;
    }


    private static void sendToSubscribers(String content, String category, int fieldIndex) {
        Set<Client> targetSubscribers = null;
        switch(fieldIndex){
            case TYPE_INDEX:
                targetSubscribers = Fields[TYPE_INDEX].get(category);
                break;
            case ORIGNIATOR_INDEX:
                targetSubscribers = Fields[ORIGNIATOR_INDEX].get(category);
                break;
            case ORGANIZATION_INDEX:
                targetSubscribers = Fields[ORGANIZATION_INDEX].get(category);
                break;
            default:
        }
        //UDPSender udps = null;
        for(Client c: targetSubscribers)
        {
            Thread sender = new Thread(new UDPSenderThread(c.getIp(),c.getPort(),content));
            sender.start();
        }
    }

    public static boolean unsubscribe(String ip, int port, String article) {
        Client sender = new Client(ip, port);
        if (!AllClients.contains(sender)) {
            return false;
        }

        String[] msgFields = article.split(";", -1);
        // invalid input
        if (msgFields.length < 4)
            return false;

        for (int fieldIndex = 0; fieldIndex < FIELDS_NUMBER; fieldIndex++) {
            if (!msgFields[fieldIndex].equals("")) {
                if (Fields[fieldIndex].containsKey(msgFields[fieldIndex])) {
                    Fields[fieldIndex].get(msgFields[fieldIndex]).remove(sender);
                }
            }
        }
        return true;
    }

    public static boolean leave(String ip, int port)
    {
        Client sender = new Client(ip, port);
        if (!AllClients.contains(sender)) {
            return false;
        }
        AllClients.remove(sender);

        for(Hashtable<String,Set<Client>> field: Fields) {
            for(String s: field.keySet()) {
                Set<Client> cur = field.get(s);
                cur.remove(sender);
            }
        }
        return true;
    }
}
