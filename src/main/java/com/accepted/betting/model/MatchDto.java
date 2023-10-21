package com.accepted.betting.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.accepted.betting.validator.ValidDate;
import com.accepted.betting.validator.ValidOdds;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ValidOdds
@ValidDate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {

    @ApiModelProperty(value = "Match description", example = "X-Y")
	private String description;

    @ApiModelProperty(value = "Match date", example = "2023-11-20")
    @NotNull
	private LocalDate matchDate;

    @ApiModelProperty(value = "Match time", example = "20:00")
    @JsonFormat(pattern = "HH:mm")
    @NotNull
	private LocalTime matchTime;

    @ApiModelProperty(value = "Team A name", example = "X")
    @NotNull
	private String teamA;

    @ApiModelProperty(value = "Team B name", example = "Y")
    @NotNull
	private String teamB;

    @ApiModelProperty(value = "Sport Type of Match", example = "FOOTBALL")
    @NotNull
	private Sport sport;

    @ApiModelProperty(value = "List of match odds")
    @NotNull
	private List<MatchOddDto> odds;

}
