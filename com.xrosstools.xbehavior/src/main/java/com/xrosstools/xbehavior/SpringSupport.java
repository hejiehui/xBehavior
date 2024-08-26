package com.xrosstools.xbehavior;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringSupport implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    public static void enable(ApplicationContext applicationContext) {
        if (SpringSupport.applicationContext == null) {
            SpringSupport.applicationContext = applicationContext;
        }
    }
  
    public static Object getBean(String className) {
    	if(applicationContext == null)
    		return null;

    	try {
    		return applicationContext.getBean(Class.forName(className));
    	}catch(Exception e) {
    		return new IllegalArgumentException(className, e);
    	}
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		if(SpringSupport.applicationContext == null)
			SpringSupport.applicationContext = applicationContext;
	}  
}