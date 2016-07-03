package com.madx.command4j.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.madx.command4j.core.utils.string.StringSymbol;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 
 * @author Daniele Maddaluno
 *
 * @param <T>
 */
@EqualsAndHashCode
public abstract class Option<T extends Command> implements Comparable<Option<T>>{
	@Getter
	private final String optionCommand;
	@Getter
	private final Object optionValue;
	@Getter
	private final int executionOrder;

	protected Option(String optionCommand, Object optionValue, int executionOrder) {
		if(optionCommand == null || optionCommand.isEmpty()) throw new IllegalArgumentException("The String field is missing (null or empty)");
		this.optionCommand = optionCommand;
		this.optionValue = optionValue;
		this.executionOrder = executionOrder;
	}
	
	@SuppressWarnings ("unchecked")
	public Class<T> getTypeParameterClass(){
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		return (Class<T>) paramType.getActualTypeArguments()[0];
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(optionCommand);
		if (optionValue != null) {
			stringBuilder.append(StringSymbol.SPACE);
			stringBuilder.append(optionValue);
		}
		return stringBuilder.toString();
	}
	
	@Override
	public int compareTo(Option<T> other) {
		return Integer.compare(this.executionOrder, other.executionOrder);
	}
	
}
