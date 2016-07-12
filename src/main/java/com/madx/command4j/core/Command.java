package com.madx.command4j.core;

import java.util.Objects;

import com.madx.command4j.core.Option.CommandOption;
import com.madx.command4j.core.Option.CommandOptionDemux;
import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * Base interface all the Unix Command have to implement so to be executable.
 * @author Daniele Maddaluno
 */
public class Command {

	protected final String name;
	protected OptionsBuilder<? extends Command> options;
	protected Command previousCommand;

	protected Command(){
		this(null, null);
	}
	
	protected Command(String name){
		this(name, null);
	}
	
	protected Command(String name, Command previousCommand) {
		this.name = (name!=null) ? name : this.getClass().getSimpleName().toLowerCase();
		this.previousCommand = previousCommand;
	}
	
	protected String getCommandName() {
		return name;
	}

	/**
	 * Different from the toString because it ignores the previous commands if any
	 * @return the string representing the current command ignoring the previous ones if any
	 */
	protected String toStringSingle() {
		return new StringBuilder().
				append(getCommandName()).
				append(StringSymbol.SPACE).
				append(Objects.toString(options, StringSymbol.EMPTY.toString())).
				toString();
	}

	@Override
	public String toString(){
		return new StringBuilder().
				append(Objects.toString(previousCommand, StringSymbol.EMPTY.toString())).
				append(this.toStringSingle()).
				toString();
	}

	/**
	 * Navigate the command chain and sets the previous command 
	 * to the first command of the commands chain, which 
	 * has previousCommand==null
	 * @param previousCommand
	 */
	protected void setPreviousCommand(Command previousCommand) {
		if(this.previousCommand==null) {
			this.previousCommand = previousCommand;
			return;
		}
		this.previousCommand.setPreviousCommand(previousCommand);
	}

	protected void setOptions(OptionsBuilder<? extends Command> options) {
		this.options = options;
	}
	
	/**
	 * Checks if the command contains a regex inside the options
	 * @return
	 */
	public boolean containsRegex(){
		boolean currentContainsRegex = this.options != null ? this.options.containsRegex() : false;
		if(currentContainsRegex) return true;
		if(this.previousCommand!=null) return this.previousCommand.containsRegex();
		return false;
	}
	
	public static Option<Command> text(String text) {
		return new CommandOption(text);
	}

	public static Option<Command> keyValue(String key, String value) {
		return new CommandOption(key, value);
	}

	public static Option<Command> help() {
		return new CommandOption("--help");
	}

	public static Option<Command> version() {
		return new CommandOption("--version");
	}
	
	public static Option<Command> path(String path) {
		return new CommandOptionDemux(path, false);
	}
	
	public static Option<Command> path(String path, boolean isRegex) {
		return new CommandOptionDemux(path, isRegex);
	}
}
