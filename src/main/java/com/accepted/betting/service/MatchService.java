package com.accepted.betting.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.accepted.betting.model.MatchDto;

public interface MatchService {

	MatchDto getById(int id);
	
	Page<MatchDto> getAll(Pageable page);
	
	Page<MatchDto> getUpcoming(Pageable page);
	
	MatchDto createMatch(MatchDto request);
	
	MatchDto updateMatch(int id, MatchDto request);
	
	boolean deleteMatch(int id);
}
