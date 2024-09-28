package com.xrosstools.xbehavior.def;

import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.Property;

public class ReferenceProperty<T> implements Property<T> {
	private String key;
	
	public ReferenceProperty(String key) {
		this.key = key;
	}

	@Override
	public T get(Blackboard blackboard) {
		return blackboard.get(key);
	}
}
