package com.ssafy.waple.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.bookmark.error.PlaceNotFoundException;
import com.ssafy.waple.promise.exception.PromiseNotFoundException;
import com.ssafy.waple.user.exception.UserNotFoundException;
import com.ssafy.waple.vote.dao.VoteDao;
import com.ssafy.waple.vote.dto.PromisePlaceDto;
import com.ssafy.waple.vote.dto.PromisePlaceResponseDto;
import com.ssafy.waple.vote.dto.VoteDto;
import com.ssafy.waple.vote.exception.DuplicatePromisePlaceException;
import com.ssafy.waple.vote.exception.DuplicateVoteException;
import com.ssafy.waple.vote.exception.PromisePlaceNotFoundException;
import com.ssafy.waple.vote.exception.VoteNotFoundException;

@Service
public class VoteServiceImpl implements VoteService {
	private static final String PROMISES_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`PROMISES_AND_PLACES`, CONSTRAINT `FK_PROMISES_PROMISES_AND_PLACES` FOREIGN KEY (`GROUP_ID`, `PROMISE_ID`) REFERENCES `PROMISES` (`GROUP_ID`, `PROMISE_ID`)";
	private static final String PLACES_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`PROMISES_AND_PLACES`, CONSTRAINT `FK_PLACES_PROMISES_AND_PLACES` FOREIGN KEY (`PLACE_ID`) REFERENCES `PLACES` (`PLACE_ID`)";
	private static final String USERS_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`PROMISES_AND_PLACES`, CONSTRAINT `FK_USERS_PROMISES_AND_PLACES` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)";
	private static final String USERS_VOTES_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`VOTES`, CONSTRAINT `FK_USERS_VOTES` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)";
	private static final String PROMISES_AND_PLACES_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`VOTES`, CONSTRAINT `FK_PROMISES_AND_PLACES_VOTES` FOREIGN KEY (`GROUP_ID`, `PROMISE_ID`, `PLACE_ID`) REFERENCES `PROMISES_AND_PLACES` (`GROUP_ID`, `PROMISE_ID`, `PLACE_ID`)";
	private static final String DUPLICATE_ENTRY_MSG = "Duplicate entry ";

	@Autowired
	VoteDao dao;

	@Override
	public void create(PromisePlaceDto promisePlace) {
		try {
			dao.create(promisePlace);
		} catch (DataAccessException e) {
			if (e.getMessage().contains(PROMISES_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new PromiseNotFoundException(promisePlace.getGroupId(), promisePlace.getPromiseId());
			}
			if (e.getMessage().contains(PLACES_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new PlaceNotFoundException(promisePlace.getPlaceId());
			}
			if (e.getMessage().contains(USERS_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new UserNotFoundException(promisePlace.getUserId());
			}
			if (e.getMessage().contains(DUPLICATE_ENTRY_MSG)) {
				throw new DuplicatePromisePlaceException(promisePlace.getGroupId(), promisePlace.getPromiseId(),
					promisePlace.getPlaceId());
			}
			throw e;
		}
	}

	@Override
	public List<PromisePlaceResponseDto> readAll(int groupId, int promiseId, long userId) {
		return dao.readAll(groupId, promiseId, userId);
	}

	@Override
	public void createVote(VoteDto vote) {
		try {
			dao.createVote(vote);
		} catch (DataAccessException e) {
			if (e.getMessage().contains(PROMISES_AND_PLACES_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new PromisePlaceNotFoundException(vote.getGroupId(), vote.getPromiseId(), vote.getPlaceId());
			}
			if (e.getMessage().contains(USERS_VOTES_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new UserNotFoundException(vote.getUserId());
			}
			if (e.getMessage().contains(DUPLICATE_ENTRY_MSG)) {
				throw new DuplicateVoteException(vote.getGroupId(), vote.getPromiseId(), vote.getPlaceId(),
					vote.getUserId());
			}
			throw e;
		}
	}

	@Override
	public void deleteVote(int groupId, int promiseId, String placeId, long userId) {
		if (dao.deleteVote(groupId, promiseId, placeId, userId) < 1) {
			throw new VoteNotFoundException(groupId, promiseId, placeId, userId);
		}
	}
}
