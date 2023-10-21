package com.accepted.betting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.accepted.betting.model.MatchDto;
import com.accepted.betting.repository.MatchRepository;
import com.accepted.betting.service.MatchService;

@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	private MatchRepository repository;
	
	@Override
	public MatchDto getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MatchDto> getAll(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MatchDto> getUpcoming(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MatchDto createMatch(MatchDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MatchDto updateMatch(int id, MatchDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteMatch(int id) {
		// TODO Auto-generated method stub
		return true;
	}

}
