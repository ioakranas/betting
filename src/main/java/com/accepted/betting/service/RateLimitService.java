package com.accepted.betting.service;

import com.accepted.betting.exception.CheckedException;

public interface RateLimitService {
	
	void handleRequest(String ipAddress) throws CheckedException;

}
