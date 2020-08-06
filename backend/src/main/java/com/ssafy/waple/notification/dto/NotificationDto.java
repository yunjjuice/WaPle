package com.ssafy.waple.notification.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class NotificationDto {

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "알림 번호", example = "1")
	private int notificationId;

	@ApiModelProperty(value = "메시지", example = "바나나먹는몽키님이 새로운 장소를 추가했습니다.")
	private String message;

	@ApiModelProperty(value = "알림 일정", example = "2020-07-31")
	private Date notificationDate;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}

	@Override
	public String toString() {
		return "NotificationsDao{" +
			"groupId=" + groupId +
			", notificationId=" + notificationId +
			", message='" + message + '\'' +
			", notificationDate=" + notificationDate +
			'}';
	}
}
