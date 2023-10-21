package com.accepted.betting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Specifier {

	WIN("1", true),
	DRAW("X", false),
	LOSE("2", true);
	
	private String code;
	private boolean isBasketValid;

}
