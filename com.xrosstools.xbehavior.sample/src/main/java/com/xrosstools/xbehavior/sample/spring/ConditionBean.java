package com.xrosstools.xbehavior.sample.spring;

import org.springframework.stereotype.Component;

import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.Condition;

@Component
public class ConditionBean implements Condition {

	@Override
	public boolean check(Blackboard context) {
		return context.get("A");
	}

}
