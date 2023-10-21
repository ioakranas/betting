package com.accepted.betting.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.accepted.betting.MatchOddStrategyContext;
import com.accepted.betting.model.MatchDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OddsValidator implements ConstraintValidator<ValidOdds, MatchDto> {

	@Autowired
	private MatchOddStrategyContext matchOddStrategyContext;
	
	@Override
    public void initialize(ValidOdds constraintAnnotation) {}
	
	@Override
	public boolean isValid(MatchDto request, ConstraintValidatorContext context) {
		if (Objects.isNull(request)) {
			return false;
		}
		if (!matchOddStrategyContext.isValid(request.getSport(), request.getOdds())) {
			log.info("Invalid match's odds found for sport:{}. Odds under:{}", request.getSport(), request.getOdds());
			return false;
		}
		return true;
	}

}
