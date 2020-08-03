package com.ssafy.waple.promise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.promise.dto.PromiseDto;

@Mapper
public interface PromiseDao {
	void create(PromiseDto promise);

	List<PromiseDto> readAll(long userId);

	int update(PromiseDto promise);
}
