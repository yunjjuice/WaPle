package com.ssafy.waple.theme.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ThemeGroupAll {
	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "그룹 이름들(합친것)", example = "먹방,찜질방")
	private String groupName;

	@ApiModelProperty(value = "그룹 이름들", example = "[먹방, 찜질방]")
	private List<String> groupNames;

	@ApiModelProperty(value = "테마 아이디들(합친것)", example = "1,2,3")
	private String themes;

	@ApiModelProperty(value = "테마 이름들(합친것)", example = "하하하,호호호,히히히,후후후")
	private String themeName;

	@ApiModelProperty(value = "테마 아이디들", example = "[1, 2, 3, 4]")
	private List<Integer> themeIds;

	@ApiModelProperty(value = "테마 이름들", example = "[하하하, 호호호, 히히히, 후후후]")
	private List<String> themeNames;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getThemes() {
		return themes;
	}

	public void setThemes(String themes) {
		this.themes = themes;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public List<Integer> getThemeIds() {
		return themeIds;
	}

	public List<String> getThemeNames() {
		return themeNames;
	}

	public void setThemeNames(List<String> themeNames) {
		this.themeNames = themeNames;
	}

	public void setThemeIds(List<Integer> themeIds) {
		this.themeIds = themeIds;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<String> getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
	}
}
