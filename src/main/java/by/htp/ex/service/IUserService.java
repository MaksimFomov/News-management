package by.htp.ex.service;

import by.htp.ex.bean.Users;

public interface IUserService {
	String signIn(Users user) throws ServiceException;
	boolean register(Users user) throws ServiceException;
}
