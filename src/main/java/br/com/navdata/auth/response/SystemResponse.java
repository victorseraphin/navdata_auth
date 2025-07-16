package br.com.navdata.auth.response;

public class SystemResponse {

	private Integer id;
	private String name;
	private Integer systemUnitId;
	private String systemUnitDesc;

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

	public Integer getSystemUnitId() {
		return systemUnitId;
	}

	public void setSystemUnitId(Integer systemUnitId) {
		this.systemUnitId = systemUnitId;
	}

	public String getSystemUnitDesc() {
		return systemUnitDesc;
	}

	public void setSystemUnitDesc(String systemUnitDesc) {
		this.systemUnitDesc = systemUnitDesc;
	}

}
