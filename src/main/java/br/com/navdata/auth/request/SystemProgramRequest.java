package br.com.navdata.auth.request;

import br.com.navdata.auth.entity.SystemUnitEntity;
public class SystemProgramRequest {

	private Integer id;
	private String name;
	private String path;
	private String method;
	private Integer systemUnitId;
	private Integer systemId;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getSystemUnitId() {
		return systemUnitId;
	}
	public void setSystemUnitId(Integer systemUnitId) {
		this.systemUnitId = systemUnitId;
	}
	public Integer getSystemId() {
		return systemId;
	}
	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
	



}
