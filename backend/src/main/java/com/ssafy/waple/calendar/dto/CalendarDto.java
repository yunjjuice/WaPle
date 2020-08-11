package com.ssafy.waple.calendar.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

public class CalendarDto {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
	@ApiModelProperty(value = "약속 일시", example = "2020-08-01")
	private Date promiseDate;

	@ApiModelProperty(value = "약속명", example = "회전초밥집")
	private String title;

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "약속 번호", example = "1")
	private int promiseId;

	public Date getPromiseDate() {
		return promiseDate;
	}

	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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
	

	@Override
	public String toString() {
		return "CalendarDto{" +
			"promiseDate=" + promiseDate +
			", title='" + title + '\'' +
			", groupId=" + groupId +
			", promiseId=" + promiseId +
			'}';
	}
}
