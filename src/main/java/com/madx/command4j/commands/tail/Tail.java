package com.madx.command4j.commands.tail;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.Option;

/**
 * {@link Tail} is a {@link Command} object that build the command to list files. 
 * 
 * @author Daniele Maddaluno
 */
public class Tail extends Command {
	private static final long serialVersionUID = 1L;
	
	public static class TailOption extends Option<Tail> {
		private static final long serialVersionUID = 1L;
		
		protected TailOption(String optionCommand, Object optionValue) {
			super(optionCommand, optionValue, 0);
		}
	}

	/**
	 * -n, --lines=N output the last N lines
	 * 
	 * @return
	 */
	public static Option<Tail> onlyLastLines(Integer numberOfLines) {
		return new TailOption("-n", numberOfLines);
	}

	/**
	 * -c, --bytes=N output the last N bytes
	 * 
	 * @return
	 */
	public static Option<Tail> onlyLastBytes(Integer numberOfBytes) {
		return new TailOption("-c", numberOfBytes);
	}
}
