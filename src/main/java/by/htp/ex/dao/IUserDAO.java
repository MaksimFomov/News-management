package by.htp.ex.dao;

import by.htp.ex.bean.Users;

public interface IUserDAO {
	boolean logination(Users user) throws DaoException;
	boolean registration(Users user) throws DaoException;
	boolean findUserByLogin(String login) throws DaoException;
	String getRole(Users user) throws DaoException;
}
