package br.com.navdata.auth.request;

public class SystemUserRequest {

	private Integer id;
	private String name;
	private String email;
	private String doc;
	private String fone;
	private String razao;
	private String password;
	private Integer systemUnitId;
	private String active;

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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}
	
	

}