package by.htp.ex.dao.impl;

import by.htp.ex.bean.Users;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.connectionPool.DBParameter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {	
	@Override
	public boolean logination(Users user) throws DaoException {	
		if(findUser(user) != null) {
			if(user.getPassword().equals(findUser(user).getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	public String getRole(Users user) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		int id = 0;
		String role = "guest";
		
		try {
			connection = DriverManager.getConnection(DBParameter.DB_URL, DBParameter.DB_USER, DBParameter.DB_PASSWORD);
			preparedStatement = connection.prepareStatement("select * from users where login = ? and password = ?");
	        preparedStatement.setString(1, user.getLogin());
	        preparedStatement.setString(2, user.getPassword());
	            
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            id = rs.getInt("roles_id");
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
		
		try {
			preparedStatement = connection.prepareStatement("select * from roles where id = ?");
	        preparedStatement.setInt(1, id);
	            
	        rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	        	role = rs.getString("title");
	        	if(role == null) {
	        		role = "guest";
	        	}
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
		
		return role;
	}

	@Override
	public boolean registration(Users user) throws DaoException  {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		if(findUser(user) == null) {
			try {
				connection = DriverManager.getConnection(DBParameter.DB_URL, DBParameter.DB_USER, DBParameter.DB_PASSWORD);
				preparedStatement = connection.prepareStatement("insert into Users(login, password, roles_id) VALUES (?, ?, 2)");
		        preparedStatement.setString(1, user.getLogin());
		        preparedStatement.setString(2, user.getPassword());
		           
		        preparedStatement.executeUpdate();
		        return true;
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
		}
		return false;
	}
	
	@Override
	public Users findUser(Users user) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(DBParameter.DB_URL, DBParameter.DB_USER, DBParameter.DB_PASSWORD);
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
