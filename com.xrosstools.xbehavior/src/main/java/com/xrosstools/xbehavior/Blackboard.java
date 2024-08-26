package com.xrosstools.xbehavior;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Blackboard {
	private Map<String, Object> entries = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T)entries.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOrDefault(String key, T value) {
		return (T)entries.getOrDefault(key, value);
	}

	public <T> void set(String key, T data) {
		entries.put(key, data);
	}
	
	public <T> void setIfAbsent(String key, T data) {
		entries.putIfAbsent(key, data);
	}

	public boolean contains(String key) {
		return entries.containsKey(key);
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}

	public Set<String> keySet() {
		return entries.keySet();
	}
	
	public void clear() {
		entries.clear();
	}
}
