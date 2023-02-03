package by.htp.ex.dao;

import java.io.Serial;

public class NewsDAOException extends Exception {
	@Serial
	private static final long serialVersionUID = 1L;

	public NewsDAOException(String message) {
		super(message);
	}
	
	public NewsDAOException(String message, Exception exception) {
		super(message, exception);
	}
}
