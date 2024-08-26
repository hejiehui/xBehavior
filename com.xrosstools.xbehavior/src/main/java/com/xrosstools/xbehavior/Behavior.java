package com.xrosstools.xbehavior;

public interface Behavior {
	StatusEnum tick(Blackboard context);
	
	/**
	 * Reset current node, like stopping running node when necessary
	 * rest is usually invoked by parent node or self when tick completed 
	 */
	void reset();
}
