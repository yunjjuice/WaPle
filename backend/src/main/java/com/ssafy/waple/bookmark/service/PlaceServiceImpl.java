package com.ssafy.waple.bookmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.bookmark.dao.PlaceDao;
import com.ssafy.waple.bookmark.dto.PlaceDto;
import com.ssafy.waple.bookmark.exception.PlaceNotFoundException;
import com.ssafy.waple.bookmark.exception.PrimaryKeyNullException;

@Service
public class PlaceServiceImpl implements PlaceService {

	private static final String PRIMARY_KEY_NOT_NULL_MSG = "'PLACE_ID' doesn't have a default value";

	@Autowired
	PlaceDao dao;

	@Override
	public void create(PlaceDto placeData) {
		try{
			dao.create(placeData);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(PRIMARY_KEY_NOT_NULL_MSG)) {
				throw new PrimaryKeyNullException("Place ID is Null");
			}
			throw e;
		}
	}

	@Override
	public PlaceDto read(String placeId) {
		return dao.read(placeId);
	}

	@Override
	public void update(PlaceDto placeData) {
		if(dao.update(placeData) < 1) {
			throw new PlaceNotFoundException(placeData.getPlaceId());
		}
	}
}
