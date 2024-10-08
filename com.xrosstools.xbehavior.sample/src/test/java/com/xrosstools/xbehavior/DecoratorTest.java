package com.xrosstools.xbehavior;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xbehavior.sample.TestBehavior;

public class DecoratorTest {
	private Boolean resetParent;
	private TestBehavior internal;
	private Decorator test = new Decorator() {

		@Override
		public StatusEnum tick(Blackboard context) {
			return null;
		}
		public void resetParent() {
			resetParent = true;
		}
		
		public void resetChild() {
			getDecorated().reset();
		}

	};

	@Before
	public void setUp() throws Exception {
		resetParent = false;
		internal = new TestBehavior();
		test.setDecorated(internal);
	}

	@Test
	public void testReset() {
		assertFalse(resetParent);
		assertEquals(0, internal.getResetCount());
		
		test.reset();
		assertTrue(resetParent);
		assertEquals(1, internal.getResetCount());
	}

}
