package by.htp.ex.service;

import by.htp.ex.bean.User;

public interface IUserService {
	String signIn(User user) throws ServiceException;
	boolean register(User user) throws ServiceException;
}
