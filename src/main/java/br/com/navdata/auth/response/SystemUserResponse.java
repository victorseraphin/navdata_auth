package br.com.navdata.auth.response;

import java.util.List;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;

public class SystemUserResponse {

	private Integer id;
	private String name;
	private String razao;
	private String doc;
	private String fone;
	private String login;
	private String email;
	private SystemUnitResponse systemUnit;
	private Integer frontpageId;
	private String facebookId;
	private String googleId;
	private String active;
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
	public SystemUnitResponse getSystemUnit() {
		return systemUnit;
	}
	public void setSystemUnit(SystemUnitResponse systemUnit) {
		this.systemUnit = systemUnit;
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

	
	
	

}