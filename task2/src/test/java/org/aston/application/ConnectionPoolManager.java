package org.aston.application;

import org.aston.application.config.ConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ConnectionPoolManager {

    Logger log = LoggerFactory.getLogger(ConnectionPoolManager.class);

    @BeforeClass
    static void setUpConnectionPool() {
        ConnectionPool.get();
        log.info("set up connection pool...");
    }

    @AfterClass
    static void destroyConnectionPool() {
        ConnectionPool.destroy();
        log.info("destroy connection pool...");
    }

}
