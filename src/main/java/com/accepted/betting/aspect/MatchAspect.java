package com.accepted.betting.aspect;

import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accepted.betting.MatchOddStrategyContext;
import com.accepted.betting.exception.CheckedException;
import com.accepted.betting.exception.DefaultErrorResponse;
import com.accepted.betting.model.MatchDto;
import com.accepted.betting.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class MatchAspect {

	@Autowired
	private MatchOddStrategyContext matchOddStrategyContext;

	@Before("execution(* com.accepted.betting.controller.MatchController.createMatch(..))")
	public void beforeMatchCreation(JoinPoint joinPoint) throws CheckedException {
		MatchDto request = (MatchDto) Stream.of(joinPoint.getArgs()).filter(MatchDto.class::isInstance).findFirst()
				.get();
		if (!matchOddStrategyContext.isValid(request.getSport(), request.getOdds())) {
			log.info("Invalid match's odds found for sport:{}. Odds under:{}", request.getSport(), request.getOdds());
			throw new CheckedException(DefaultErrorResponse.INVALID_MATCH_ODDS);
		}
		if (!DateUtils.isInFuture(request.getMatchDate(), request.getMatchTime())) {
			log.info("Invalid match's date found for date:{} and time:{}", request.getMatchDate(), request.getMatchTime());
			throw new CheckedException(DefaultErrorResponse.INVALID_MATCH_DATE);
		}
	}

}
