package com.xrosstools.xbehavior;

public class Inverter extends Decorator {
	public StatusEnum tick(Blackboard context) {
		StatusEnum status = getDecorated().tick(context);
		if(status == StatusEnum.FAILURE)
			return StatusEnum.SUCCESS;
		
		if(status == StatusEnum.SUCCESS)
			return StatusEnum.FAILURE;
		
		return status;
	}
}
