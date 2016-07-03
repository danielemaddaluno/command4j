package com.madx.command4j.core.task;

import static com.madx.command4j.core.executors.CommandExecutor.getCommandExecutor;

import java.util.concurrent.Callable;

import org.apache.commons.lang3.time.StopWatch;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.executors.CommandExecutor;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandResponse;

/**
 * This class run the Executor. Forking the executor is helpful in the case we have to grep into multiple files (Example when the profile.fileName is
 * .../server.log*)
 * 
 * @author Marco Castigliego
 */
public class CommandCallable implements Callable<CommandResponse> {

	private final Profile profile;
	private final Command command;
	private final CommandExecutor executorTask;

	public CommandCallable(Profile profile, Command command) {
		this.profile = profile;
		this.command = command;
		this.executorTask = getCommandExecutor(profile.getServerDetails());
	}

	@Override
	public CommandResponse call() throws Exception {
		StopWatch clock = new StopWatch();
		clock.start();
		String result = this.executorTask.execute(this.command).andReturnResult();
		clock.stop();
		CommandResponse taskResult = new CommandResponse(profile, command, result, clock.getTime());
		return taskResult;
	}
}
