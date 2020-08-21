package com.ssafy.waple.bookmark.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.bookmark.dao.BookmarkDao;
import com.ssafy.waple.bookmark.dto.BookmarkDto;
import com.ssafy.waple.bookmark.dto.SearchType;
import com.ssafy.waple.bookmark.exception.BookmarkNotFonudException;
import com.ssafy.waple.bookmark.exception.GroupIsNullException;
import com.ssafy.waple.bookmark.exception.PlaceNotFoundException;
import com.ssafy.waple.bookmark.exception.ThemeIsNullException;
import com.ssafy.waple.error.exception.DuplicateKeyException;
import com.ssafy.waple.error.exception.PageNotFoundException;
import com.ssafy.waple.group.exception.GroupNotFoundException;
import com.ssafy.waple.theme.exception.ThemeNotFoundException;
import com.ssafy.waple.user.exception.UserNotFoundException;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	private final static String GROUP_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`BOOKMARKS`, CONSTRAINT `FK_THEMES_BOOKMARKS` FOREIGN KEY (`GROUP_ID`, `THEME_ID`) REFERENCES `THEMES` (`GROUP_ID`, `THEME_ID`)";
	private final static String THEME_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`BOOKMARKS`, CONSTRAINT `FK_PLACES_BOOKMARKS` FOREIGN KEY (`PLACE_ID`) REFERENCES `PLACES` (`PLACE_ID`)";
	private final static String PLACE_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`BOOKMARKS`, CONSTRAINT `FK_PLACES_BOOKMARKS` FOREIGN KEY (`PLACE_ID`) REFERENCES `PLACES` (`PLACE_ID`)";
	private final static String USER_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`BOOKMARKS`, CONSTRAINT `FK_USERS_BOOKMARKS` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)";
	private final static String DUPLICATE_KEY_CONSTRAINT_MSG = "Duplicate";

	@Autowired
	BookmarkDao dao;

	@Override
	public void create(String token, BookmarkDto bookmark) {
		try {
			dao.create(bookmark);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(GROUP_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new GroupNotFoundException(bookmark.getGroupId());
			}
			if(e.getMessage().contains(USER_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new UserNotFoundException(bookmark.getUserId());
			}
			if(e.getMessage().contains(PLACE_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new PlaceNotFoundException(bookmark.getPlaceId());
			}
			if(e.getMessage().contains(THEME_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new ThemeNotFoundException(bookmark.getGroupId(), bookmark.getThemeId());
			}
			if(e.getMessage().contains(DUPLICATE_KEY_CONSTRAINT_MSG)) {
				throw new DuplicateKeyException("Duplicate Primary KEY");
			}
			throw e;
		}
	}

	@Override
	public List<BookmarkDto> read(String token, SearchType type) {

		// 유저가 해당 그룹인지 유효성 처리
		long userId = type.getUserId();

		// Paging 처리
		int limit = type.getLimit();
		int offset = type.getOffset();

		if (offset < 1) {
			throw new PageNotFoundException();
		}

		// Search Data
		List<SearchType.Group> list = type.getGroupList();

		if(list == null) {
			throw new GroupIsNullException();
		}

		// Return Data
		List<BookmarkDto> result = new ArrayList<>();

		// 중복 체크용
		List<String> compare = new ArrayList<>();

		for (SearchType.Group group : list) {

			// 원하는 데이터 크기 넘어가면 조회 종료
			if (result.size() > offset * limit) {
				break;
			}
			if(group.getThemeId() == 0) {
				throw new ThemeIsNullException();
			}
			try {
				List<BookmarkDto> tempResult = dao.read(group);
				int size = tempResult.size();
				for (int i=0; i<size; i++) {
					BookmarkDto tempDto = tempResult.get(i);
					if(!compare.contains(tempDto.getPlaceId())) {
						compare.add(tempDto.getPlaceId());
						result.add(tempDto);
					}
				}
			} catch (DataAccessException e) {
				throw e;
			}
		}
		// for (BookmarkDto dto: result) {
		// 	System.out.println(dto.toString());
		// }

		if (result.size() < limit * offset) {
			if(limit * (offset-1) >= result.size()) {
				return new ArrayList<>();
			}
			return result.subList((offset - 1) * limit, result.size());
		} else {
			return result.subList((offset - 1) * limit, offset * limit);
		}
	}

	@Override
	public void delete(String token, int themeId, int groupId, String placeId) {
		if(dao.delete(themeId, groupId, placeId) < 1) {
			throw new BookmarkNotFonudException(groupId, themeId, placeId);
		}
	}

	@Override
	public List<BookmarkDto> readAll(String token, long userId, int limit, int offset) {
		List<BookmarkDto> result = new ArrayList<>();
		try {
			result = dao.readAll(userId, limit, (offset-1) * limit);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(USER_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new UserNotFoundException(userId);
			}
		}
		return result;
	}
}
