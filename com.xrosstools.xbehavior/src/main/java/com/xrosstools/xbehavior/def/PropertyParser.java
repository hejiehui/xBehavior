package com.xrosstools.xbehavior.def;

import com.xrosstools.xbehavior.Property;

public class PropertyParser {
	public Property<Integer> parseInteger(String expression) {
		try {
			return ValueProperty.of(Integer.parseInt(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Integer>(expression);
		}
	}

	public Property<Long> parseLong(String expression) {
		try {
			return ValueProperty.of(Long.parseLong(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Long>(expression);
		}
	}

	public Property<Boolean> parseBoolean(String expression) {
		if("true".equalsIgnoreCase(expression))
			return ValueProperty.of(true);
		if("false".equalsIgnoreCase(expression))
			return ValueProperty.of(false);
		return new ReferenceProperty<Boolean>(expression);
	}

	public Property<Float> parseFloat(String expression) {
		try {
			return ValueProperty.of(Float.parseFloat(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Float>(expression);
		}
	}

	public Property<Double> parseDouble(String expression) {
		try {
			return ValueProperty.of(Double.parseDouble(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Double>(expression);
		}
	}
}
