package br.com.navdata.auth.dto;

public class TokenValidationDTO {
	private String token;

    public TokenValidationDTO() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
