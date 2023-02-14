package by.htp.ex.dao.impl;

import by.htp.ex.bean.Users;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO implements IUserDAO {		
	private static final String SQL_QUERY_FOR_LOGINATION_AND_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
	private static final String SQL_QUERY_FOR_GET_ROLE = "SELECT * FROM users JOIN roles ON users.roles_id = roles.id WHERE users.login = ?";
	private static final String SQL_QUERY_FOR_REGISTRATION = "INSERT INTO Users(login, password, roles_id) VALUES (?, ?, 2)";
	
	private static final String DB_FIELD_PASSWORD = "password";
	private static final String DB_FIELD_TITLE = "title";
	
	private static final String ROLE_GUEST = "guest";
	
	@Override
	public boolean logination(Users user) throws DaoException {	
		ResultSet resultSet = null;
		String password = null;
		
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_LOGINATION_AND_FIND_USER_BY_LOGIN)) {
			statement.setString(1, user.getLogin());
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString(DB_FIELD_PASSWORD);
				
				return BCrypt.checkpw(user.getPassword(), password);
			} 
		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
		
		return false;
	}
	
	public String getRole(Users user) throws DaoException {
		ResultSet resultSet = null;
		String role = ROLE_GUEST;
		
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_GET_ROLE)) {
			statement.setString(1, user.getLogin());
			
	        resultSet = statement.executeQuery();
	        if (resultSet.next()) {
				role = resultSet.getString(DB_FIELD_TITLE);
			} 
		} catch (SQLException e) {
			throw new DaoException(e);
	    }  catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
		
		return role;
	}

	@Override
	public boolean registration(Users user) throws DaoException  {		
		if(!findUserByLogin(user.getLogin())) {
			try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
					PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_REGISTRATION)) {
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
				
				statement.setString(1, user.getLogin());
				statement.setString(2, user.getPassword());
				statement.executeUpdate();
		        
		        return true;
			} catch (SQLException e) {
				throw new DaoException(e);
			}  catch (ConnectionPoolException e) {
				throw new DaoException(e);
			} 
		}
		
		return false;
	}
	
	@Override
	public boolean findUserByLogin(String login) throws DaoException {
		ResultSet resultSet = null;
		
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_LOGINATION_AND_FIND_USER_BY_LOGIN)) {
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			
			return resultSet.next(); 
		} catch (SQLException e) {
			throw new DaoException(e);
		}  catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}
}
