package com.madx.command4j.core.response;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.madx.command4j.core.model.Profile;

/**
 * This class contains a List with all the results produced by a grep task
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 * @author Daniele Maddaluno
 */
public class CommandsResponses implements Collection<CommandResponse> {

	private final String LINE_SEPARATOR = System.getProperty("line.separator");
	private final List<CommandResponse> commandResults;
	private long executionTime;
	
	public Stream<CommandResponse> stream() {
        return commandResults.stream();
    }
	
	/**
	 * GlobalGrepResult is a container of different {@link CommandResponse}
	 * 
	 * @param the
	 *            expression used to grep
	 */
	public CommandsResponses() {
		commandResults = new CopyOnWriteArrayList<CommandResponse>();
	}

	/**
	 * it counts how many times the pattern is found in all the results
	 * 
	 * @return total number of time the patter is found in all the GrepResults
	 */
	public int totalLines() {
		int totalLines = 0;
		for (CommandResponse result : commandResults) {
			totalLines += result.totalLines();
		}
		return totalLines;
	}

	/**
	 * It filters the grepResults object based on the Profile name passed and return All the GrepResult for only the particular Profile passed.
	 * 
	 * @param profile
	 * @return GrepResults for the passed Profile
	 */
	public CommandsResponses filterOnProfile(Profile profile) {
		CommandsResponses filteredResults = new CommandsResponses();
		filteredResults.addAll(commandResults.stream().filter(p -> p.getProfileName().equals(profile.getName())).collect(Collectors.toList()));
		return filteredResults;
	}

	/**
	 * @return the average time spent for all the grep tasks in milliseconds
	 */
	public long getAverageExecutionTime() {
		long totalExecutionTime = 0;
		for (CommandResponse result : commandResults) {
			totalExecutionTime += result.getExecutionTime();
		}
		return totalExecutionTime / commandResults.size();
	}

	/**
	 * Loop through all the GrepResults and for each one extracts the lines that match with the passed filter as a constant or regular Expression
	 * 
	 * @param expression
	 * @return the lines that match with the passed filter as a constant or regular Expression
	 */
	public CommandsResponses filterBy(LineFilter lineFilter) {
		CommandsResponses grepResultsSet = new CommandsResponses();
		for (CommandResponse result : commandResults) {
			CommandResponse extractResult = result.filterBy(lineFilter);
			if (!extractResult.getText().isEmpty()) {
				grepResultsSet.add(extractResult);
			}
		}
		return grepResultsSet;
	}

	/**
	 * This return the first GrepResult in the GrepResuts list
	 * 
	 * @return the first GrepResult in the GrepResuts list
	 */
	public CommandResponse getSingleResult() {
		return commandResults.iterator().next();
	}

	@Override
	public String toString() {
		StringBuilder tostringbuilder = new StringBuilder();
		for (CommandResponse result : commandResults) {
			tostringbuilder.append(result);
		}
		return tostringbuilder.toString();
	}

	/**
	 * 
	 * @return all the header information for each single @GrepResult in this format : Profile name >>>%s<<< [ File Name:%s; Total lines found:%s; Total execution time:%s; Expression:%s ]
	 */
	public String getHeaderInformation() {
		StringBuilder sb = new StringBuilder();
		for (CommandResponse result : commandResults) {
			sb.append(String.format("Profile name >>>%s<<< [ Total lines found:%s; Total execution time:%s; Expression:%s ]",
					result.getProfile().getName(), 
					result.totalLines(), 
					result.getExecutionTime(), 
					result.getCommand().toString()));
			sb.append(LINE_SEPARATOR);
		}
		return sb.toString();
	}

	/**
	 * Add a {@link CommandResponse} to the Set of results
	 * 
	 * @param grepResult
	 */
	@Override
	public boolean add(CommandResponse e) {
		return commandResults.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends CommandResponse> c) {
		return commandResults.addAll(c);
	}

	@Override
	public void clear() {
		commandResults.clear();
	}

	@Override
	public boolean contains(Object o) {
		return commandResults.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return commandResults.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return commandResults.isEmpty();
	}

	@Override
	public Iterator<CommandResponse> iterator() {
		return commandResults.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return commandResults.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return commandResults.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return commandResults.retainAll(c);
	}

	@Override
	public int size() {
		return commandResults.size();
	}

	@Override
	public Object[] toArray() {
		return commandResults.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return commandResults.toArray(a);
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
}