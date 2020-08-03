package com.ssafy.waple.vote.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.vote.dto.PromisePlaceDto;
import com.ssafy.waple.vote.dto.PromisePlaceResponseDto;
import com.ssafy.waple.vote.dto.VoteDto;

@Mapper
public interface VoteDao {

	int create(PromisePlaceDto promisePlace);

	List<PromisePlaceResponseDto> readAll(int groupId, int promiseId, long userId);

	int createVote(VoteDto vote);
}
