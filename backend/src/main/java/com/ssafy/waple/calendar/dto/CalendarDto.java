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

	@ApiModelProperty(value = "장소 이름", example = "스시메이진 홍대점")
	private String placeName;

	@ApiModelProperty(value = "장소 아이디", example = "123456789")
	private String placeId;

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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	@Override
	public String toString() {
		return "CalendarDto{" +
			"promiseDate=" + promiseDate +
			", title='" + title + '\'' +
			", groupId=" + groupId +
			", promiseId=" + promiseId +
			", placeName='" + placeName + '\'' +
			", placeId='" + placeId + '\'' +
			'}';
	}
}
