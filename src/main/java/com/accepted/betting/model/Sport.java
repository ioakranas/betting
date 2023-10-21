package com.accepted.betting.model;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sport {

	FOOTBALL("1", 3),
	BASKET("2", 2);

	private String code;
	private int numberOfValidOdds;
		
	public static Sport findByCode(String code) {
		return Stream.of(Sport.values())
                .filter(s -> s.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(Sport.FOOTBALL);
	}
}
