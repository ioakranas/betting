package com.accepted.betting.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.accepted.betting.model.MatchDto;
import com.accepted.betting.model.Sport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"match\"")
@Entity
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	private LocalDate matchDate;

	private LocalTime matchTime;

	@Column(name = "team_a")
	private String teamA;

	@Column(name = "team_b")
	private String teamB;

	private String sport;

	@OneToMany(mappedBy = "match", fetch = FetchType.EAGER,  cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<MatchOdd> odds;

	public MatchDto toDto() {
		return MatchDto.builder()
				.description(description)
				.matchDate(matchDate)
				.matchTime(matchTime)
				.teamA(teamA)
				.teamB(teamB)
				.sport(Sport.findByCode(sport))
				.odds(odds.stream().map(MatchOdd::toDto).collect(Collectors.toList()))
				.build();
	}
}
