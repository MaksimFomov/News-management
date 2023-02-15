package by.htp.ex.bean;

import java.io.Serializable;
import java.util.Objects;

public class Users implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String login;
	private String password;
	private int roles_id;

	public Users() {}

	public Users(int id, String login, String password, int roles_id) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.roles_id = roles_id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public int getRoles_id() {
		return roles_id;
	}

	public void setRoles_id(int roles_id) {
		this.roles_id = roles_id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Users that = (Users) o;
		return Objects.equals(id, that.id) && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(roles_id, that.roles_id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, roles_id);
	}
}
