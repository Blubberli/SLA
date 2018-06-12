package de.ws.shared;

import java.io.Serializable;

public class Person implements Serializable{
	String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
