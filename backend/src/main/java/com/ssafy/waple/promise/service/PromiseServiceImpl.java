package com.ssafy.waple.promise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.promise.dao.PromiseDao;
import com.ssafy.waple.promise.dto.PromiseDto;

@Service
public class PromiseServiceImpl implements PromiseService {
	@Autowired
	PromiseDao dao;

	@Override
	public void create(PromiseDto promise) {
		dao.create(promise);
		//TODO 발생할 수 있는 에러 -> GroupId가 없음, 약속 날짜가 현재보다 이전, 약속 이름이 null임
	}

	@Override
	public List<PromiseDto> readAll(long userId) {
		return dao.readAll(userId);
	}
}
