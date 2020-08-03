package com.ssafy.waple.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.review.dao.ReviewDao;
import com.ssafy.waple.review.dto.ReviewDto;
import com.ssafy.waple.review.error.NameNotEmptyException;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewDao dao;

	@Override
	public void create(ReviewDto dto) {
		// 유효성 검증 : 유저 찾기, 그룹 찾기, 장소 찾기
		if(dto.getTitle() == null || dto.getTitle() == "") {
			throw new NameNotEmptyException();
		}
		dao.create(dto);
	}

}
