package br.com.navdata.auth.request;

import java.util.List;


public class SystemRequest {
	
	private Integer id;
    private String name;
	private SystemUnitRequest systemUnit;
	
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
	public SystemUnitRequest getSystemUnit() {
		return systemUnit;
	}
	public void setSystemUnit(SystemUnitRequest systemUnit) {
		this.systemUnit = systemUnit;
	}


	
    
}
