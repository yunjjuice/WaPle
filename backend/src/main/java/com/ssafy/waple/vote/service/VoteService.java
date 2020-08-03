package com.ssafy.waple.vote.service;

import java.util.List;

import com.ssafy.waple.vote.dto.PromisePlaceDto;
import com.ssafy.waple.vote.dto.PromisePlaceResponseDto;
import com.ssafy.waple.vote.dto.VoteDto;

public interface VoteService {
	void create(PromisePlaceDto promisePlace);

	List<PromisePlaceResponseDto> readAll(int groupId, int promiseId, long userId);

	void createVote(VoteDto vote);
}
