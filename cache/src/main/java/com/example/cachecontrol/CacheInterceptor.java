package com.example.cachecontrol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CacheInterceptor extends HandlerInterceptorAdapter {

	private static final CacheControl DEFAULT_CACHE_CONTROL = CacheControl.noCache().cachePrivate();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.addHeader(HttpHeaders.CACHE_CONTROL, DEFAULT_CACHE_CONTROL.getHeaderValue());
		return super.preHandle(request, response, handler);
	}
}
