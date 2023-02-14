package by.htp.ex.dao.connection_pool;

public class ConnectionPoolProvider {
    private final static ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }
}
