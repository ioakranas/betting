package com.accepted.betting.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.accepted.betting.model.MatchOddDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MatchOdd {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Match match;
	
	private String specifier;
	
	private double odd;
	
	public MatchOddDto toDto() {
		return MatchOddDto.builder()
				.specifier(specifier)
				.odd(odd)
				.build();
	}
}
