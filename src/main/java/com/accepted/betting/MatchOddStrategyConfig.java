package com.accepted.betting;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.accepted.betting.component.MatchOddValidator;
import com.accepted.betting.model.Sport;

@Component
public class MatchOddStrategyConfig {

	@Autowired
	private List<MatchOddValidator> availableMatchOddStrategies;
	
	@Bean
	public Map<Sport, MatchOddValidator> availableMatchOddValidatorBySport() {
		Map<Sport, MatchOddValidator> availableMatchOddValidatorBySport = new EnumMap<>(Sport.class);
		availableMatchOddStrategies.forEach(s -> availableMatchOddValidatorBySport.put(s.getSport(), s));
		return availableMatchOddValidatorBySport;
	}
	
}
