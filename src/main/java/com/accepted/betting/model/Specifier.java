package com.accepted.betting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Specifier {

	WIN("1"),
	DRAW("X"),
	LOSE("2");
	
	private String code;

}
