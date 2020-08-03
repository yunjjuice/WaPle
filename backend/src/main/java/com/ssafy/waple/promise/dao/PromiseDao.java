package com.ssafy.waple.promise.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.promise.dto.PromiseDto;

@Mapper
public interface PromiseDao {
	void create(PromiseDto promise);
}
