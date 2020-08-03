package com.ssafy.waple.notification.service;

import com.ssafy.waple.notification.dto.NotificationDto;

public interface NotificationService {

	// 알림 생성
	void create(String token, NotificationDto dto);


}
