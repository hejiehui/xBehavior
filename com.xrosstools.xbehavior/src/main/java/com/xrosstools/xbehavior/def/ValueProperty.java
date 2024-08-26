package com.xrosstools.xbehavior.def;

import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.Property;

public class ValueProperty<T> implements Property<T>{
	private T value;
	public ValueProperty(T value) {
		this.value = value;
	}

	@Override
	public T get(Blackboard blackboard) {
		return value;
	}
	
	public static <T> ValueProperty<T> of(T value) {
		return new ValueProperty<T>(value);
	}
}
