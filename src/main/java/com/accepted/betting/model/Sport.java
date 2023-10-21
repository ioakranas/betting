package com.accepted.betting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sport {

	FOOTBALL("1"),
	BASKET("2");

	private String code;
}
