package br.com.navdata.auth.response;

public class ProgramPermissionResponse {
	private Integer programId;
	private String name;
	private String path;
	private String method;
	private boolean permitted;

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer integer) {
		this.programId = integer;
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

	public boolean isPermitted() {
		return permitted;
	}

	public void setPermitted(boolean permitted) {
		this.permitted = permitted;
	}

}
