package com.ssafy.waple.group.service;

import java.util.List;

import com.ssafy.waple.group.dto.GroupDto;

public interface GroupService {
	List<GroupDto> readAll(long userId);

	List<GroupDto> readGroupMembers(int groupId);

	boolean isOwner(int groupId, long userId);

	void create(GroupDto group);

	void createMember(GroupDto group);

	void update(GroupDto group);

	void delete(int groupId, long userId);

	void deleteMember(int groupId, long userId);
}
