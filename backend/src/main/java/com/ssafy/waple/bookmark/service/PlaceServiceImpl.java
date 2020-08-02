package com.ssafy.waple.bookmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.bookmark.dao.PlaceDao;
import com.ssafy.waple.bookmark.dto.PlaceDto;

@Service
public class PlaceServiceImpl implements PlaceService {
	@Autowired
	PlaceDao dao;

	@Override
	public PlaceDto read(String placeId) {
		try {
			return dao.read(placeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean create(PlaceDto placeData) {
		int result = 0;
		try {
			result = dao.create(placeData);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public boolean update(PlaceDto placeData) {
		int result = 0;
		try {
			result = dao.update(placeData);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result > 0;
	}
}
