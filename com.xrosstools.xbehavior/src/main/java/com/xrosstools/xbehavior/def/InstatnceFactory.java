package com.xrosstools.xbehavior.def;

import com.xrosstools.xbehavior.SpringSupport;

public class InstatnceFactory implements PropertyConstants {
	private static boolean enableSpring;

	static {
		try {
			Class.forName("org.springframework.context.ApplicationContext");
			enableSpring = true;
		} catch (ClassNotFoundException e) {
			enableSpring = false;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String className) {
		if (isEmpty(className))
			throw new IllegalArgumentException("class or method name is empty.");

		Object behavior = null;
		if (enableSpring)
			behavior = SpringSupport.getBean(className);

		if (behavior == null)
			try {
				behavior = Class.forName(className).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException(className, e);
			}

		return (T) behavior;
	}

	private static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
}
