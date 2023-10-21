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

import com.accepted.betting.exception.CheckedException;
import com.accepted.betting.model.MatchDto;
import com.accepted.betting.model.Sport;
import com.accepted.betting.service.MatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/match")
public class MatchController {

	@Autowired
	private MatchService service;
	
	@GetMapping("{id}")
	public ResponseEntity<MatchDto> getMatchById(@PathVariable() int id) throws CheckedException {
		log.info("Will fetch match with id:{}", id);
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	@GetMapping()
	public ResponseEntity<Page<MatchDto>> getMatches(@RequestParam(value = "inFuture", defaultValue = "true") boolean inFuture, 
			@RequestParam(value = "type", required = false) Sport type, Pageable page) {
		log.info("Will fetch matches will param 'inFuture':{}", inFuture);
		return ResponseEntity.status(HttpStatus.OK).body(inFuture ? service.getUpcoming(type, page) : service.getAll(type, page)); 
	}
	
	@PostMapping()
	public ResponseEntity<MatchDto> createMatch(@Valid @RequestBody MatchDto request) {
		log.info("Will create match from request under:{}", request);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createMatch(request));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<MatchDto> updateMatch(@PathVariable() int id, @RequestBody MatchDto request) throws CheckedException {
		log.info("Will update match with id:{} from request under:{}", id, request);
		return ResponseEntity.status(HttpStatus.OK).body(service.updateMatch(id, request));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> deleteMatch(@PathVariable() int id) throws CheckedException {
		log.info("Will delete match with id:{}", id);
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteMatch(id));
	}
}
