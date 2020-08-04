package com.ssafy.waple.promise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.error.exception.InvalidValueException;
import com.ssafy.waple.group.exception.GroupNotFoundException;
import com.ssafy.waple.promise.dao.PromiseDao;
import com.ssafy.waple.promise.dto.PromiseDto;
import com.ssafy.waple.promise.exception.PromiseNotFoundException;

@Service
public class PromiseServiceImpl implements PromiseService {
	private static final String SELECT_KEY_RETURNED_NO_DATA = "SelectKey returned no data.";
	@Autowired
	PromiseDao dao;

	@Override
	public void create(PromiseDto promise) {
		if (promise.getPromiseDate().getTime() < System.currentTimeMillis()) {
			throw new InvalidValueException("Promise date must be later than current date.");
		}
		if (promise.getTitle() == null || promise.getTitle().equals("")) {
			throw new InvalidValueException("Promise name cannot be null");
		}
		try {
			dao.create(promise);
		} catch (DataAccessException e) {
			if (e.getMessage().contains(SELECT_KEY_RETURNED_NO_DATA)) { //Group이 없을 때 발생
				throw new GroupNotFoundException(promise.getGroupId());
			}
			throw e;
		}
	}

	@Override
	public List<PromiseDto> readAll(long userId) {
		return dao.readAll(userId);
	}

	@Override
	public void update(PromiseDto promise) {
		if (promise.getPromiseDate().getTime() < System.currentTimeMillis()) {
			throw new InvalidValueException("Promise date must be later than current date.");
		}
		if (promise.getTitle() == null || promise.getTitle().equals("")) {
			throw new InvalidValueException("Promise name cannot be null");
		}
		if (dao.update(promise) < 1) {
			throw new PromiseNotFoundException(promise.getGroupId(), promise.getPromiseId());
		}
	}

	@Override
	public void delete(int groupId, int promiseId) {
		if (dao.delete(groupId, promiseId) < 1) {
			throw new PromiseNotFoundException(groupId, promiseId);
		}
	}
}
