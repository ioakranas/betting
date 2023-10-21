package com.accepted.betting.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.accepted.betting.MatchOddStrategyContext;
import com.accepted.betting.model.MatchOddDto;
import com.accepted.betting.model.Sport;

@SpringBootTest
public class MatchOddValidatorTest {

	@Autowired
    private MatchOddStrategyContext matchOddStrategyContext;
	
	@Test
	public void footballTestSuccess() {
		// Given
		Sport s = Sport.FOOTBALL;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("1").odd(9d).build(),
				MatchOddDto.builder().specifier("X").odd(5d).build(),
				MatchOddDto.builder().specifier("2").odd(2d).build());

		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(true, result);
	}
	
	@Test
	public void footballTestFailedForWrongSpecifier() {
		// Given
		Sport s = Sport.FOOTBALL;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("1").odd(9d).build(),
				MatchOddDto.builder().specifier("2").odd(3d).build(),
				MatchOddDto.builder().specifier("2").odd(2d).build());

		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(false, result);
	}
	
	@Test
	public void footballTestFailedLessSpecifier() {
		// Given
		Sport s = Sport.FOOTBALL;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("1").odd(9d).build(),
				MatchOddDto.builder().specifier("2").odd(3d).build());
		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(false, result);
	}
	
	@Test
	public void footballTestFailedOdd() {
		// Given
		Sport s = Sport.FOOTBALL;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("1").odd(9d).build(),
				MatchOddDto.builder().specifier("X").odd(2d).build(),
				MatchOddDto.builder().specifier("2").odd(2d).build());
		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(false, result);
	}
	
	@Test
	public void basketTestSuccess() {
		// Given
		Sport s = Sport.BASKET;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("1").odd(2d).build(),
				MatchOddDto.builder().specifier("2").odd(3d).build());

		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(true, result);
	}
	
	@Test
	public void basketTestWrongSpecifier() {
		// Given
		Sport s = Sport.BASKET;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("1").odd(2d).build(),
				MatchOddDto.builder().specifier("X").odd(3d).build());

		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(false, result);
	}
	
	@Test
	public void basketTestFailedLessSpecifier() {
		// Given
		Sport s = Sport.BASKET;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("X").odd(3d).build());

		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(false, result);
	}
	
	@Test
	public void basketTestFailedOdd() {
		// Given
		Sport s = Sport.BASKET;
		List<MatchOddDto> odds = Arrays.asList(
				MatchOddDto.builder().specifier("1").odd(2d).build(),
				MatchOddDto.builder().specifier("X").odd(1.8d).build());

		// When
		boolean result = matchOddStrategyContext.isValid(s, odds);
		
		assertEquals(false, result);
	}
}
