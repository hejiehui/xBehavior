package com.xrosstools.xbehavior;

public class RawValueProperty<T> implements Property<T> {
	private T value;
	
	public RawValueProperty(T value) {
		this.value = value;
	}

	@Override
	public T get(Blackboard blackboard) {
		return value;
	}
}
