package com.xrosstools.xbehavior.def;

import com.xrosstools.xbehavior.Property;
import com.xrosstools.xbehavior.RawValueProperty;
import com.xrosstools.xbehavior.ReferenceProperty;

public class PropertyParser {
	public Property<Integer> parseInteger(String expression) {
		try {
			return new RawValueProperty<Integer>(Integer.parseInt(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Integer>(expression);
		}
	}

	public Property<Long> parseLong(String expression) {
		try {
			return new RawValueProperty<Long>(Long.parseLong(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Long>(expression);
		}
	}

	public Property<Boolean> parseBoolean(String expression) {
		if("true".equalsIgnoreCase(expression))
			return new RawValueProperty<Boolean>(true);
		if("false".equalsIgnoreCase(expression))
			return new RawValueProperty<Boolean>(false);
		return new ReferenceProperty<Boolean>(expression);
	}

	public Property<Float> parseFloat(String expression) {
		try {
			return new RawValueProperty<Float>(Float.parseFloat(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Float>(expression);
		}
	}

	public Property<Double> parseDouble(String expression) {
		try {
			return new RawValueProperty<Double>(Double.parseDouble(expression));
		} catch (NumberFormatException e) {
			return new ReferenceProperty<Double>(expression);
		}
	}
}
