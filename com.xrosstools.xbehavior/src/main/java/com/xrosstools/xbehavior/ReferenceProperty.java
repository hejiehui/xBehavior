package com.xrosstools.xbehavior;

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
