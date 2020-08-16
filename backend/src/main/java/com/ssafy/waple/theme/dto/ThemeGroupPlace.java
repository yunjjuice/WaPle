package com.ssafy.waple.theme.dto;

import io.swagger.annotations.ApiModelProperty;

public class ThemeGroupPlace {

	@ApiModelProperty(value = "그룹 이름", example = "먹방")
	private String groupName;

	@ApiModelProperty(value = "테마 이름", example = "하하하")
	private String themeName;

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "테마 아이디", example = "1")
	private int themeId;

	@ApiModelProperty(value = "아이콘", example = "default.png")
	private String icon;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
