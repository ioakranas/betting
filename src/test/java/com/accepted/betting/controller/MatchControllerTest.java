package com.accepted.betting.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.accepted.betting.exception.CheckedException;
import com.accepted.betting.model.MatchDto;
import com.accepted.betting.model.MatchOddDto;
import com.accepted.betting.model.Sport;
import com.accepted.betting.service.MatchService;

@SpringBootTest
public class MatchControllerTest {

	private static final int id = 1;
	private static final String DESCRIPTION = "X-Y";
	private static final MatchDto MATCH_SAMPLE = MatchDto.builder()
			.description(DESCRIPTION)
			.odds(Collections.emptyList())
			.build();
	private static final Pageable PAGE = PageRequest.of(0, 5);
	
	@MockBean
	private MatchService service;
	
	@Autowired
	private MatchController controller;
	
	@Test
	public void getById() throws CheckedException {
		// Given
        Mockito.when(service.getById(id)).thenReturn(MATCH_SAMPLE);

        // When
        ResponseEntity<MatchDto> response = controller.getMatchById(id);
        
        assertNotNull(response.getBody());
	}
	
	@Test
	public void getMatches() throws CheckedException {
		// Given
		Sport type = Sport.BASKET;
        Mockito.when(service.getAll(type, PAGE)).thenReturn(new PageImpl<>(Arrays.asList(MATCH_SAMPLE)));

        // When
        ResponseEntity<Page<MatchDto>> response = controller.getMatches(false, type, PAGE);
        
        assertNotNull(response.getBody());
	}
	
	@Test
	public void createMatch() {
		// Given
		MatchDto request = MatchDto.builder().teamA("X").teamB("Y").sport(Sport.FOOTBALL)
				.matchDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1))
				.matchTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalTime())
				.odds(Arrays.asList(MatchOddDto.builder().specifier("1").odd(9d).build(),
						MatchOddDto.builder().specifier("X").odd(4d).build(),
						MatchOddDto.builder().specifier("2").odd(2d).build()))
				.build();
		Mockito.when(service.createMatch(request)).thenReturn(MATCH_SAMPLE);

		// When
		ResponseEntity<MatchDto> result = controller.createMatch(request);

		// Then
		assertNotNull(result.getBody());
		assertEquals(DESCRIPTION, result.getBody().getDescription());
	}
	
	@Test
	public void updateMatch() throws CheckedException {
		// Given
		MatchDto request = MatchDto.builder().teamA("X").teamB("Y").sport(Sport.FOOTBALL)
				.matchDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
				.matchTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalTime())
				.odds(Arrays.asList(MatchOddDto.builder().specifier("1").odd(9d).build(),
						MatchOddDto.builder().specifier("X").odd(4d).build(),
						MatchOddDto.builder().specifier("2").odd(2d).build()))
				.build();
		Mockito.when(service.updateMatch(id, request)).thenReturn(MATCH_SAMPLE);

		// When
		ResponseEntity<MatchDto> result = controller.updateMatch(id, request);

		// Then
		assertNotNull(result.getBody());
		assertEquals(DESCRIPTION, result.getBody().getDescription());
	}
	
	@Test
	public void deleteMatch() throws CheckedException {
		// Given
		Mockito.when(service.deleteMatch(id)).thenReturn(true);

		// When
		ResponseEntity<Boolean> result = controller.deleteMatch(id);

		// Then
		assertEquals(true, result.getBody());
	}
	
}
