package br.com.navdata.auth.response;

public class AuthResponse {

	private String accessToken;
	private SystemUserResponse systemUser;

	public AuthResponse() {
	}

	public AuthResponse(String accessToken, SystemUserResponse systemUser) {
		this.accessToken = accessToken;
		this.systemUser = systemUser;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public SystemUserResponse getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUserResponse systemUser) {
		this.systemUser = systemUser;
	}

}