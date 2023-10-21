package com.accepted.betting.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.accepted.betting.exception.CheckedException;
import com.accepted.betting.model.MatchDto;
import com.accepted.betting.model.Sport;

public interface MatchService {

	MatchDto getById(int id) throws CheckedException; 
	
	Page<MatchDto> getAll(Sport type, Pageable page);
	
	Page<MatchDto> getUpcoming(Sport type, Pageable page);
	
	MatchDto createMatch(MatchDto request);
	
	MatchDto updateMatch(int id, MatchDto request) throws CheckedException;
	
	boolean deleteMatch(int id) throws CheckedException;
}
