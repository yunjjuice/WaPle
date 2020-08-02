package com.ssafy.waple.theme.dto;

import io.swagger.annotations.ApiModelProperty;

public class ThemeDto {
	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "테마 아이디", example = "1")
	private int themeId;

	@ApiModelProperty(value = "테마 이름", example = "먹방")
	private String name;

	@ApiModelProperty(value = "테마 아이콘", example = "food.ico")
	private String icon;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[Theme_ID : ").append(this.themeId).append(", Group_ID : ").append(this.groupId).append(", Name : "
		).append(this.name).append(", icon : ").append(this.icon).append(" ]");
		return sb.toString();
	}
}
