package com.xrosstools.xbehavior.sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xrosstools.xbehavior.Action;
import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.StatusEnum;

@Component
public class ActionBean implements Action {

	@Autowired
	private ConditionBean condition;

	@Override
	public StatusEnum tick(Blackboard context) {
		return condition.check(context) == true? StatusEnum.SUCCESS: StatusEnum.FAILURE;
	}

	@Override
	public void reset() {
	}
}
