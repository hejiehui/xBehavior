package com.xrosstools.xbehavior;

import java.util.ArrayList;
import java.util.List;

public abstract class Composite implements Behavior {
	private List<Behavior> children = new ArrayList<>();
	
	public List<Behavior> getChildren() {
		return new ArrayList<Behavior>(children);
	}

	public void setChildren(List<Behavior> children) {
		this.children = children;
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

	public int size() {
		return children.size();
	}
	
	public Behavior get(int index) {
		return children.get(index);
	}
	
	public void add(Behavior child) {
		children.add(child);
	}
	
	public void add(int index, Behavior child) {
		children.add(index, child);
	}
	
	public void addAll(List<Behavior> list) {
		children.addAll(list);
	}
	
	public Behavior remove(int index) {
		return children.remove(index);
	}
	
	public void clear() {
		children.clear();
	}
	
	public final void reset() {
		resetChildren();
		resetParent();
	}
	
	public void resetParent() {}
	
	public void resetChildren() {
		for(Behavior child: getChildren())
			child.reset();
	}
}
