package com.accepted.betting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accepted.betting.component.MatchOddValidator;
import com.accepted.betting.model.MatchOddDto;
import com.accepted.betting.model.Sport;

@Component
public class MatchOddStrategyContext {

	@Autowired
	private Map<Sport, MatchOddValidator> availableMatchOddValidatorBySport;
	
	public boolean isValid(Sport sport, List<MatchOddDto> odds) {
		return availableMatchOddValidatorBySport.get(sport).isValid(odds);
	}
	
}
