package com.ssafy.waple.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.group.dto.GroupDto;

@Mapper
public interface GroupDao {
	List<GroupDto> readAll(long userId);

	List<GroupDto> readGroupMembers(int groupId);

	int isOwner(int groupId, long userId);

	int create(GroupDto group);

	int createMember(GroupDto group);

	int update(GroupDto group);

	int delete(int groupId);

	int deleteMember(int groupId, long userId);
}
