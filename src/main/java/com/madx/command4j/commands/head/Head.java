package com.madx.command4j.commands.head;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.Option;

/**
 * {@link Head} is a {@link Command} object that build the command to list files. 
 * 
 * @author Daniele Maddaluno
 */
public class Head extends Command {
	private static final long serialVersionUID = 1L;
	
	public static class HeadOption extends Option<Head> {
		private static final long serialVersionUID = 1L;

		protected HeadOption(String optionCommand, Object optionValue) {
			super(optionCommand, optionValue, 0);
		}
	}

	/**
	 * -n, --lines=N output the first N lines
	 * 
	 * @return
	 */
	public static Option<Head> onlyFirstLines(Integer numberOfLines) {
		return new HeadOption("-n", numberOfLines);
	}

	/**
	 * -c, --bytes=N output the first N bytes
	 * 
	 * @return
	 */
	public static Option<Head> onlyFirstBytes(Integer numberOfBytes) {
		return new HeadOption("-c", numberOfBytes);
	}
}
