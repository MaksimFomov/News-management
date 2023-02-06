package by.htp.ex.dao.connectionPool;

public final class DBParameter {
	private DBParameter(){}
	
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1/news-management?&useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "toortoor";
    public static final String DB_POLL_SIZE = "5";
}
