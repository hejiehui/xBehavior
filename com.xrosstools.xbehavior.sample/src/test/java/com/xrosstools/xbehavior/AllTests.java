package com.xrosstools.xbehavior;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	//Action
	FixedStatusTest.class,
	AsyncActionTest.class,
	SleepTest.class,
	TimeoutTest.class,
	
	//Decorator
	DecoratorTest.class,
	WaitTest.class,
	ForceStatusTest.class,
	InverterTest.class,
	RepeatTest.class,
	RetryTest.class,
	
	//Composite
	CompositeTest.class,
	SequenceTest.class,
	SelectorTest.class,
	ParallelTest.class,
})
public class AllTests {

}
