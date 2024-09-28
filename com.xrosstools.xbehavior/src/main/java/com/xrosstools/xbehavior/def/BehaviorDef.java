package com.xrosstools.xbehavior.def;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.xrosstools.xbehavior.Action;
import com.xrosstools.xbehavior.AsyncAction;
import com.xrosstools.xbehavior.Behavior;
import com.xrosstools.xbehavior.Evaluator;
import com.xrosstools.xbehavior.ExpressionCondition;
import com.xrosstools.xbehavior.FixedStatus;
import com.xrosstools.xbehavior.Sleep;
import com.xrosstools.xbehavior.StatusEnum;

public abstract class BehaviorDef implements PropertyConstants {
	private String name;
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public abstract Behavior create();
	
	private static PropertyParser parser = new PropertyParser();
	
	public static BehaviorDef actionDef(final String implementation) {
		return new BehaviorDef() {
			@Override
			public Behavior create() {
				return InstatnceFactory.getInstance(implementation);
			}
		};
	}

	public static BehaviorDef asynchActionDef(final String implementation, final String delayExp, final TimeUnit timeUnit) {
		return new BehaviorDef() {
			@Override
			public Behavior create() {
				Action act = InstatnceFactory.getInstance(implementation);
				return new AsyncAction(parser.parseLong(delayExp), timeUnit, act);
			}
		};
	}

	public static BehaviorDef sleepDef(final String delayExp, final TimeUnit timeUnit) {
		return new BehaviorDef() {
			@Override
			public Behavior create() {
				return new Sleep(parser.parseLong(delayExp), timeUnit);
			}
		};
	}
	
	public static BehaviorDef fixedStatusDef(final String statusExp) {
		return new BehaviorDef() {
			@Override
			public Behavior create() {
				return new FixedStatus(StatusEnum.valueOf(statusExp));
			}
		};
	}
	
	public static BehaviorDef callbackConditionDef(final String implementation) {
		return new BehaviorDef() {
			@Override
			public Behavior create() {
				return InstatnceFactory.getInstance(implementation);
			}
		};
	}	

	public static BehaviorDef exprConditionDef(final Evaluator evaluator, final String expressionStr) {
		return new BehaviorDef() {
			@Override
			public Behavior create() {
				return new ExpressionCondition(evaluator, expressionStr);
			}
		};
	}
	
	public static BehaviorDef subtreeDef(final Map<String, BehaviorDef> treeDefs, final String name) {
		return new BehaviorDef() {
			@Override
			public Behavior create() {
				return treeDefs.get(name).create();
			}
		};
	}
}
