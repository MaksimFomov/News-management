package by.htp.ex.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.ex.bean.Users;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {
	private static final String databaseURL = "jdbc:mysql://127.0.0.1/news-management?&useSSL=false";
	private final String sqlUser = "root";
	private final String sqlPassword = "toortoor";
	
	private static Connection connection = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	public static final String ROLE_GUEST = "guest";

	List<Users> userStorage = new ArrayList<>();
	{
		userStorage.add(new Users("MaksimFomov", "1234"));
	}
	
	List<Users> adminStorage = new ArrayList<>();
	{
		adminStorage.add(new Users("admin", "admin"));
	}

	@Override
	public boolean logination(String login, String password) throws DaoException {	
		boolean status = false;
		
		try {
			connection = DriverManager.getConnection(databaseURL, sqlUser, sqlPassword);
			preparedStatement = connection.prepareStatement("select * from users where login = ? and password = ?");
	        preparedStatement.setString(1, login);
	        preparedStatement.setString(2, password);
	           
	        rs = preparedStatement.executeQuery();
            status = rs.next();        
		} catch (SQLException e) {
			printSQLException(e);
	    } finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;
	}
	
	public String getRole(String login, String password) throws DaoException {
		int id = 0;
		String role = "guest";
		
		try {
			connection = DriverManager.getConnection(databaseURL, sqlUser, sqlPassword);
			preparedStatement = connection.prepareStatement("select * from users where login = ? and password = ?");
	        preparedStatement.setString(1, login);
	        preparedStatement.setString(2, password);
	            
	        ResultSet rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            id = rs.getInt(4);
            }
	        
	        preparedStatement = connection.prepareStatement("select * from roles where id = ?");
	        preparedStatement.setInt(1, id);
	            
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	        	role = rs.getString(2);
	        }
		} catch (SQLException e) {
			printSQLException(e);
	    } finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(role == null) {
			return "guest";
		}
		
		return role;
	}

	@Override
	public boolean registration(Users user) throws DaoException  {
		if(userStorage.stream().filter(o -> o.getLogin().equals(user.getLogin())).findAny().orElse(null) != null) {
			return false;
		} 
		else {
			userStorage.add(user);
			
			return true;
		}
	}
	
	private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
