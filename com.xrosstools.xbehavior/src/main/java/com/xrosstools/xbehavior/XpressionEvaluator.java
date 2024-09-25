package com.xrosstools.xbehavior;

import com.xrosstools.xpression.Expression;
import com.xrosstools.xpression.Facts;
import com.xrosstools.xpression.XpressionCompiler;

public class XpressionEvaluator implements Evaluator {
	private XpressionCompiler compiler = new XpressionCompiler();
	
	@Override
	public Object compile(String expressionStr) {
		return compiler.compile(expressionStr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(Object expression, Blackboard context) {
		Expression exp = (Expression)expression;
		return (T)exp.evaluate(new BlackboardFact(context));
	}
	
	private class BlackboardFact implements Facts {
		Blackboard blackboard;
		BlackboardFact(Blackboard blackboard) {
			this.blackboard = blackboard;
		}

		@Override
		public String[] getNames() {
			return blackboard.keySet().toArray(new String[blackboard.size()]);
		}

		@Override
		public boolean contains(String name) {
			return blackboard.contains(name);
		}

		@Override
		public Object get(String name) {
			return blackboard.get(name);
		}
	}
}
