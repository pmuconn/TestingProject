package com.boot;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

public class BaseProperties {
	private final Logger logger = LoggerFactory.getLogger(BaseProperties.class.getName());

	/**
	 * @return false if any properties are null (empty values are ok), true otherwise.
	 */
	public boolean isDataPopulated() {
		boolean result = true;
		Class<?> clazz = ClassUtils.getUserClass(this);
		logger.info("*** " + clazz.getName());
        for (Field f : clazz.getDeclaredFields()) {
            try {
            	f.setAccessible(true);
            	Object value = f.get(this);
				logger.info(clazz.getName() + "." + f.getName() + " = " + value);
				if (f.get(this) == null) {
					logger.error(clazz.getName() + "." + f.getName() + " = null.");
				    result = false;
				}
			} catch (Exception e) {
				result = false;
			}
        }
        if (result == false) {
    		logger.error(clazz.getName() + " has null values.");
        }
        return result;            
    }    
}
