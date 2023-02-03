package by.htp.ex.dao;

import by.htp.ex.bean.Users;

public interface IUserDAO {
	boolean logination(String login, String password) throws DaoException;
	boolean registration(Users user) throws DaoException;
	String getRole(String login, String password) throws DaoException;
}
