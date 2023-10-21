package com.accepted.betting.component;

import java.util.List;

import com.accepted.betting.model.MatchOddDto;
import com.accepted.betting.model.Sport;

public interface MatchOddValidator {

	boolean isValid(List<MatchOddDto> odds);

	Sport getSport();
}
