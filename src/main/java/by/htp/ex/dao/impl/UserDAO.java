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
	@Override
	public boolean logination(Users user) throws DaoException {	
		ResultSet resultSet = null;
		
		String password = "";
		
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?")) {
			statement.setString(1, user.getLogin());
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString("password");
				
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
		
		String role = "guest";
		
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM users JOIN roles ON users.roles_id = roles.id WHERE users.login = ?")) {
			statement.setString(1, user.getLogin());
			
	        resultSet = statement.executeQuery();
	        if (resultSet.next()) {
				role = resultSet.getString("title");
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
		Connection connection = null;
		PreparedStatement statement = null;
		
		if(!findUserByLogin(user.getLogin())) {
			try {
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
				
				connection = ConnectionPoolProvider.getInstance().takeConnection();
				statement = connection.prepareStatement("INSERT INTO Users(login, password, roles_id) VALUES (?, ?, 2)");
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
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");) {
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
