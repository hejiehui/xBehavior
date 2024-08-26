package com.xrosstools.xbehavior;

public class ExpressionCondition implements Condition, Behavior {
	private Evaluator evaluator;	
	private Object expression;

	public ExpressionCondition(Evaluator evaluator, String expressionStr) {
		this.evaluator = evaluator;
		this.expression = evaluator.parse(expressionStr);
	}
	
	@Override
	public boolean check(Blackboard context) {
		return (Boolean)evaluator.evaluate(expression , context);
	}

	@Override
	public StatusEnum tick(Blackboard context) {
		return check(context) ? StatusEnum.SUCCESS : StatusEnum .FAILURE;
	}

	@Override
	public void reset() {
	}
}
