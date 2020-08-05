package com.ssafy.waple.promise.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class PromiseDto {
	@ApiModelProperty(value = "그룹 Id", example = "1")
	private int groupId;

	@ApiModelProperty(value = "그룹 이름", example = "나")
	private String name;

	@ApiModelProperty(value = "약속 Id", example = "1")
	private int promiseId;

	@ApiModelProperty(value = "약속 이름", example = "정기 방탈출 모임")
	private String title;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
	@ApiModelProperty(value = "약속 날짜", example = "2020-07-31T18:00")
	private Date promiseDate;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPromiseId() {
		return promiseId;
	}

	public void setPromiseId(int promiseId) {
		this.promiseId = promiseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPromiseDate() {
		return promiseDate;
	}

	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}

	@Override
	public String toString() {
		return "PromiseDto{" +
			"groupId=" + groupId +
			", name='" + name + '\'' +
			", promiseId=" + promiseId +
			", title='" + title + '\'' +
			", promiseDate=" + promiseDate +
			'}';
	}
}
