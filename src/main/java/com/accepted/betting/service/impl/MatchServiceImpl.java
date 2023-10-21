package com.accepted.betting.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.accepted.betting.entity.Match;
import com.accepted.betting.entity.MatchOdd;
import com.accepted.betting.exception.CheckedException;
import com.accepted.betting.exception.DefaultErrorResponse;
import com.accepted.betting.model.MatchDto;
import com.accepted.betting.model.Sport;
import com.accepted.betting.repository.MatchRepository;
import com.accepted.betting.service.MatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	private MatchRepository repository;
	
	@Transactional(readOnly = true)
	@Override
	public MatchDto getById(int id) throws CheckedException {
		Optional<Match> match = repository.findById(id);
		if (!match.isPresent()) {
			log.info("No match found for id:{}", id);
			throw new CheckedException(DefaultErrorResponse.NO_MATCH_FOUND);
		}
		return match.get().toDto();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<MatchDto> getAll(Sport type, Pageable page) {
		return (Objects.nonNull(type) ? repository.findAllBySport(type.getCode(), page) : repository.findAll(page))
				.map(Match::toDto);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<MatchDto> getUpcoming(Sport type, Pageable page) {
		LocalDate date = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return (Objects.nonNull(type) ? repository.findBySportAndMatchDateGreaterThanEqual(type.getCode(), date, page)
				: repository.findByMatchDateGreaterThanEqual(date, page)).map(Match::toDto);
	}

	@Transactional
	@Override
	public MatchDto createMatch(MatchDto request) {
		Match toSave = Match.builder()
				.description(request.getTeamA() + "-" + request.getTeamB())
				.teamA(request.getTeamA())
				.teamB(request.getTeamB())
				.sport(request.getSport().getCode())
				.matchDate(request.getMatchDate())
				.matchTime(request.getMatchTime())
				.build();
		toSave.setOdds(request.getOdds().stream()
				.map(o -> MatchOdd.builder()
						.match(toSave)
						.odd(o.getOdd())
						.specifier(o.getSpecifier())
						.build())
				.collect(Collectors.toList()));
		return repository.save(toSave).toDto();
	}

	@Transactional
	@Override
	public MatchDto updateMatch(int id, MatchDto request) throws CheckedException {
		Optional<Match> optionalMatch = repository.findById(id);
		if (!optionalMatch.isPresent()) {
			log.info("No match found for id:{}", id);
			throw new CheckedException(DefaultErrorResponse.NO_MATCH_FOUND);
		}
		Match match = optionalMatch.get();
		Optional.ofNullable(request.getTeamA()).ifPresent(t -> {
			match.setTeamA(request.getTeamA());
			match.setDescription(request.getTeamA() + match.getDescription().substring(match.getDescription().indexOf("-")));
		});
		Optional.ofNullable(request.getTeamB()).ifPresent(t -> {
			match.setTeamA(request.getTeamB());
			match.setDescription(match.getDescription().split("-")[0]+"-"+request.getTeamB());
		});
		Optional.ofNullable(request.getMatchDate()).ifPresent(match::setMatchDate);
		Optional.ofNullable(request.getMatchTime()).ifPresent(match::setMatchTime);
		if (!CollectionUtils.isEmpty(request.getOdds())) {
			request.getOdds().stream().forEach(od -> {
				Optional<MatchOdd> odd = match.getOdds().stream().filter(o -> Objects.equals(od.getSpecifier(), o.getSpecifier())).findFirst();
				if (odd.isPresent()) {
					odd.get().setOdd(od.getOdd());
				}
			});
		}
		return repository.save(match).toDto();
	}

	@Transactional
	@Override
	public boolean deleteMatch(int id) throws CheckedException {
		Optional<Match> match = repository.findById(id);
		if (!match.isPresent()) {
			log.info("No match found for id:{}", id);
			throw new CheckedException(DefaultErrorResponse.NO_MATCH_FOUND);
		}
		repository.delete(match.get());
		return true;
	}

}
