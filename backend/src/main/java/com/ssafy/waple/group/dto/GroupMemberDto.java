package com.ssafy.waple.group.dto;

import io.swagger.annotations.ApiModelProperty;

public class GroupMemberDto {
	@ApiModelProperty(value = "회원 아이디", example = "1412733569")
	private long userId;

	@ApiModelProperty(value = "회원 닉네임", example = "바나나먹는몽키")
	private String name;

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
		return "GroupMemberDto{" +
			"userId=" + userId +
			", name='" + name + '\'' +
			'}';
	}
}
