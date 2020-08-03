package com.ssafy.waple.notification.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.waple.notification.dto.NotificationDto;

@Mapper
public interface NotificationDao {

	// 알림 생성
	int create(NotificationDto dto);

	// 알림 최초 생성
	int createInit(NotificationDto dto);

	// 알림 조회
	NotificationDto read(@Param("groupId")int groupId, @Param("notificationId")int notificationId);
}
