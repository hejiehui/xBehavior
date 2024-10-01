package com.xrosstools.xbehavior;

public interface Evaluator {
	/**
	 * Compile raw expression string into object that can be evaluated against Blackboard.
	 * This is only for better performance.
	 * @param leftExpressionStr original left expression in string form
	 * @param operatorStr original operator in string form
	 * @param rightExpressionStr original right expression in string form
	 * @return the compiled expression
	 */
	Object compile(String leftExpressionStr, String operatorStr, String rightExpressionStr);
	
	<T> T evaluate(Object expression, Blackboard context);
}
