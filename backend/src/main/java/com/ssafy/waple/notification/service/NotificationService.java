package com.ssafy.waple.notification.service;

import com.ssafy.waple.notification.dto.NotificationDto;

public interface NotificationService {

	// 알림 생성
	void create(String token, NotificationDto dto);

	// 알림 조회
	NotificationDto read(String token, int groupId, int notificationId);
}
