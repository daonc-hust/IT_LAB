package com.topica.daonc.jdbc.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPool implements Runnable {

    private String driver, url, username, password;
    private int maxConnections;
    private boolean waitIfBusy;
    private boolean connectionPending = false;
    public Vector<Connection> availableConnections, busyConnections;

    public ConnectionPool() {
    }

    public ConnectionPool(String driver, String url, String username, String password, int initialConnections, int maxConnections, boolean waitIfBusy) throws SQLException, ClassNotFoundException {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.maxConnections = maxConnections;
        this.waitIfBusy = waitIfBusy;

        if (initialConnections > maxConnections) {
            initialConnections = maxConnections;
        }

        availableConnections = new Vector<>(initialConnections);
        busyConnections = new Vector<>();

        for (int i = 0; i < initialConnections; i++) {
            availableConnections.addElement(makeNewConnection());
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (!availableConnections.isEmpty()) {
            Connection currentConnection = availableConnections.lastElement();

            int lastIndex = availableConnections.size() - 1;
            availableConnections.remove(lastIndex);
            if (currentConnection.isClosed()) {
                notifyAll();
                return getConnection();
            } else {
                busyConnections.addElement(currentConnection);
                return currentConnection;
            }
        } else {
            if (totalConnections() < maxConnections && !connectionPending) {
                makeBackgroundConnection();
            } else if (!waitIfBusy) {
                throw new SQLException("Connection limit reached");
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getConnection();
        }
    }

    private Connection makeNewConnection() throws SQLException, ClassNotFoundException {

        try {
            Class.forName(driver);

            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Exception: " + e.getMessage());
        }
    }

    private void makeBackgroundConnection() {
        connectionPending = true;
        Thread connectThread = new Thread(this);
        connectThread.start();
    }

    public synchronized void free(Connection connection) {
        busyConnections.removeElement(connection);
        availableConnections.addElement(connection);
        notifyAll();
    }

    public synchronized int totalConnections() {
        return (availableConnections.size() + busyConnections.size());
    }

    @Override
    public void run() {
        try {
            Connection connection = makeNewConnection();
            synchronized (this) {
                availableConnections.addElement(connection);
                connectionPending = false;
                notifyAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized String display() {
        String info = "ConnectionPool(" + url + "," + username + ")"
                + ", available=" + availableConnections.size() + ", busy="
                + busyConnections.size() + ", max=" + maxConnections;
        return info;
    }
}
