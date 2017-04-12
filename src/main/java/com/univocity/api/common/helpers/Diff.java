package com.univocity.api.common.helpers;

import com.univocity.api.*;

/**
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public class Diff {

	public static final char EQUAL = '=';
	public static final char ADDED = '+';
	public static final char REMOVED = '-';

	public final char type;

	public final String content;

	public Diff(char type, String content) {
		this.type = type;
		this.content = content;
	}

	public String toString() {
		return type + "[" + content +']';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Diff diff = (Diff) o;

		if (type != diff.type) {
			return false;
		}
		return content.equals(diff.content);
	}

	@Override
	public int hashCode() {
		int result = (int) type;
		result = 31 * result + content.hashCode();
		return result;
	}

	public static Diff[] compare(String value1, String value2) {
		return Univocity.provider().execute("diff", value1, value2);
	}
}
