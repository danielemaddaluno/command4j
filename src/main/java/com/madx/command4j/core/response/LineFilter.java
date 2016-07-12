package com.madx.command4j.core.response;

/**
 * Represents a grep expression
 * @author Daniele Maddaluno
 */
public class LineFilter {
	
	private final String text;
	private final boolean isRegularExpression;
	
	private LineFilter(String text, boolean isRegularExpression) {
		this.text = text;
		this.isRegularExpression = isRegularExpression;
	}

	/**
	 * Regular expression
	 * 
	 * @param text
	 * @return GrepExpression
	 */
	public static LineFilter regularExpression(String text) {
		return new LineFilter(text, true);
	}

	/**
	 * Natural Language(string)
	 * 
	 * @param text
	 * @return GrepExpression
	 */
	public static LineFilter constantExpression(String text) {
		return new LineFilter(text, false);
	}

	public String getText() {
		return text;
	}

	public boolean isRegularExpression() {
		return isRegularExpression;
	}
}
