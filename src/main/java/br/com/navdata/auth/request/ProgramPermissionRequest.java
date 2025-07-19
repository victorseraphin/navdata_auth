package br.com.navdata.auth.request;

public class ProgramPermissionRequest {
	private Integer programId;
	private boolean permitted;

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public boolean isPermitted() {
		return permitted;
	}

	public void setPermitted(boolean permitted) {
		this.permitted = permitted;
	}

}