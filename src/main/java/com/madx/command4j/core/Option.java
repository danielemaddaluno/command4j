package com.madx.command4j.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * 
 * @author Daniele Maddaluno
 *
 * @param <T>
 */
public abstract class Option<T extends Command> implements Comparable<Option<T>>{
	private final String optionCommand;
	private final Object optionValue;
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

	public String getOptionCommand() {
		return optionCommand;
	}

	public Object getOptionValue() {
		return optionValue;
	}

	public int getExecutionOrder() {
		return executionOrder;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option<?> other = (Option<?>) obj;
		if (executionOrder != other.executionOrder)
			return false;
		if (optionCommand == null) {
			if (other.optionCommand != null)
				return false;
		} else if (!optionCommand.equals(other.optionCommand))
			return false;
		if (optionValue == null) {
			if (other.optionValue != null)
				return false;
		} else if (!optionValue.equals(other.optionValue))
			return false;
		return true;
	}
	
}
