package com.xrosstools.xbehavior.def;

import java.util.ArrayList;
import java.util.List;

import com.xrosstools.xbehavior.Behavior;
import com.xrosstools.xbehavior.Composite;
import com.xrosstools.xbehavior.Parallel;
import com.xrosstools.xbehavior.Selector;
import com.xrosstools.xbehavior.Sequence;

public abstract class CompositeDef extends BehaviorDef {
	abstract public Composite createParent(PropertyParser parser);

	private List<BehaviorDef> childDefs = new ArrayList<>();
	
	public List<BehaviorDef> getChildDefs() {
		return childDefs;
	}

	public void setChildDefs(List<BehaviorDef> childDefs) {
		this.childDefs = childDefs;
	}

	@Override
	public Behavior create(PropertyParser parser) {
		Composite parent = createParent(parser);
		for(BehaviorDef def: childDefs)
			parent.add(def.create(parser));
		return parent;
	}
	
	public static BehaviorDef sequenceDef(final String reactiveExp) {
		return new CompositeDef() {
			@Override
			public Composite createParent(PropertyParser parser) {
				return new Sequence(Boolean.parseBoolean(reactiveExp));
			}
		};
	}

	public static BehaviorDef selectorDef(final String reactiveExp) {
		return new CompositeDef() {
			@Override
			public Composite createParent(PropertyParser parser) {
				return new Selector(Boolean.parseBoolean(reactiveExp));
			}
		};
	}
	
	public static BehaviorDef parallelDef(final String modeExp, final String minSuccessExp) {
		return new CompositeDef() {
			@Override
			public Composite createParent(PropertyParser parser) {
				return new Parallel(Parallel.Mode.valueOf(modeExp), parser.parseInteger(minSuccessExp));
			}
		};
	}	
}
