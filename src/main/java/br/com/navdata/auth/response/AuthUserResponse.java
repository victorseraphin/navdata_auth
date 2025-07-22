package br.com.navdata.auth.response;

import java.util.List;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;

public class AuthUserResponse {

	private Integer id;
	private String name;
	private String email;
	private Integer systemUnitId;

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

	public Integer getSystemUnitId() {
		return systemUnitId;
	}

	public void setSystemUnitId(Integer systemUnitId) {
		this.systemUnitId = systemUnitId;
	}
	
	
}