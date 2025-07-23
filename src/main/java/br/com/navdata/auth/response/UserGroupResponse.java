package br.com.navdata.auth.response;

public class UserGroupResponse {
	private Integer groupId;
	private String name;
	private boolean permitted;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPermitted() {
		return permitted;
	}

	public void setPermitted(boolean permitted) {
		this.permitted = permitted;
	}

}
