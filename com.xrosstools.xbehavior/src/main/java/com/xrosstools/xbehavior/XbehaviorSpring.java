package com.xrosstools.xbehavior;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class XbehaviorSpring implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    public static void enable(ApplicationContext applicationContext) {
        if (XbehaviorSpring.applicationContext == null) {
            XbehaviorSpring.applicationContext = applicationContext;
        }
    }
  
    public static Object getBean(String className) {
    	if(applicationContext == null)
    		return null;

    	try {
    		return applicationContext.getBean(Class.forName(className));
    	}catch(Exception e) {
    		return null;
    	}
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		if(XbehaviorSpring.applicationContext == null)
			XbehaviorSpring.applicationContext = applicationContext;
	}  
}