package com.ssafy.waple.group.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.group.dao.GroupDao;
import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.exception.DuplicatedMemberException;
import com.ssafy.waple.group.exception.GroupIsNotEmptyException;
import com.ssafy.waple.group.exception.GroupNotFoundException;
import com.ssafy.waple.group.exception.MemberNotFoundException;
import com.ssafy.waple.user.exception.UserNotFoundException;

@Service
public class GroupServiceImpl implements GroupService {
	private static final String GROUP_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`GROUP_MEMBERS`, CONSTRAINT `FK_GROUPS_GROUP_MEMBERS` FOREIGN KEY (`GROUP_ID`) REFERENCES `GROUPS` (`GROUP_ID`)";
	private static final String USER_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`GROUP_MEMBERS`, CONSTRAINT `FK_USERS_GROUP_MEMBERS` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)";
	private static final String PRIMARY_KEY_CONSTRAINT_MSG = "for key 'PRIMARY'";
	@Autowired
	GroupDao dao;

	@Override
	public List<GroupDto> readAll(long userId) {
		return dao.readAll(userId);
	}

	@Override
	public List<GroupDto> readGroupMembers(int groupId) {
		List<GroupDto> groups = dao.readGroupMembers(groupId);
		if (groups == null || groups.size() < 1) { //그룹에 멤버는 최소 1명 (그룹장)
			throw new GroupNotFoundException(groupId);
		}
		return groups;
	}

	@Override
	public boolean isOwner(int groupId, long userId) {
		return dao.isOwner(groupId, userId) == 1;
	}

	@Override
	public void create(GroupDto group) {
		dao.create(group);
		createMember(group);
	}

	@Override
	public void createMember(GroupDto group) {
		try {
			dao.createMember(group);
		} catch (DataAccessException e) {
			if (e.getMessage().contains(GROUP_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new GroupNotFoundException(group.getGroupId());
			}
			if (e.getMessage().contains(USER_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new UserNotFoundException(group.getUserId());
			}
			if (e.getMessage().contains(PRIMARY_KEY_CONSTRAINT_MSG)) {
				throw new DuplicatedMemberException(group.getGroupId(), group.getUserId());
			}
			throw e;
		}
	}

	@Override
	public void update(GroupDto group) {
		if (dao.update(group) < 1) {
			throw new GroupNotFoundException(group.getGroupId());
		}
	}

	@Override
	public void delete(int groupId, long userId) {
		boolean isOwner = isOwner(groupId, userId);
		if (isOwner) {
			List<GroupDto> members = readGroupMembers(groupId);
			if (members.size() > 1 || members.get(0).getUserId() != userId) {
				throw new GroupIsNotEmptyException(groupId);
			}
		}

		deleteMember(groupId, userId);
		if (isOwner && dao.delete(groupId) < 1) {
			throw new GroupNotFoundException(groupId);
		}
	}

	@Override
	public void deleteMember(int groupId, long userId) {
		if (dao.deleteMember(groupId, userId) < 1) {
			throw new MemberNotFoundException(groupId, userId);
		}
	}
}
