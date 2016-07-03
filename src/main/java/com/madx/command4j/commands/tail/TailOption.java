package com.madx.command4j.commands.tail;

import com.madx.command4j.core.Option;

public class TailOption extends Option<Tail> {

	protected TailOption(String optionCommand, Object optionValue) {
		super(optionCommand, optionValue, 0);
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
