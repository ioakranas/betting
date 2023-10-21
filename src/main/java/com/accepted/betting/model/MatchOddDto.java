package com.accepted.betting.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchOddDto {

    @ApiModelProperty(value = "Specifier for the odds", example = "1")
	@NotNull
	private String specifier;
	
    @ApiModelProperty(value = "Odds value", example = "1.5")
	private double odd;
}
