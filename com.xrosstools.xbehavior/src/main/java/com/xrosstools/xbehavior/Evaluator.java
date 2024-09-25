package com.xrosstools.xbehavior;

public interface Evaluator {
	/**
	 * Compile raw expression string into object that can be evaluated against Blackboard.
	 * This is only for better performance.
	 * @param expressionStr original expression in string form
	 * @return the compiled expression
	 */
	Object compile(String expressionStr);

	<T> T evaluate(Object expression, Blackboard context);
}
