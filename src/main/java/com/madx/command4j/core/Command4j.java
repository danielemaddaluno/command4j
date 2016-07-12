package com.madx.command4j.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.time.StopWatch;

import com.google.common.collect.ImmutableList;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandResponse;
import com.madx.command4j.core.response.CommandsResponses;
import com.madx.command4j.core.task.CommandsCallable;
import com.madx.command4j.core.utils.pool.StackSessionPool;
import com.madx.command4j.core.utils.thread.ForkController;

/**
 * Study this: http://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/
 * for running
 * @author Daniele Maddaluno
 *
 */
public final class Command4j {

	private final List<Profile> profiles;
	private final List<Command> commands;

	private Command4j(List<Profile> profiles, List<Command> commands) {
		this.commands = commands;
		this.profiles = ImmutableList.copyOf(profiles);
	}

	public static CommandsResponses execute(List<Profile> profiles, List<Command> commands) {
		return new Command4j(profiles, commands).execute();
	}

	public static CommandsResponses execute(Profile profile, List<Command> commands) {
		return execute(Collections.singletonList(profile), commands);
	}

	public static CommandsResponses execute(List<Profile> profiles, Command command) {
		return execute(profiles, Collections.singletonList(command));
	}

	public static CommandsResponses execute(Profile profile, Command command) {
		return execute(Collections.singletonList(profile), Collections.singletonList(command));
	}

	private CommandsResponses execute() {
		verifyInputs();

		StopWatch clock = new StopWatch();
		CommandsResponses results = new CommandsResponses();
		ExecutorService executorService = null;
		StackSessionPool.getInstance().startPool();
		try {
			clock.start();
			executorService = Executors.newFixedThreadPool(ForkController.maxCommandExecutorTaskThreads(profiles.size()));
			List<CommandsCallable> tasks = new ArrayList<CommandsCallable>();

			for(Profile profile : profiles){
				tasks.add(new CommandsCallable(profile, commands));
			}

			List<Future<List<CommandResponse>>> grepTaskFutures = executorService.invokeAll(tasks);
			for (Future<List<CommandResponse>> future : grepTaskFutures) {
				for (CommandResponse singleGrepResult : future.get())
					results.add(singleGrepResult);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error when executing the CommandTask", e);
		} finally {
			clock.stop();
			results.setExecutionTime(clock.getTime());
			if (executorService != null) {
				executorService.shutdownNow();
			}
			try {
				StackSessionPool.getInstance().getPool().close();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
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
