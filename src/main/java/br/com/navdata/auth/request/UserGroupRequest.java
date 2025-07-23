package br.com.navdata.auth.request;

public class UserGroupRequest {
	private Integer groupId;
	private boolean permitted;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public boolean isPermitted() {
		return permitted;
	}

	public void setPermitted(boolean permitted) {
		this.permitted = permitted;
	}

}