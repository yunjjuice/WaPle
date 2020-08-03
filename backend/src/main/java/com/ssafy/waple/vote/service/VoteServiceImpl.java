package com.ssafy.waple.vote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.vote.dao.VoteDao;
import com.ssafy.waple.vote.dto.PromisePlaceDto;

@Service
public class VoteServiceImpl implements VoteService {
	@Autowired
	VoteDao dao;

	@Override
	public void create(PromisePlaceDto promisePlace) {
		dao.create(promisePlace);
	}
}
