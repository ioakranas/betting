package com.accepted.betting.component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.accepted.betting.model.MatchOddDto;
import com.accepted.betting.model.Specifier;
import com.accepted.betting.model.Sport;

@Component
public class FootballOddValidator implements MatchOddValidator {
	
	@Value("${match.odd.rake}")
	private double rake;

	@Override
	public boolean isValid(List<MatchOddDto> odds) {
		if (odds.size() != Sport.FOOTBALL.getNumberOfValidOdds()) {
			return false;
		}
		return checkSpecifiers(odds) && checkOdds(odds);
	}
	
	private boolean checkOdds(List<MatchOddDto> odds) {
		return odds.stream().mapToDouble(o -> 100/o.getOdd()).sum() <= 100 - rake;
	}
	
	private boolean checkSpecifiers(List<MatchOddDto> odds) {
		return Arrays.asList(Specifier.values()).stream().map(s -> countOddType(s.getCode(), odds) == 1).allMatch(Boolean::booleanValue);
	}
	
	private int countOddType(String specifier, List<MatchOddDto> odds) {
		return (int) odds.stream().filter(o -> Objects.equals(specifier, o.getSpecifier())).count();
	}

	@Override
	public Sport getSport() {
		return Sport.FOOTBALL;
	}

}
