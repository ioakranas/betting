package com.accepted.betting.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.accepted.betting.entity.Match;
import com.accepted.betting.model.Sport;

@SpringBootTest
public class MatchRepositoryTest {

	@Autowired
	private MatchRepository repo;

	@Test
	public void findAll() {
		// When
		Page<Match> result = repo.findAll(PageRequest.of(0, 5));
		
		// Then
		assertNotNull(result);
		assertNotNull(result.getTotalPages());
	}
	
	@Test
	public void findAllBySport() {
		// When
		Page<Match> result = repo.findAllBySport(Sport.FOOTBALL.getCode(),  PageRequest.of(0, 5));
		
		// Then
		assertNotNull(result);
		assertNotNull(result.getTotalPages());
	}
	
	@Test
	public void findByMatchDateGreaterThanEqual() {
		// Given
		LocalDate localDate =  new Date().toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
		
		// When
		Page<Match> result = repo.findByMatchDateGreaterThanEqual(localDate, PageRequest.of(0, 5));
		
		// Then
		assertNotNull(result);
		assertNotNull(result.getTotalPages());
	}
	
	@Test
	public void findBySportAndMatchDateGreaterThanEqual() {
		// Given
		LocalDate localDate =  new Date().toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
		
		// When
		Page<Match> result = repo.findBySportAndMatchDateGreaterThanEqual(Sport.FOOTBALL.getCode(), localDate, PageRequest.of(0, 5));
		
		// Then
		assertNotNull(result);
		assertNotNull(result.getTotalPages());
	}
}
