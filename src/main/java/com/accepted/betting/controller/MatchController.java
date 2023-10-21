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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "Match API")
@RestController
@RequestMapping("api/match")
public class MatchController {

	@Autowired
	private MatchService service;
	
	@ApiOperation("Get a match by Id")
	@GetMapping("{id}")
	public ResponseEntity<MatchDto> getMatchById(
			@ApiParam(value = "The Id of the match", example = "1") @PathVariable() int id) throws CheckedException {
		log.info("Will fetch match with id:{}", id);
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	@ApiOperation("Get matches")
	@GetMapping()
	public ResponseEntity<Page<MatchDto>> getMatches(
			@ApiParam(value = "To get upcoming or all", example = "true") @RequestParam(value = "inFuture", defaultValue = "true") boolean inFuture, 
			@ApiParam(value = "Get by Type", example = "FOOTBALL") @RequestParam(value = "type", required = false) Sport type, 
			Pageable page) {
		log.info("Will fetch matches will param 'inFuture':{}", inFuture);
		return ResponseEntity.status(HttpStatus.OK).body(inFuture ? service.getUpcoming(type, page) : service.getAll(type, page)); 
	}
	
	@ApiOperation("Create match")
	@PostMapping()
	public ResponseEntity<MatchDto> createMatch(@Valid @RequestBody MatchDto request) {
		log.info("Will create match from request under:{}", request);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createMatch(request));
	}
	
	@ApiOperation("Update specific match")
	@PutMapping("{id}")
	public ResponseEntity<MatchDto> updateMatch(
			@ApiParam(value = "The Id of the match", example = "1") @PathVariable() int id, @RequestBody MatchDto request) throws CheckedException {
		log.info("Will update match with id:{} from request under:{}", id, request);
		return ResponseEntity.status(HttpStatus.OK).body(service.updateMatch(id, request));
	}
	
	@ApiOperation("Delete specific match")
	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> deleteMatch(
			@ApiParam(value = "The Id of the match", example = "1") @PathVariable() int id) throws CheckedException {
		log.info("Will delete match with id:{}", id);
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteMatch(id));
	}
}
