package com.ssafy.waple.bookmark.dto;

import io.swagger.annotations.ApiModelProperty;

public class BookMarkDto {
	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "테마 아이디", example = "1")
	private int themeId;

	@ApiModelProperty(value = "장소 아이디", example = "19781214")
	private String placeId;

	@ApiModelProperty(value = "회원 아이디", example = "1412733569")
	private long userId;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BookMarkDto{" +
			"groupId=" + groupId +
			", themeId=" + themeId +
			", placeId='" + placeId + '\'' +
			", userId=" + userId +
			'}';
	}
}
