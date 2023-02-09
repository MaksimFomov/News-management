package by.htp.ex.dao.connectionPool;

public class ConnectionPoolProvider {
    private final static ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }
}
