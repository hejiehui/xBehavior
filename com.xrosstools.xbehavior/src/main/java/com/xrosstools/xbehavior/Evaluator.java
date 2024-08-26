package com.xrosstools.xbehavior;

public interface Evaluator {
	Object parse(String expressionStr);
	<T> T evaluate(Object expression, Blackboard context);
}
