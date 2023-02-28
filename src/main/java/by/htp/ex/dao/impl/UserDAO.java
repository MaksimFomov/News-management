package by.htp.ex.dao.impl;

import by.htp.ex.bean.User;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.connection_pool.ConnectionPool;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO implements IUserDAO {		
	private static final ReentrantLock LOCK = new ReentrantLock();
	
	private static final String SQL_QUERY_FOR_LOGINATION_AND_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
	private static final String SQL_QUERY_FOR_GET_ROLE = "SELECT * FROM users JOIN roles ON users.roles_id = roles.id WHERE users.login = ?";
	private static final String SQL_QUERY_FOR_REGISTRATION = "INSERT INTO User(login, password, roles_id) VALUES (?, ?, 2)";
	
	private static final String DB_FIELD_PASSWORD = "password";
	private static final String DB_FIELD_TITLE = "title";
	
	private static final String ROLE_GUEST = "guest";
	
	@Override
	public boolean logination(User user) throws DaoException {	
		ResultSet resultSet = null;
		String password = null;
		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_LOGINATION_AND_FIND_USER_BY_LOGIN)) {
			statement.setString(1, user.getLogin());
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString(DB_FIELD_PASSWORD);
				
				return BCrypt.checkpw(user.getPassword(), password);
			} 
		} catch (SQLException e) {
			throw new DaoException("sql error", e);
		} catch (ConnectionPoolException e) {
			throw new DaoException("error trying to take connection", e);
		}
		
		return false;
	}
	
	public String getRole(User user) throws DaoException {
		ResultSet resultSet = null;
		String role = ROLE_GUEST;
		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_GET_ROLE)) {
			statement.setString(1, user.getLogin());
			
	        resultSet = statement.executeQuery();
	        if (resultSet.next()) {
				role = resultSet.getString(DB_FIELD_TITLE);
			} 
		} catch (SQLException e) {
			throw new DaoException("sql error", e);
	    }  catch (ConnectionPoolException e) {
			throw new DaoException("error trying to take connection", e);
		}
		
		return role;
	}

	@Override
	public boolean registration(User user) throws DaoException  {		
		if(!findUserByLogin(user.getLogin())) {
			LOCK.lock();
			
			try (Connection connection = ConnectionPool.getInstance().takeConnection();
					PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_REGISTRATION)) {
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
				
				statement.setString(1, user.getLogin());
				statement.setString(2, user.getPassword());
				statement.executeUpdate();
		        
		        return true;
			} catch (SQLException e) {
				throw new DaoException("sql error", e);
			}  catch (ConnectionPoolException e) {
				throw new DaoException("error trying to take connection", e);
			} finally {
				LOCK.unlock();
			}
		}
		
		return false;
	}
	
	@Override
	public boolean findUserByLogin(String login) throws DaoException {
		ResultSet resultSet = null;
		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_LOGINATION_AND_FIND_USER_BY_LOGIN)) {
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			
			return resultSet.next(); 
		} catch (SQLException e) {
			throw new DaoException("sql error", e);
		}  catch (ConnectionPoolException e) {
			throw new DaoException("error trying to take connection", e);
		}
	}
}
