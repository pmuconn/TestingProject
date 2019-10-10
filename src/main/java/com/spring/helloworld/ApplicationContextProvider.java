package com.spring.helloworld;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author pmorano
 *
 *	This class was created so that a POJO has the ability to get the spring
 *  application context without specifying a context string.
 *  
 */

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	private static ApplicationContext ctx = null;

	/**
	 * @return The Spring application context.
	 */
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
}
