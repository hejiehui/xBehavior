package com.xrosstools.xbehavior;

public class ExpressionCondition extends Condition {
	private Evaluator evaluator;	
	private Object expression;

	public ExpressionCondition(Evaluator evaluator, String expressionStr) {
		this.evaluator = evaluator;
		this.expression = evaluator.compile(expressionStr);
	}
	
	@Override
	public boolean check(Blackboard context) {
		return (Boolean)evaluator.evaluate(expression , context);
	}

	@Override
	public void reset() {
	}
}
