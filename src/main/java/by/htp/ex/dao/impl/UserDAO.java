package by.htp.ex.dao.impl;

import by.htp.ex.bean.Users;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.connectionPool.ConnectionPoolException;
import by.htp.ex.dao.connectionPool.ConnectionPoolProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO implements IUserDAO {		
	@Override
	public boolean logination(Users user) throws DaoException {	
		String password = "";
		
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?")) {
				statement.setString(1, user.getLogin());
				try(ResultSet rs = statement.executeQuery()) {
					while (rs.next()) {
			        	password = rs.getString("password");
			        }
					return BCrypt.checkpw(user.getPassword(), password);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}
	
	public String getRole(Users user) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int id = 0;
		String role = "guest";
		
		try {
			connection = ConnectionPoolProvider.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement("select * from users where login = ?");
	        preparedStatement.setString(1, user.getLogin());
	            
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            id = resultSet.getInt("roles_id");
            }
		} catch (SQLException e) {
			printSQLException(e);
	    }  catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
		
		try {
			preparedStatement = connection.prepareStatement("select * from roles where id = ?");
	        preparedStatement.setInt(1, id);
	            
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	role = resultSet.getString("title");
	        	if(role == null) {
	        		role = "guest";
	        	}
	        }
		} catch (SQLException e) {
			printSQLException(e);
	    } 
		
		return role;
	}

	@Override
	public boolean registration(Users user) throws DaoException  {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		if(findUser(user) == null) {
			try {
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
				
				connection = ConnectionPoolProvider.getInstance().takeConnection();
				preparedStatement = connection.prepareStatement("insert into Users(login, password, roles_id) VALUES (?, ?, 2)");
		        preparedStatement.setString(1, user.getLogin());
		        preparedStatement.setString(2, user.getPassword());
		           
		        preparedStatement.executeUpdate();
		        return true;
			} catch (SQLException e) {
				printSQLException(e);
			}  catch (ConnectionPoolException e) {
				throw new DaoException(e);
			} 
		}
		return false;
	}
	
	@Override
	public Users findUser(Users user) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionPoolProvider.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement("select * from users where login = ?");
	        preparedStatement.setString(1, user.getLogin());
	           
	        rs = preparedStatement.executeQuery();
	        if(rs.next() == false) {
	        	user = null;
	        }   
	        else {
		        while (rs.next()) {
		            user.setPassword(rs.getString("password"));
		            user.setRoles_id(rs.getInt("roles_id"));
		        }
	        }	        

		} catch (SQLException e) {
			printSQLException(e);
		}  catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}

		return user;
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
