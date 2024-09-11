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
	abstract public Decorator createParent(PropertyParser parser);

	private BehaviorDef childDef;

	public BehaviorDef getChildDef() {
		return childDef;
	}

	public void setChildDef(BehaviorDef childDef) {
		this.childDef = childDef;
	}
	
	public Behavior create(PropertyParser parser) {
		Decorator parent = createParent(parser);
		parent.setDecorated(childDef.create(parser));
		return parent;
	}
	
	public static BehaviorDef waitDef(final String delayExp, final TimeUnit timeUnit) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent(PropertyParser parser) {
				return new Wait(parser.parseLong(delayExp), timeUnit);
			}
		};
	}
	
	public static BehaviorDef forceStatusDef(final StatusEnum status) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent(PropertyParser parser) {
				return new ForceStatus(status);
			}
		};
	}

	public static BehaviorDef inverterDef() {
		return new DecoratorDef() {
			@Override
			public Decorator createParent(PropertyParser parser) {
				return new Inverter();
			}
		};
	}
	
	public static BehaviorDef repeatDef(final String delayExp, final TimeUnit timeUnit, final boolean repeatUntilFailure) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent(PropertyParser parser) {
				Repeat rep = new Repeat(parser.parseLong(delayExp), timeUnit);
				rep.setRepeatUntilFailure(repeatUntilFailure);
				return rep;
			}
		};
	}
	
	public static BehaviorDef repeatDef(final String maxAttemptExp, final boolean repeatUntilFailure) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent(PropertyParser parser) {
				Repeat rep = new Repeat(parser.parseInteger(maxAttemptExp));
				rep.setRepeatUntilFailure(repeatUntilFailure);
				return rep;
			}
		};
	}
	
	public static BehaviorDef retryDef(final String delayExp, final TimeUnit timeUnit) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent(PropertyParser parser) {
				return new Retry(parser.parseLong(delayExp), timeUnit);
			}
		};
	}
	
	public static BehaviorDef retryDef(final String maxAttemptExp) {
		return new DecoratorDef() {
			@Override
			public Decorator createParent(PropertyParser parser) {
				return new Retry(parser.parseInteger(maxAttemptExp));
			}
		};
	}
}
