package com.ssafy.waple.group.dto;

import io.swagger.annotations.ApiModelProperty;

public class GroupCreateDto extends GroupDto {
	@ApiModelProperty(value = "유저 Id", example = "1412733569")
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
