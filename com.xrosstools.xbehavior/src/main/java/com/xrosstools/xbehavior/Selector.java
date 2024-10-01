package com.xrosstools.xbehavior;

import java.util.List;

public class Selector extends Composite {
	private boolean reactive;
	private volatile int currentIndex;
	
	public Selector() {
		this(false);
	}
			
	public Selector(boolean reactive) {
		this.reactive = reactive;
	}

	public boolean isReactive() {
		return reactive;
	}

	public StatusEnum tick(Blackboard context) {
		List<Behavior> children = getChildren();

		for(int i = currentIndex; i < children.size(); i++) {
			Behavior child = children.get(i);

			StatusEnum status = child.tick(context);
			if(status == StatusEnum.SUCCESS) {
				reset();
				return StatusEnum.SUCCESS;
			}
			
			if(status == StatusEnum.RUNNING) {
				currentIndex = reactive ? 0 : i;
				return StatusEnum.RUNNING;
			}
		}
		
		reset();
		return StatusEnum.FAILURE;
	}
	
	public void resetParent() {
		currentIndex = 0;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
}
