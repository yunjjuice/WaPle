package com.ssafy.waple.promise.service;

import java.util.List;

import com.ssafy.waple.promise.dto.PromiseDto;

public interface PromiseService {
	void create(PromiseDto promise);

	List<PromiseDto> readAll(long userId);

	void update(PromiseDto promise);
}
