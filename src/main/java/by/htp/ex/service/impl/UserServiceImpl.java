package by.htp.ex.service.impl;

import by.htp.ex.bean.Users;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.IUserService;

public class UserServiceImpl implements IUserService{
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	public static final String ROLE_GUEST = "guest";

	private final IUserDAO userDAO = DaoProvider.getInstance().getUserDao();
	
	@Override
	public String signIn(String login, String password) throws ServiceException {
		try {
			if(userDAO.logination(login, password)) {
				return userDAO.getRole(login, password);
			} 
			else {
				return ROLE_GUEST;
			}
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean register(Users user)  throws ServiceException {
		try {
			return userDAO.registration(user);

		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}
}
