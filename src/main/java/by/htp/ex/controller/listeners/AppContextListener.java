package by.htp.ex.controller.listeners;

import by.htp.ex.dao.connection_pool.ConnectionPool;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
        	throw new RuntimeException("error during init of connection pool", e);
        }
        
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().dispose();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
