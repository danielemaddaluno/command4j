package com.madx.command4j.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.madx.command4j.core.Option.CommandOptionDemux;
import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * @author Daniele Maddaluno
 * @param <T>
 */
public class OptionsBuilder<T extends Command> implements Serializable {

	private static final long serialVersionUID = 8774926070054623578L;
	private final List<Option<? super T>> options = new ArrayList<Option<? super T>>();

	protected OptionsBuilder(){}

	protected OptionsBuilder(List<Option<? super T>> options){
		if(options!=null) this.options.addAll(options);
	}

	@Deprecated
	public static <V extends Command> OptionsBuilder<V> with(Option<? super V> firstArgument) {
		return new OptionsBuilder<V>().and(firstArgument);
	}

	@Deprecated
	public OptionsBuilder<T> and(Option<? super T> arg) {
		options.add(arg);
		return this;
	}

	/**
	 * 
	 * @param type
	 * @param equals if true matches the options equals to the type otherwise matches the options not equal to type
	 * @return
	 */
	public OptionsBuilder<T> filterOptionsByType(Class<? super T> type, boolean equals) {
		OptionsBuilder<T> options = new OptionsBuilder<T>();
		options.options.addAll(this.options.
				stream().
				filter(o -> equals ? o.getTypeParameterClass().equals(type) : !o.getTypeParameterClass().equals(type)).
				collect(Collectors.toList()));
		return options;
	}

	public OptionsBuilder<T> onlyGenericOptions() {
		return filterOptionsByType(Command.class, true);
	}

	public OptionsBuilder<T> onlySpecificOptions() {
		return filterOptionsByType(Command.class, false);
	}

	private Predicate<? super Option<? super T>> isRegex(){
		return o -> o.getClass().equals(CommandOptionDemux.class) && o.containsRegex();
	}
	
	public boolean containsRegex() {
		return this.options.
				parallelStream().
				anyMatch(isRegex());
	}

	public List<CommandOptionDemux> containedRegex(){
		return this.options.
				stream().
				filter(isRegex()).
				map(CommandOptionDemux.class::cast).
				collect(Collectors.toList());
	}

	public OptionsBuilder<T> replaceRegex(List<Option<Command>> optionsToReplace){
		Function<Option<? super T>, Option<? super T>> replaceFunction = o -> isRegex().test(o) ? optionsToReplace.remove(0) : o;
		List<Option<? super T>> options = this.options.
				stream().
				map(replaceFunction).
				collect(Collectors.toList());
		return new OptionsBuilder<T>(options);
	}

	private void sort(){
		Collections.sort(this.options);
	}

	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		if(options != null && !options.isEmpty()){
			sort();
			b.append(this.options.stream().
					map(Option::toString).
					collect(Collectors.joining(StringSymbol.SPACE.toString())));
			b.append(StringSymbol.SPACE.toString());
		}
		return b.toString();
	}

}