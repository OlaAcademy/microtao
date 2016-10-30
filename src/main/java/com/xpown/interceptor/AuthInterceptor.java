package com.xpown.interceptor;

public class AuthInterceptor extends BaseAuthInterceptor {
	static {
		useUriFilterForNoCheck();
		controllerUriFilter.add("");
		
		
	}
}
