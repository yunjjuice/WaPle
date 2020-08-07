package com.ssafy.waple.group.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.ssafy.waple.group.dao.GroupDao;
import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.dto.GroupMemberDto;
import com.ssafy.waple.group.exception.DuplicateMemberException;
import com.ssafy.waple.group.exception.GroupIsNotEmptyException;
import com.ssafy.waple.group.exception.GroupNotFoundException;
import com.ssafy.waple.group.exception.InvalidGroupTokenException;
import com.ssafy.waple.group.exception.MemberNotFoundException;
import com.ssafy.waple.user.exception.UserNotFoundException;

@Service
public class GroupServiceImpl implements GroupService {
	private static final String GROUP_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`GROUP_MEMBERS`, CONSTRAINT `FK_GROUPS_GROUP_MEMBERS` FOREIGN KEY (`GROUP_ID`) REFERENCES `GROUPS` (`GROUP_ID`)";
	private static final String USER_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`GROUP_MEMBERS`, CONSTRAINT `FK_USERS_GROUP_MEMBERS` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)";
	private static final String PRIMARY_KEY_CONSTRAINT_MSG = "for key 'PRIMARY'";
	private static final String INVALID_TOKEN_MSG = "Column 'GROUP_ID' cannot be null";

	@Autowired
	GroupDao dao;

	@Override
	public List<GroupDto> readAll(long userId) {
		return dao.readAll(userId);
	}

	@Override
	public List<GroupMemberDto> readGroupMembers(int groupId) {
		List<GroupMemberDto> members = dao.readGroupMembers(groupId);
		if (members == null || members.size() < 1) { //그룹에 멤버는 최소 1명 (그룹장)
			throw new GroupNotFoundException(groupId);
		}
		return members;
	}

	@Override
	public boolean isOwner(int groupId, long userId) {
		return dao.isOwner(groupId, userId) == 1;
	}

	@Override
	public void create(GroupDto group) {
		dao.create(group);
		generateToken(group);
		createMember(group);
	}

	//JWT로 Token 생성하고 db에 삽입
	private void generateToken(GroupDto group) {
		String token = JWT.create()
			.withIssuer("Toppings")
			.withSubject("Invite group")
			.withClaim("groupId", group.getGroupId())
			.withClaim("name", group.getName())
			.sign(Algorithm.HMAC256("waple_project"));
		dao.updateToken(group.getGroupId(), token);
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
				throw new DuplicateMemberException(group.getGroupId(), group.getUserId());
			}
			if (e.getMessage().contains(INVALID_TOKEN_MSG)) {
				throw new InvalidGroupTokenException();
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
			List<GroupMemberDto> members = readGroupMembers(groupId);
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
