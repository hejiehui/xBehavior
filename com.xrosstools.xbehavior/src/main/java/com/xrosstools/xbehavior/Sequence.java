package com.xrosstools.xbehavior;

import java.util.List;

public class Sequence extends Composite {
	private boolean reactive;
	private int currentIndex;

	public Sequence() {
		this(false);
	}

	public Sequence(boolean reactive) {
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
			if(status == StatusEnum.FAILURE) {
				reset();
				return StatusEnum.FAILURE;
			} 
			
			if(status == StatusEnum.RUNNING) {
				currentIndex = reactive ? 0 : i;
				return StatusEnum.RUNNING;
			}
		}
		
		reset();
		return StatusEnum.SUCCESS;
	}
	
	public void resetParent() {
		currentIndex = 0;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
}