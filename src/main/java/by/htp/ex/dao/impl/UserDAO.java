package by.htp.ex.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.ex.bean.Users;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;

public class UserDAO implements IUserDAO	{

	@Override
	public boolean logination(String login, String password) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registration(Users user) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getRole(String login, String password) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
