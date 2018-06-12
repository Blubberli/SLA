package de.ws.shared;

import java.io.Serializable;

public class Admin extends Person implements Serializable{
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
