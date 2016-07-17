package com.madx.command4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
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

	/**
	 * Recursive function to take the regular expression options contained in the 
	 * command in the order of their appearance inside the command
	 * @return
	 */
	public List<CommandOptionDemux> containedRegex(){
		List<CommandOptionDemux> regexOptions = new ArrayList<CommandOptionDemux>();
		if(this.previousCommand != null) regexOptions.addAll(this.previousCommand.containedRegex());
		if(this.options!=null) regexOptions.addAll(this.options.containedRegex());
		return regexOptions;
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

	public static void main(String[] args) {
		//		List<String> list = Arrays.asList("Bob", "Ateve", "Jim", "Arbby");
		//		list.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);
		//		
		//	    list.replaceAll(String::toUpperCase);
		//	    
		//	    list.stream().map(o -> o.charAt(0)=='A' ? "+" : "-").collect(Collectors.toList()).forEach(System.out::println);



		LinkedList<String> repl = new LinkedList<String>(Arrays.asList("+++", "---", "***"));
		List<List<String>> test = Arrays.asList(Arrays.asList("Bob", "Ateve", "Jim", "Arbby"), Arrays.asList("Pro", "Tre", "And"));

		System.out.println(test);

		List<List<String>> testCopy = new LinkedList<List<String>>();
		for(List<String> a : test){
			testCopy.add(a.stream().map(o -> o.charAt(0)=='A' ? repl.pollFirst() : o).collect(Collectors.toList()));
		}
		System.out.println(repl.size()==0);
		System.out.println(testCopy);


		Set<List<String>> result = Sets.cartesianProduct(
				ImmutableSet.of("q", "w"),
				ImmutableSet.of("a", "s"),
				ImmutableSet.of("z", "x")
				);

		System.out.println(result);

	}
}
