package org.aston.application.config;

import org.aston.application.exception.DbConnectionException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    public static final int SIZE = 10;
    private static final BlockingQueue<Object> proxies = new ArrayBlockingQueue<>(SIZE);
    private static final List<Connection> realConnections = new ArrayList<>(SIZE);
    private static final ConnectionManager connection = new ConnectionManager();

    private static void init() {
        for (int i = 0; i < SIZE; i++) {
            Connection realConnection = connection.get();
            realConnections.add(realConnection);
            proxies.add(getNewProxyInstance(realConnection));
        }
    }

    public static Connection get() {
        if (realConnections.isEmpty()) {
            init();
        }
        try {
            return (Connection) proxies.take();
        } catch (InterruptedException e) {
            throw new DbConnectionException("Method proxies.take() was interrupted while waiting. ", e);
        }
    }

    public static void destroy() {
        try {
            for (Connection realConnection : realConnections) {
                if (!realConnection.getAutoCommit()) {
                    realConnection.rollback();
                }
                realConnection.close();
            }
        } catch (SQLException e) {
            throw new DbConnectionException("A database access error occurs. ", e);
        }
    }

    private static Object getNewProxyInstance(Connection realConnection) {
        return Proxy.newProxyInstance(
                ConnectionPool.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> "close".equals(method.getName())
                        ? Boolean.valueOf(proxies.add(proxy))
                        : method.invoke(realConnection, args)
        );
    }


}
