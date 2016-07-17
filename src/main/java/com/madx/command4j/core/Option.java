package com.madx.command4j.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * 
 * @author Daniele Maddaluno
 *
 * @param <T>
 */
public abstract class Option<T extends Command> implements Comparable<Option<T>>, Serializable{

	private static final long serialVersionUID = -3560165931981379751L;
	private final String optionCommand;
	private final Object optionValue;
	private final int executionOrder;
	private boolean isRegex = false;

	private Option(String optionCommand, Object optionValue, int executionOrder, boolean isRegex) {
		if(StringUtils.isEmpty(optionCommand) || StringUtils.isBlank(optionCommand)) throw new IllegalArgumentException("The String field is missing (null or empty)");
		this.optionCommand = optionCommand;
		this.optionValue = optionValue;
		this.executionOrder = executionOrder;
		this.isRegex = isRegex;
	}

	protected Option(String optionCommand, Object optionValue, int executionOrder) {
		this(optionCommand, optionValue, executionOrder, false);
	}

	public boolean containsRegex() {
		return isRegex ? StringUtils.contains(this.getOptionCommand(), "*") : false;
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
		if (isRegex != other.isRegex)
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

	/**
	 * Represents the options that refers generally to any command
	 * and can be used with all of them indifferently
	 * @author Daniele Maddaluno
	 *
	 */
	protected static class CommandOption extends Option<Command> {
		private static final long serialVersionUID = 1L;
		
		protected CommandOption(String optionCommand, String optionValue, int executionOrder) {
			super(optionCommand, optionValue, executionOrder);
		}

		protected CommandOption(String optionCommand, String optionValue) {
			this(optionCommand, optionValue, 0);
		}

		protected CommandOption(String optionCommand) {
			this(optionCommand, null, 0);
		}
	}

	protected static class CommandOptionDemux extends Option<Command> {
		private static final long serialVersionUID = 1L;
		
		protected CommandOptionDemux(String path, boolean isRegex) {
			super(path, null, 0, isRegex);
		}
	}
}
