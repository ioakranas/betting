package com.accepted.betting.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accepted.betting.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

	Page<Match> findAll(Pageable page);
	
	Page<Match> findAllBySport(String sport, Pageable page);
	
	Page<Match> findByMatchDateGreaterThanEqual(LocalDate date, Pageable page);
	
	Page<Match> findBySportAndMatchDateGreaterThanEqual(String sport, LocalDate date, Pageable page);

	
}
