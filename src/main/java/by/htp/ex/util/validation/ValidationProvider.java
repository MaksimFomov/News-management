package by.htp.ex.util.validation;

public class ValidationProvider {
	private static final ValidationProvider instance = new ValidationProvider();
	private final UserDataValidation userDataValidation = new UserDataValidationImpl();
	private final NewsDataValidation newsDataValidation = new NewsDataValidationImpl();
	
	private ValidationProvider() {}
	
	public static ValidationProvider getInstance() {
		return instance;
	}

	public UserDataValidation getUserDataValidation() {
		return userDataValidation;
	}
	
	public NewsDataValidation getNewsDataValidation() {
		return newsDataValidation;
	}
}
