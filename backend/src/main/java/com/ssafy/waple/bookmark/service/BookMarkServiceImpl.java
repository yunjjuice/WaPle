package com.ssafy.waple.bookmark.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.bookmark.dao.BookMarkDao;
import com.ssafy.waple.bookmark.dto.BookMarkDto;
import com.ssafy.waple.bookmark.dto.SearchType;

@Service
public class BookMarkServiceImpl implements BookMarkService {
	@Autowired
	BookMarkDao dao;

	@Override
	public boolean create(BookMarkDto bookMark) {
		int result = 0;
		try {
			result = dao.create(bookMark);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public List<BookMarkDto> read(SearchType type) {

		boolean success = false;
		long userId = type.getUserId();

		// Paging 처리
		int limit = type.getLimit();
		int offset = type.getOffset();

		// Search Data
		List<SearchType.Group> list = type.getGroupList();

		// Return Data
		List<BookMarkDto> result = new ArrayList<>();

		for (SearchType.Group group : list) {

			// 원하는 데이터 크기 넘어가면 조회 종료
			if (result.size() > offset * limit)
				break;
			try {
				result.addAll(dao.read(group));
				success = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (success && offset >= 1) {
			if (result.size() < limit) {
				return result.subList((offset - 1) * limit, result.size());
			} else {
				return result.subList((offset - 1) * limit, offset * limit);
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean delete(long userId, int themeId, int groupId, String placeId) {
		boolean success = dao.delete(userId, themeId, groupId, placeId) > 0;
		return success;
	}
}
