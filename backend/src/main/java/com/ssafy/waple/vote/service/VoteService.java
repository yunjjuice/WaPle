package com.ssafy.waple.vote.service;

import java.util.List;

import com.ssafy.waple.vote.dto.PromisePlaceDto;
import com.ssafy.waple.vote.dto.PromisePlaceResponseDto;

public interface VoteService {
	void create(PromisePlaceDto promisePlace);

	List<PromisePlaceResponseDto> readAll(int groupId, int promiseId, long userId);
}
