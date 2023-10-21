package com.accepted.betting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.accepted.betting.MatchOddStrategyContext;
import com.accepted.betting.entity.Match;
import com.accepted.betting.exception.CheckedException;
import com.accepted.betting.exception.DefaultErrorResponse;
import com.accepted.betting.model.MatchDto;
import com.accepted.betting.model.MatchOddDto;
import com.accepted.betting.model.Sport;
import com.accepted.betting.repository.MatchRepository;

@SpringBootTest
public class MatchServiceTest {

	private static final int id = 1;
	private static final String DESCRIPTION = "X-Y";
	private static final Match MATCH_SAMPLE = Match.builder()
			.id(id)
			.description(DESCRIPTION)
			.sport(Sport.FOOTBALL.getCode())
			.odds(Collections.emptyList())
			.build();
	private static final Pageable PAGE = PageRequest.of(0, 5);
	
	@MockBean
	private MatchRepository repository;
	
	@MockBean
	private MatchOddStrategyContext matchOddStrategyContext;
	
	@Autowired
	private MatchService service;
	
	@Test
	public void getById() throws CheckedException {
		// Given
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(MATCH_SAMPLE));

        // When
        MatchDto result = service.getById(id);
        
        // Then
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
	}
	
	@Test
	public void getByIdFailed() throws CheckedException {
		// Given
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
        // When
        Exception exception = assertThrows(CheckedException.class, () -> service.getById(id));

		// Then
		assertEquals(DefaultErrorResponse.NO_MATCH_FOUND.getMessage(), exception.getMessage());
    }
	
	@Test
	public void getAll() {
		// Given
        Mockito.when(repository.findAll(PAGE)).thenReturn(new PageImpl<>(Arrays.asList(MATCH_SAMPLE)));
	
        // When
        Page<MatchDto> result = service.getAll(null, PAGE);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
	}
	
	@Test
	public void getAllBySport() {
		// Given
        Mockito.when(repository.findAllBySport(Sport.BASKET.getCode(), PAGE)).thenReturn(new PageImpl<>(Arrays.asList(MATCH_SAMPLE)));
	
        // When
        Page<MatchDto> result = service.getAll(Sport.BASKET, PAGE);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
	}
	
	@Test
	public void getUpcoming() {
		// Given
        Mockito.when(repository.findByMatchDateGreaterThanEqual(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(Arrays.asList(MATCH_SAMPLE)));
	
        // When
        Page<MatchDto> result = service.getUpcoming(null, PAGE);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
	}
	
	@Test
	public void getUpcomingBySport() {
		// Given
        Mockito.when(repository.findBySportAndMatchDateGreaterThanEqual(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(Arrays.asList(MATCH_SAMPLE)));
	
        // When
        Page<MatchDto> result = service.getUpcoming(Sport.BASKET, PAGE);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
	}
	
	@Test
	public void createMatch() {
		// Given
		MatchDto request = MatchDto.builder()
				.teamA("X")
				.teamB("Y")
				.sport(Sport.FOOTBALL)
				.matchDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
				.matchTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalTime())
				.odds(Arrays.asList(
						MatchOddDto.builder().specifier("1").odd(9d).build(),
						MatchOddDto.builder().specifier("X").odd(4d).build(),
						MatchOddDto.builder().specifier("2").odd(2d).build()))
				.build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(MATCH_SAMPLE);
        
        // When
        MatchDto result = service.createMatch(request);
        
        // Then
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
	}
	
	@Test
	public void updateMatch() throws CheckedException {
		// Given
		MatchDto request = MatchDto.builder()
				.teamA("X")
				.teamB("Y")
				.matchDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(2))
				.matchTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalTime())
				.odds(Arrays.asList(MatchOddDto.builder().specifier("2").odd(2d).build()))
				.sport(Sport.BASKET)
				.build();
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(MATCH_SAMPLE));
        Mockito.when(repository.save(Mockito.any())).thenReturn(MATCH_SAMPLE);
        Mockito.when(matchOddStrategyContext.isValid(Mockito.any(), Mockito.any())).thenReturn(true);

        // When
        MatchDto result = service.updateMatch(id, request);
		
        // Then
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
	}
	
	@Test
	public void updateMatchFailed() {
		// Given
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
        // When
        Exception exception = assertThrows(CheckedException.class, () -> service.updateMatch(id, null));

		// Then
		assertEquals(DefaultErrorResponse.NO_MATCH_FOUND.getMessage(), exception.getMessage());
	}
	
	@Test
	public void deleteMatch() throws CheckedException {
		// Given
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(MATCH_SAMPLE));
        
        // When 
		boolean result = service.deleteMatch(id);
		
		// Then
		assertEquals(true, result);
	}
	
	@Test
	public void deleteMatchFailed() throws CheckedException {
		// Given
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
        // When
        Exception exception = assertThrows(CheckedException.class, () -> service.deleteMatch(id));

		// Then
		assertEquals(DefaultErrorResponse.NO_MATCH_FOUND.getMessage(), exception.getMessage());
    }
}
