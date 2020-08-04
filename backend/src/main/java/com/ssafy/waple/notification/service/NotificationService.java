package com.ssafy.waple.notification.service;

import java.util.List;

import com.ssafy.waple.notification.dto.NotificationDto;

public interface NotificationService {

	// 알림 생성
	void create(String token, NotificationDto dto);

	// 알림 조회
	NotificationDto read(String token, int groupId, int notificationId);

	// 알림 리스트 조회
	List<NotificationDto> readAll(String token, long userId);
}
