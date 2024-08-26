package com.xrosstools.xbehavior.def;

import com.xrosstools.xbehavior.Property;

public interface PropertyParser {
	public Property<String> parseString(String expression);

	public Property<Integer> parseInteger(String expression);

	public Property<Long> parseLong(String expression);

	public Property<Boolean> parseBoolean(String expression);

	public Property<Float> parseFloat(String expression);

	public Property<Double> parseDouble(String expression);
}
