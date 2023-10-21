package com.accepted.betting.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.accepted.betting.model.MatchDto;
import com.accepted.betting.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchDateValidator implements ConstraintValidator<ValidDate, MatchDto> {

	@Override
    public void initialize(ValidDate constraintAnnotation) {}
	
	@Override
	public boolean isValid(MatchDto request, ConstraintValidatorContext context) {
		if (Objects.isNull(request)) {
			return false;
		}
		if (!DateUtils.isInFuture(request.getMatchDate(), request.getMatchTime())) {
			log.info("Invalid match's date found for date:{} and time:{}", request.getMatchDate(), request.getMatchTime());
			return false;
		}
		return true;
	}

}
