package com.ea.eamobile.nfsmw.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * It is not thread safe
 */
public class Markable {
	private Map<String, String> marks;

	public void mark(String key, String value) {
		getMarks().put(key, value);
	}

	public void mark(String key, int value) {
		getMarks().put(key, String.valueOf(value));
	}

	public void mark(String key, Long value) {
		getMarks().put(key, String.valueOf(value));
	}

	public void mark(String key, boolean value) {
		getMarks().put(key, String.valueOf(value));
	}

	// TODO make sure this is correct
	public void mark(String key, Date value) {
		getMarks().put(key, String.valueOf(value));
	}

	public Map<String, String> demark() {
		Map<String, String> result = marks;
		marks = null;
		return result;
	}

	public Map<String, String> getMarks() {
		if (marks == null) {
			marks = new HashMap<String, String>();
		}
		return marks;
	}

	public void setMarks(Map<String, String> marks) {
		getMarks().putAll(marks);
	}
	
	public void clear() {
		marks = null;
	}
}
