package br.com.navdata.auth.request;

public class AuthUserRequest {

	private Integer id;
    private String name;
    private String email;
    private String password; 
    private Integer systemUnitId;
    private Integer frontpageId;
    private String facebookId;
    private String googleId;
    private boolean active;
    private String rememberToken;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSystemUnitId() {
		return systemUnitId;
	}
	public void setSystemUnitId(Integer systemUnitId) {
		this.systemUnitId = systemUnitId;
	}
	public Integer getFrontpageId() {
		return frontpageId;
	}
	public void setFrontpageId(Integer frontpageId) {
		this.frontpageId = frontpageId;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRememberToken() {
		return rememberToken;
	}
	public void setRememberToken(String rememberToken) {
		this.rememberToken = rememberToken;
	}
	
}