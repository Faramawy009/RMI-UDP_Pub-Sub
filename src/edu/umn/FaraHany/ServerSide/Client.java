package edu.umn.FaraHany.ServerSide;

import java.util.Objects;

public class Client {
    private String ip;
    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return port == client.port &&
                Objects.equals(ip, client.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
