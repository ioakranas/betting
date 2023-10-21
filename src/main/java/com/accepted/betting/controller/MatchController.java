package com.accepted.betting.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accepted.betting.model.MatchDto;
import com.accepted.betting.service.MatchService;

@RestController
@RequestMapping("api/match")
public class MatchController {

	@Autowired
	private MatchService service;
	
	@GetMapping("{id}")
	public ResponseEntity<MatchDto> getMatchById(@PathVariable() int id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	@GetMapping()
	public ResponseEntity<Page<MatchDto>> getMatches(@RequestParam(value = "inFuture", defaultValue = "true") boolean inFuture, Pageable page) {
		return ResponseEntity.status(HttpStatus.OK).body(inFuture ? service.getUpcoming(page) : service.getAll(page)); 
	}
	
	@PostMapping()
	public ResponseEntity<MatchDto> createMatch(@Valid @RequestBody MatchDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createMatch(request));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<MatchDto> updateMatch(@PathVariable() int id, @RequestBody MatchDto request) {
		return ResponseEntity.status(HttpStatus.OK).body(service.updateMatch(id, request));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> deleteMatch(@PathVariable() int id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteMatch(id));
	}
}
