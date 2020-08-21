package com.ssafy.waple.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.bookmark.exception.PlaceNotFoundException;
import com.ssafy.waple.error.exception.IncorrectFormatException;
import com.ssafy.waple.error.exception.PageNotFoundException;
import com.ssafy.waple.group.exception.GroupNotFoundException;
import com.ssafy.waple.review.dao.ReviewDao;
import com.ssafy.waple.review.dto.ReviewDto;
import com.ssafy.waple.review.dto.ReviewPlaceDto;
import com.ssafy.waple.review.exception.NameNotEmptyException;
import com.ssafy.waple.review.exception.ReviewNotFoundException;
import com.ssafy.waple.user.exception.UserNotFoundException;

@Service
public class ReviewServiceImpl implements ReviewService {

	private static final String USER_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`REVIEWS`, CONSTRAINT `FK_USERS_REVIEWS` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)";
	private static final String GROUP_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`REVIEWS`, CONSTRAINT `FK_GROUPS_REVIEWS` FOREIGN KEY (`GROUP_ID`) REFERENCES `GROUPS` (`GROUP_ID`)";
	private static final String PLACE_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`REVIEWS`, CONSTRAINT `FK_PLACES_REVIEWS` FOREIGN KEY (`PLACE_ID`) REFERENCES `PLACES` (`PLACE_ID`)";
	private static final String INCORRECT_DATE_TYPE = "Incorrect date value";

	@Autowired
	ReviewDao dao;

	@Override
	public void create(String token, ReviewDto dto) {
		// 유효성 검증 : 유저 찾기, 그룹 찾기, 장소 찾기
		if ("".equals(dto.getTitle())) {
			throw new NameNotEmptyException();
		}
		try {
			dao.create(dto);
		} catch (DataAccessException e) {
			if (e.getMessage().contains(USER_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new UserNotFoundException(dto.getUserId());
			}
			if (e.getMessage().contains(GROUP_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new GroupNotFoundException(dto.getGroupId());
			}
			if (e.getMessage().contains(PLACE_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new PlaceNotFoundException(dto.getPlaceId());
			}
			if (e.getMessage().contains(INCORRECT_DATE_TYPE)) {
				throw new IncorrectFormatException("Incorrect Date Format");
			}
			throw e;
		}
	}

	@Override
	public List<ReviewDto> readAll(String token, long userId, String placeId, int limit, int offset) {
		if (offset < 1) {
			throw new PageNotFoundException();
		}
		// User Not in This Group Exception, Group Not Found Exception, UserNotFound Exception, Theme Not Found Exception
		List<ReviewDto> result = dao.readAll(userId, placeId);

		if (result.size() - (offset - 1) * limit < limit) {
			return result.subList((offset - 1) * limit, result.size());
		} else {
			return result.subList((offset - 1) * limit, offset * limit);
		}
	}

	@Override
	public List<ReviewPlaceDto> readAllByUserId(String token, long userId, int limit, int offset) {
		if (offset < 1) {
			throw new PageNotFoundException();
		}
		// UserNotFound Exception
		List<ReviewPlaceDto> result = dao.readAllByUserId(userId);
		if (result.size() - (offset - 1) * limit < limit) {
			return result.subList((offset - 1) * limit, result.size());
		} else {
			return result.subList((offset - 1) * limit, offset * limit);
		}
	}

	@Override
	public ReviewDto read(String token, int reviewId) {
		return dao.read(reviewId);
	}

	@Override
	public void update(String token, int reviewId, String title, String content) {
		if ("".equals(title)) {
			throw new NameNotEmptyException();
		}
		if (dao.update(reviewId, title, content) < 1) {
			throw new ReviewNotFoundException(reviewId);
		}
	}

	@Override
	public void delete(String token, int reviewId) {
		if (dao.delete(reviewId) < 1) {
			throw new ReviewNotFoundException(reviewId);
		}
	}
}
