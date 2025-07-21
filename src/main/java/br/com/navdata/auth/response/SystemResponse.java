package br.com.navdata.auth.response;

import java.util.List;

public class SystemResponse {

	private Integer id;
	private String name;
	List<Integer> systemProgramId;

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

	public List<Integer> getSystemProgramId() {
		return systemProgramId;
	}

	public void setSystemProgramId(List<Integer> systemProgramId) {
		this.systemProgramId = systemProgramId;
	}

	

	

}
