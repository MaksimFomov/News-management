package by.htp.ex.controller.listeners;

import by.htp.ex.dao.connectionPool.ConnectionPoolException;
import by.htp.ex.dao.connectionPool.ConnectionPoolProvider;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionPoolProvider.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException(e); //TODO handle
        }
        
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPoolProvider.getInstance().dispose();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
