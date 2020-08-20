package com.mpl.login.response;

import java.time.OffsetDateTime;

/**

 * Esta clase es utilizada para generar la respuesta del servicio

 * @author: Juan David Gallego G.

 * @version: 20/08/2020

 */

public class LoginResponse {
	
	private String session;
	private String description;
	private String timestamp;
	
	public LoginResponse(String session, String description) {
		super();
		this.session = session;
		this.description = description;
		this.timestamp = OffsetDateTime.now().toString();

	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	
   
	

}
