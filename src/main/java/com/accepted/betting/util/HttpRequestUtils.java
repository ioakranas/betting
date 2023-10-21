package com.accepted.betting.util;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtils {

	public static final String LOCALHOST = "localhost";

	public static final String REMOTE_ADDR = "REMOTE_ADDR";

	public static final String HTTP_X_FORWARDED_FOR = "x-forwarded-for";

	public static final String LAN_PREFIX = "10";

	public static final String HTTP_X_REAL_IP = "x-real-ip";

	public static String populateIp(HttpServletRequest request) {
		return Optional.ofNullable(request).map(r -> Optional.ofNullable(r.getHeader(HTTP_X_FORWARDED_FOR)).map(t -> t.split(",")[0].trim())
                .orElse(Optional.ofNullable(request.getHeader(HTTP_X_REAL_IP)).filter(i -> !i.startsWith(LAN_PREFIX))
                        .orElse(Optional.ofNullable(request.getHeader(REMOTE_ADDR)).map(t -> t).orElse(LOCALHOST)))).orElse(LOCALHOST);
	}
}
