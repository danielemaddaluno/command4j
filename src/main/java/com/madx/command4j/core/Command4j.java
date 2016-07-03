package com.madx.command4j.core;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.madx.command4j.core.launchers.CommandsLauncher;
import com.madx.command4j.core.launchers.Launcher;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandsResponses;

/**
 * Study this: http://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/
 * for running
 * @author Daniele Maddaluno
 *
 */
public final class Command4j {

	private final List<Profile> profiles;
	private final List<Command> commands;
	private final Launcher<CommandsResponses, List<Profile>, List<Command>> commandsLauncher;

	private Command4j(List<Profile> profiles, List<Command> commands) {
		this.commands = commands;
		this.profiles = ImmutableList.copyOf(profiles);
		this.commandsLauncher = new CommandsLauncher();
	}
	
	public static CommandsResponses command(List<Profile> profiles, List<Command> commands) {
		return new Command4j(profiles, commands).execute();
	}
	
	public static CommandsResponses command(Profile profile, List<Command> commands) {
		return command(Collections.singletonList(profile), commands);
	}
	
	public static CommandsResponses command(List<Profile> profiles, Command command) {
		return command(profiles, Collections.singletonList(command));
	}
	
	public static CommandsResponses command(Profile profile, Command command) {
		return command(Collections.singletonList(profile), Collections.singletonList(command));
	}

	private CommandsResponses execute() {
		verifyInputs();
		return commandsLauncher.launch(profiles, commands);
	}
	
	private void verifyInputs() {
		if (commands == null || commands.isEmpty()) {
			throw new IllegalArgumentException("No command to execute was specified");
		}
		if (profiles == null || profiles.isEmpty()) {
			throw new IllegalArgumentException("No profile to execute was specified");
		}
	}
}
