package by.htp.ex.dao.connectionPool;

import java.util.ResourceBundle;

public class DBResourceManager {
	private final static DBResourceManager instance = new DBResourceManager(); 
	
	private ResourceBundle bundle = ResourceBundle.getBundle("_java._se._07._connectionpool.db"); 
	
	public static DBResourceManager getInstance() {
		return instance;
	}

	public String getValue(String key) { 
		return bundle.getString(key);
	}
}
