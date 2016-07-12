package com.madx.command4j.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.madx.command4j.core.Option.CommandOptionDemux;
import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * @author Daniele Maddaluno
 * @param <T>
 */
public class OptionsBuilder<T extends Command> {

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

	public boolean containsRegex() {
		return this.options.
				parallelStream().
				anyMatch(o -> o.getClass().equals(CommandOptionDemux.class) && o.containsRegex());
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