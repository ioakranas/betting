package com.accepted.betting.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {

	private String desctiption;

	private LocalDate matchDate;

	private LocalTime matchTime;

	private String teamA;

	private String teamB;

	private Sport sport;

	private List<MatchOddDto> odds;

}
