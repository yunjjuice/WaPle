package com.ssafy.waple.bookmark.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

public class SearchType {
	@ApiModelProperty(value = "회원 아이디", example = "1412733569")
	@SerializedName("userId")
	private long userId;

	@ApiModelProperty(value = "그룹들", example = "1")
	@SerializedName("groups")
	private List<Group> groupList;

	@ApiModelProperty(value = "한 페이지에 출력할 갯수", example = "10")
	@SerializedName("limit")
	private int limit;

	@ApiModelProperty(value = "페이지 인덱스 번호", example = "1")
	@SerializedName("offset")
	private int offset;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SearchType{User_ID=").append(this.userId).append(", Group_IDs=");
		for (Group group : this.groupList) {
			sb.append(group.toString()).append("\n");
		}
		sb.append("Limit=").append(this.limit).append(", Offset=").append(this.offset).append("}");
		return sb.toString();
	}

	public static class Group {
		@ApiModelProperty(value = "그룹 아이디", example = "1")
		@SerializedName("groupId")
		private int groupId;

		@ApiModelProperty(value = "테마 아이디들", example = "1")
		@SerializedName("themeIds")
		private List<Integer> themeIds;

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

		public List<Integer> getThemeIds() {
			return themeIds;
		}

		public void setThemeIds(List<Integer> themeIds) {
			this.themeIds = themeIds;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("SearchType.Group{Group_ID=").append(this.groupId).append(", Theme_IDs=");
			for (int i = 0; i < this.themeIds.size(); i++) {
				sb.append(this.themeIds.get(i));
				if (!(i == this.themeIds.size() - 1))
					sb.append(", ");
			}
			sb.append("}");
			return sb.toString();
		}
	}
}
