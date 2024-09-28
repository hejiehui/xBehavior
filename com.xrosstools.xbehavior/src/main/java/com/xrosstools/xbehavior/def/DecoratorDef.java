package com.xrosstools.xbehavior.def;

import java.util.concurrent.TimeUnit;

import com.xrosstools.xbehavior.Behavior;
import com.xrosstools.xbehavior.Decorator;
import com.xrosstools.xbehavior.Wait;
import com.xrosstools.xbehavior.ForceStatus;
import com.xrosstools.xbehavior.Inverter;
import com.xrosstools.xbehavior.Repeat;
import com.xrosstools.xbehavior.Retry;
import com.xrosstools.xbehavior.StatusEnum;

public abstract class DecoratorDef extends BehaviorDef {
	abstract public Decorator createParent();

	private BehaviorDef childDef;

	public BehaviorDef getChildDef() {
		return childDef;
	}

	public void setChildDef(BehaviorDef childDef) {
		this.childDef = childDef;
	}
	
	public Behavior create() {
		Decorator parent = createParent();
		parent.setDecorated(childDef.create());
		return parent;
	}
	
	private static PropertyParser parser = new PropertyParser();

	public static BehaviorDef waitDef(final String delayExp, final TimeUnit timeUnit) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent() {
				return new Wait(parser.parseLong(delayExp), timeUnit);
			}
		};
	}
	
	public static BehaviorDef forceStatusDef(final StatusEnum status) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent() {
				return new ForceStatus(status);
			}
		};
	}

	public static BehaviorDef inverterDef() {
		return new DecoratorDef() {
			@Override
			public Decorator createParent() {
				return new Inverter();
			}
		};
	}
	
	public static BehaviorDef repeatDef(final String delayExp, final TimeUnit timeUnit, final boolean repeatUntilFailure) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent() {
				return new Repeat(parser.parseLong(delayExp), timeUnit, repeatUntilFailure);
			}
		};
	}
	
	public static BehaviorDef repeatDef(final String maxAttemptExp, final boolean repeatUntilFailure) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent() {
				return new Repeat(parser.parseInteger(maxAttemptExp), repeatUntilFailure);
			}
		};
	}
	
	public static BehaviorDef retryDef(final String delayExp, final TimeUnit timeUnit) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent() {
				return new Retry(parser.parseLong(delayExp), timeUnit);
			}
		};
	}
	
	public static BehaviorDef retryDef(final String maxAttemptExp) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent() {
				return new Retry(parser.parseInteger(maxAttemptExp));
			}
		};
	}
}
