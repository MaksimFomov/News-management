package by.htp.ex.dao;

import by.htp.ex.bean.User;

public interface IUserDAO {
	boolean logination(User user) throws DaoException;
	boolean registration(User user) throws DaoException;
	boolean findUserByLogin(String login) throws DaoException;
	String getRole(User user) throws DaoException;
}
