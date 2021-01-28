package top.aoae.jftp.bean;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private int port;
    private List<User> users = new ArrayList<>();

    public Config(int port, List<User> users) {
        this.port = port;
        this.users = users;
    }

    public Config() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Config{" +
                "port=" + port +
                ", users=" + users +
                '}';
    }
}
