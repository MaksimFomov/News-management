package by.htp.ex.service.impl;

import by.htp.ex.bean.Users;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.UserDataValidation;
import by.htp.ex.util.validation.ValidationProvider;
import by.htp.ex.service.IUserService;

public class UserServiceImpl implements IUserService {
	private final UserDataValidation userDataValidation = ValidationProvider.getInstance().getUserDataValidation();
	private final IUserDAO userDAO = DaoProvider.getInstance().getUserDao();
	
	private static final String ERROR_MESSAGE_FOR_INVALID_LOGIN_OR_PASSWORD = "invalid login or password value";
	private static final String ROLE_GUEST = "guest";
	
	@Override
	public String signIn(Users user) throws ServiceException {		
		try {
			if(userDAO.logination(user)) {
				return userDAO.getRole(user);
			} 
			else {
				return ROLE_GUEST;
			}
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean register(Users user) throws ServiceException {
		if(!userDataValidation.checkAuthData(user.getLogin(), user.getPassword())) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_LOGIN_OR_PASSWORD);
		}
		
		try {
			return userDAO.registration(user);
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}
}
