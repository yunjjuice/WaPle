package com.ssafy.waple.theme.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ThemeGroupAll {
	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "테마 아이디들(합친것)", example = "1,2,3")
	private String themes;

	@ApiModelProperty(value = "테마 아이디들", example = "[1, 2, 3, 4]")
	private List<Integer> themeIds;

	public ThemeGroupAll() {}

	public ThemeGroupAll(int groupId, List<Integer> themeIds) {
		this.groupId = groupId;
		this.themeIds = themeIds;
	}

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

	public List<Integer> getThemeIds() {
		return themeIds;
	}

	public void setThemeIds(List<Integer> themeIds) {
		this.themeIds = themeIds;
	}
}
