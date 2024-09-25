package com.xrosstools.xbehavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xbehavior.def.PropertyParser;

public class PropertyParserTest {
	private PropertyParser test;
	private Blackboard bb;
	private String key;

	@Before
	public void setUp() throws Exception {
		test = new PropertyParser();
		bb = new Blackboard();
		key = "Abc";
	}

	@Test
	public void testParseInteger() {
		assertEquals(123, (int)test.parseInteger("123").get(bb));
		
		bb.set(key, 123);
		assertEquals(123, (int)test.parseInteger(key).get(bb));
	}

	@Test
	public void testParseLong() {
		assertEquals(655356, (long)test.parseLong("655356").get(bb));

		bb.set(key, 655356);
		assertEquals(655356, (long)test.parseInteger(key).get(bb));
	}

	@Test
	public void testParseBoolean() {
		assertTrue(test.parseBoolean("true").get(bb));
		assertFalse(test.parseBoolean("false").get(bb));

		bb.set(key, true);
		assertTrue(test.parseBoolean(key).get(bb));

		bb.set(key, false);
		assertFalse(test.parseBoolean(key).get(bb));
	}

	@Test
	public void testParseFloat() {
		assertEquals(655.1121, (float)test.parseFloat("655.1121").get(bb), 0.001);

		bb.set(key, 655.1121f);
		assertEquals(655.1121, (float)test.parseFloat(key).get(bb), 0.001);
	}

	@Test
	public void testParseDouble() {
		assertEquals(6551234.1121, (double)test.parseDouble("6551234.1121").get(bb), 0.001);

		bb.set(key, 6551234.1121);
		assertEquals(6551234.1121, (double)test.parseDouble(key).get(bb), 0.001);
	}
}
