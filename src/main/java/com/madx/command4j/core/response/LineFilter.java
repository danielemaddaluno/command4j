package com.madx.command4j.core.response;

import lombok.Data;

/**
 * Represents a grep expression
 * @author Daniele Maddaluno
 */
@Data
public class LineFilter {
	
	private final String text;
	private final boolean isRegularExpression;
	
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
}
