package com.ssafy.waple.group.service;

import java.util.List;

import com.ssafy.waple.group.dto.GroupCreateDto;
import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.dto.GroupMemberDto;

public interface GroupService {
	List<GroupDto> readAll(long userId);

	List<GroupMemberDto> readGroupMembers(int groupId);

	void create(GroupCreateDto group);

	void createMember(GroupCreateDto group);

	void update(GroupDto group);

	void delete(int groupId, long userId);

	void deleteMember(int groupId, long userId);
}
