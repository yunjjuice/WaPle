package com.ssafy.waple.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.vote.dao.VoteDao;
import com.ssafy.waple.vote.dto.PromisePlaceDto;
import com.ssafy.waple.vote.dto.PromisePlaceResponseDto;
import com.ssafy.waple.vote.dto.VoteDto;

@Service
public class VoteServiceImpl implements VoteService {
	@Autowired
	VoteDao dao;

	@Override
	public void create(PromisePlaceDto promisePlace) {
		dao.create(promisePlace);
	}

	@Override
	public List<PromisePlaceResponseDto> readAll(int groupId, int promiseId, long userId) {
		return dao.readAll(groupId, promiseId, userId);
	}

	@Override
	public void createVote(VoteDto vote) {
		dao.createVote(vote);
	}

	@Override
	public void deleteVote(int groupId, int promiseId, String placeId, long userId) {
		dao.deleteVote(groupId, promiseId, placeId, userId);
	}
}
