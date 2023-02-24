package org.holodeckb2b.webui.application.rest.impl.user;

import org.holodeckb2b.webui.application.rest.api.users.User;

public class UserImpl implements User {

	private String id;
	private String name;
	private String secret;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
