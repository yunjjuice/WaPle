package com.ssafy.waple.group.dto;

import io.swagger.annotations.ApiModelProperty;

public class GroupDto {
	@ApiModelProperty(value = "그룹 Id", example = "1")
	private int groupId;

	@ApiModelProperty(value = "그룹장 Id", example = "1412733569")
	private long userId;

	@ApiModelProperty(value = "그룹 이름", example = "나")
	private String name;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "GroupDto{" +
			"groupID=" + groupId +
			", userID=" + userId +
			", name=" + name +
			'}';
	}
}
