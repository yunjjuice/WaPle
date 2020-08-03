package com.ssafy.waple.vote.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.vote.dto.PromisePlaceDto;

@Mapper
public interface VoteDao {

	int create(PromisePlaceDto promisePlace);
}
