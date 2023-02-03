package by.htp.ex.service;

import by.htp.ex.bean.Users;

public interface IUserService {
	String signIn(String login, String password) throws ServiceException;
	boolean register(Users user) throws ServiceException;
}
