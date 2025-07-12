package br.com.navdata.auth.dto;

import java.time.LocalDateTime;

public class SystemUserDTO {

	private Integer id;
    private String name;
    private String razao;
    private String doc;
    private String fone;
    private String login;
    private String email;
    private String password; 
    private Integer systemUnitId;
    private Integer frontpageId;
    private String facebookId;
    private String googleId;
    private String active;
    private String rememberToken;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;
    
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
	public String getRazao() {
		return razao;
	}
	public void setRazao(String razao) {
		this.razao = razao;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getRememberToken() {
		return rememberToken;
	}
	public void setRememberToken(String rememberToken) {
		this.rememberToken = rememberToken;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public LocalDateTime getDeleted_at() {
		return deleted_at;
	}
	public void setDeleted_at(LocalDateTime deleted_at) {
		this.deleted_at = deleted_at;
	}

    
    
}
