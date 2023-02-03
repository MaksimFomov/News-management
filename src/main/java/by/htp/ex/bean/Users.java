package by.htp.ex.bean;

import java.io.Serializable;
import java.util.Objects;

public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	public Users() {
	}

	public Users(String login, String password) {
		this.login = login;
		this.password = password;
	}

	private String login;
	private String password;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Users that = (Users) o;
		return Objects.equals(login, that.login) && Objects.equals(password, that.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(login, password);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
