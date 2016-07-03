package com.madx.command4j.commands.ls;

import com.madx.command4j.core.Option;

public class LsOption extends Option<Ls> {
	protected LsOption(String optionCommand) {
		super(optionCommand, null, 0);
	}

	public static Option<Ls> absolutePath(String path) {
		return new LsOption(path);
	}
}