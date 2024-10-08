package com.xrosstools.xbehavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xbehavior.sample.TestBehavior;

public class CompositeTest {
	private Composite test;
	private boolean resetParent = false;
	
	@Before
	public void setup() {
		test = new Composite() {
			@Override
			public StatusEnum tick(Blackboard context) {
				// TODO Auto-generated method stub
				return StatusEnum.SUCCESS;
			}
			
			public void resetParent() {
				resetParent = true;
			}
		};
		
		test.add(new TestBehavior(StatusEnum.SUCCESS));
		test.add(new TestBehavior(StatusEnum.SUCCESS));		
		test.add(new TestBehavior(StatusEnum.SUCCESS));
	}

	@Test
	public void testGetChildren() {
		List<Behavior> l = test.getChildren();
		assertNotNull(l);
		assertEquals(3l, l.size());
	}

	@Test
	public void testSetChildren() {
		List<Behavior> l = new ArrayList<>();
		l.add(new TestBehavior(StatusEnum.SUCCESS));
		test.setChildren(l);
		assertEquals(1l, test.getChildren().size());
	}

	@Test
	public void testIsEmpty() {
		assertFalse(test.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(3l, test.size());
	}

	@Test
	public void testGet() {
		assertNotNull(test.get(0));
		assertNotNull(test.get(1));
		assertNotNull(test.get(2));
	}

	@Test
	public void testAddBehavior() {
		test.add(new TestBehavior(StatusEnum.FAILURE));
		assertNotNull(test.get(3));
		assertEquals(StatusEnum.FAILURE, test.get(3).tick(null));
	}

	@Test
	public void testAddIntBehavior() {
		assertEquals(StatusEnum.SUCCESS, test.get(0).tick(null));
		test.add(0, new TestBehavior(StatusEnum.FAILURE));
		assertNotNull(test.get(0));
		assertEquals(StatusEnum.FAILURE, test.get(0).tick(null));
	}

	@Test
	public void testAddAll() {
		List<Behavior> l = new ArrayList<>();
		l.add(new TestBehavior(StatusEnum.FAILURE));
		l.add(new TestBehavior(StatusEnum.FAILURE));
		test.addAll(l);
		assertEquals(5l, test.size());
		assertEquals(StatusEnum.FAILURE, test.get(3).tick(new Blackboard()));
		assertEquals(StatusEnum.FAILURE, test.get(4).tick(new Blackboard()));
	}

	@Test
	public void testRemove() {
		assertNotNull(test.remove(0));
		assertNotNull(test.remove(1));
		assertEquals(1l, test.size());
	}

	@Test
	public void testClear() {
		test.clear();
		assertEquals(0l, test.size());
	}

	@Test
	public void testReset() {
		for(int i = 0; i < 3;i++) {
			test.reset();
			assertTrue(resetParent);
			resetParent = false;
			for(Behavior c: test.getChildren()) {
				TestBehavior b = (TestBehavior)c;
				assertEquals(i+1, b.getResetCount());
			}
		}
	}
}
