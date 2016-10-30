package com.xpown.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限控制拦截器
 * 
 */
public class BaseAuthInterceptor extends HandlerInterceptorAdapter {
	protected final static Logger logger = LoggerFactory.getLogger(BaseAuthInterceptor.class);
	protected static List<String> controllerUriFilter = Collections.synchronizedList(new ArrayList<String>());
	protected static boolean inUriFilterIsNeedCheck = true;
	private boolean returnJson = true;
	private boolean debugMode = false;
	private List<String> lstExclude = new ArrayList<String>();
	
	public BaseAuthInterceptor() {
		lstExclude.add("connection");
		lstExclude.add("cache-control");
		lstExclude.add("accept");
		lstExclude.add("x-requested-with");
		lstExclude.add("content-type");
		lstExclude.add("referer");
		lstExclude.add("user-agent");
		lstExclude.add("accept-encoding");
		lstExclude.add("accept-language");
		// lstExclude.add("cookie");
	}

	/**
	 * 显示视图前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * Controller之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("enter BaseAuthInterceptor perHandle method...");
		String requestURI1 = request.getRequestURI();
		logger.info("Visitor URI[" + requestURI1 + "]," + getHeaders(request) + getParams(request));
		
		if (debugMode == true) {
			return true;
		}
		// web请求从cookies取登录用户信息 端请求按照端的方式取登录用户信息
		String requestURI = request.getRequestURI();
		// 判断是否带有cookie
		logger.info("Visitor URI[" + requestURI + "]," + getHeaders(request) + getParams(request));
		// 删除访问后缀
		if (StringUtils.contains(requestURI, ".")) {
			requestURI = StringUtils.substringBefore(requestURI, ".");
		}
		logger.info("Visitor URI[" + requestURI + "]");
		if (controllerUriFilter.contains(requestURI)) {
			if (inUriFilterIsNeedCheck == false) {
				return true;
			}
		} else {
			if (inUriFilterIsNeedCheck == true) {
				return true;
			}
		}
		
		return true;
	}

	

	protected void writeResponse(HttpServletResponse response, int code, String message) throws IOException {
		if (returnJson == true) {
			response.setDateHeader("Expires", 0);
			// Set standard HTTP/1.1 no-cache headers.
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			// Set standard HTTP/1.0 no-cache header.
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Content-type", "application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("{\"code\":" + code + ",\"msg\":\"" + message + "\"}");
		} else {
			response.sendRedirect("/web/login");
		}
	}

	public boolean getReturnJson() {
		return returnJson;
	}

	public void setReturnJson(boolean returnJson) {
		this.returnJson = returnJson;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	/**
	 * 设定controllerUriFilter中的uri是需要登录的，其它的放行
	 */
	protected static void useUriFilterForNeedCheck() {
		inUriFilterIsNeedCheck = true;
	}

	/**
	 * 设定controllerUriFilter中的uri是不需要检查的，其它的要登录
	 */
	protected static void useUriFilterForNoCheck() {
		inUriFilterIsNeedCheck = false;
	}

	/**
	 * 获取Headers,by lijin,2014-6-11
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	private String getHeaders(HttpServletRequest httpServletRequest) {
		@SuppressWarnings("rawtypes")
		Enumeration e = httpServletRequest.getHeaderNames();

		StringBuilder builder = new StringBuilder();
		builder.append("\n\tHeaders->");
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			if (lstExclude.indexOf(name.toLowerCase()) >= 0)
				continue;
			String value = httpServletRequest.getHeader(name);
			builder.append(name + "=" + value + "\t");
		}
		return builder.toString();
	}

	/**
	 * 获取Headers,by lijin,2014-6-11
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	private String getParams(HttpServletRequest httpServletRequest) {
		@SuppressWarnings("rawtypes")
		Enumeration e = httpServletRequest.getParameterNames();
		StringBuilder builder = new StringBuilder();
		builder.append("\n\tParameters->");
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();

			String value = httpServletRequest.getParameter(name);
			builder.append(name + "=" + value + "\t");
		}
		return builder.toString();
	}
}
