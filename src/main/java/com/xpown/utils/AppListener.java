package com.xpown.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class AppListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		sc.setAttribute("wxappid", ConfigUtil.getStringValue("wxappid"));
		sc.setAttribute("domain", ConfigUtil.getStringValue("domain"));
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}
