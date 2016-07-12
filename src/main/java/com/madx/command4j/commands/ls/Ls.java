package com.madx.command4j.commands.ls;


import com.madx.command4j.core.Command;
import com.madx.command4j.core.Option;

/**
 * {@link Ls} is a {@link Command} object that build the command to list files. It's used in case a wildcard is specified. Example:
 * "ls /tmp/server.log*"
 * 
 * @author Daniele Maddaluno
 */
public class Ls extends Command {

	public static class LsOption extends Option<Ls> {
		protected LsOption(String optionCommand) {
			super(optionCommand, null, 0);
		}
	}

	public static Option<Ls> absolutePath(String path) {
		return new LsOption(path);
	}
}
