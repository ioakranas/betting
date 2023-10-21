package com.accepted.betting.service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.accepted.betting.exception.CheckedException;
import com.accepted.betting.exception.DefaultErrorResponse;
import com.accepted.betting.service.RateLimitService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucket;
import io.github.bucket4j.local.LocalBucketBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {

	@Value("${rate.limit.requests.max}")
	private int maxRequests;

	@Value("${rate.limit.per.minutes}")
	private int perMinutes;

	private final Map<String, LocalBucket> buckets = new ConcurrentHashMap<>();

	@Override
	public void handleRequest(String ipAddress) throws CheckedException {
		Bandwidth limit = Bandwidth.classic(maxRequests,
				Refill.intervally(maxRequests, Duration.ofMinutes(perMinutes)));

		LocalBucket bucket = buckets.computeIfAbsent(ipAddress, key -> {
			return new LocalBucketBuilder().addLimit(limit).build();
		});

		if (!bucket.tryConsume(1)) {
			log.info("Too many requests for ip address:{}", ipAddress);
			throw new CheckedException(DefaultErrorResponse.TOO_MANY_REQUESTS);
		}
	}

}
