package com.xrosstools.xbehavior;

public abstract class Decorator implements Behavior {
	private Behavior behavior;
	
	public void setDecorated(Behavior behavior) {
		this.behavior = behavior;
	}

	public Behavior getDecorated() {
		return behavior;
	}
	
	public final void reset() {
		resetChild();
		resetParent();
	}
	
	public void resetParent() {}
	
	public void resetChild() {
		getDecorated().reset();
	}
}
