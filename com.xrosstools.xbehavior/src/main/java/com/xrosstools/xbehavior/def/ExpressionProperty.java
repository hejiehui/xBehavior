package com.xrosstools.xbehavior.def;

import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.Evaluator;
import com.xrosstools.xbehavior.Property;

public class ExpressionProperty<T> implements Property<T>{
	private Evaluator evaluator;
	private Object expression;

	public ExpressionProperty(Evaluator evaluator, Object expression) {
		this.expression = expression;
	}

	@Override
	public T get(Blackboard context) {
		return evaluator.evaluate(expression, context);
	}
	
	public static <T> ExpressionProperty<T> of(Evaluator evaluator, String expStr) {
		return new ExpressionProperty<T>(evaluator, evaluator.compile(expStr));
	}
}
