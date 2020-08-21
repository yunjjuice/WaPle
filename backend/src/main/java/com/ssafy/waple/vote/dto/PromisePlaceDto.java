package com.ssafy.waple.vote.dto;

import io.swagger.annotations.ApiModelProperty;

public class PromisePlaceDto {
	@ApiModelProperty(value = "그룹 Id", example = "1")
	private int groupId;

	@ApiModelProperty(value = "약속 Id", example = "1")
	private int promiseId;

	@ApiModelProperty(value = "투표 장소 Id", example = "19781214")
	private String placeId;

	@ApiModelProperty(value = "장소 추가한 유저 Id", example = "1412733569")
	private long userId;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getPromiseId() {
		return promiseId;
	}

	public void setPromiseId(int promiseId) {
		this.promiseId = promiseId;
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
		return "PromisePlaceDto{" +
			"groupId=" + groupId +
			", promiseId=" + promiseId +
			", placeId='" + placeId + '\'' +
			", userId=" + userId +
			'}';
	}
}
