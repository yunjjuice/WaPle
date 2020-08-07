package com.ssafy.waple.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.dto.GroupMemberDto;

@Mapper
public interface GroupDao {
	List<GroupDto> readAll(long userId);

	List<GroupMemberDto> readGroupMembers(int groupId);

	int isOwner(int groupId, long userId);

	int create(GroupDto group);

	int updateToken(int groupId, String token);

	int createMember(GroupDto group);

	int update(GroupDto group);

	int delete(int groupId);

	int deleteMember(int groupId, long userId);
}
