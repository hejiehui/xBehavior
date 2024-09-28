package com.xrosstools.xbehavior;

import java.util.HashSet;
import java.util.Set;

import com.xrosstools.xbehavior.def.ValueProperty;

public class Parallel extends Composite {
    public enum Mode {
        ALL, ANY, SOME,
    }

    private Mode mode;
    private Property<Integer> minSuccess;
	private Set<Integer> completed;
	private int successCounter;
	private int failureCounter;
	
	public Parallel() {}

	public Parallel(Mode mode, Integer minSuccess) {
		this(mode, ValueProperty.of(minSuccess));
	}
	
	public Parallel(Mode mode, Property<Integer> minSuccess) {
		this.mode = mode;
		this.minSuccess = minSuccess;
	}

	public void setMinSuccess(Integer minSuccess) {
		this.minSuccess = ValueProperty.of(minSuccess);
	}

	/**
	 * All children need to be executed
	 */
	public StatusEnum tick(Blackboard context) {
		if(completed == null) {
			completed = new HashSet<>();
			int min = mode == Mode.ANY ? 1 : mode == Mode.ALL ? size() : minSuccess.get(context);
			successCounter = min;
			failureCounter = size() - min;
		}

		for(int i = 0; i < size(); i++) {
			if(completed.contains(i))
				continue;

			StatusEnum status = get(i).tick(context);
			
			if(status == StatusEnum.RUNNING)
				continue;

			if(status == StatusEnum.SUCCESS) {
				successCounter--;
				if(successCounter == 0) {
					reset();
					return StatusEnum.SUCCESS;
				}
			} else if(status == StatusEnum.FAILURE) {
				failureCounter--;
				if(failureCounter < 0) {
					reset();
					return StatusEnum.FAILURE;
				}
			}

			completed.add(i);
		}
		
		return StatusEnum.RUNNING;
	}
	
	public void resetParent() {
		completed.clear();
		completed = null;
	}
}