package com.xrosstools.xbehavior;

public class ExpressionCondition implements Condition, Behavior {
	private Evaluator evaluator;	
	private Object expression;

	public ExpressionCondition(Evaluator evaluator, String leftExpressionStr, String operatorStr, String rightExpressionStr) {
		this.evaluator = evaluator;
		this.expression = evaluator.compile(leftExpressionStr, operatorStr, rightExpressionStr);
	}
	
	@Override
	public boolean check(Blackboard context) {
		return (Boolean)evaluator.evaluate(expression , context);
	}

	@Override
	public final StatusEnum tick(Blackboard context) {
		return check(context) ? StatusEnum.SUCCESS : StatusEnum .FAILURE;
	}

	@Override
	public void reset() {
	}
}
