package com.accepted.betting;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.accepted.betting.entity.Match;
import com.accepted.betting.repository.MatchRepository;

@SpringBootTest
public class MatchRepositoryTest {

	@Autowired
	private MatchRepository repo;

	@Test
	public void findAll() {
		// When
		List<Match> match = repo.findAll();
		
		// Then
		assertNotNull(match);
	}
}
