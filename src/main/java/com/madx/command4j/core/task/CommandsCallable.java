package com.madx.command4j.core.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.core.Command;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandResponse;
import com.madx.command4j.core.utils.thread.ForkController;

/**
 * Callable class used to run {@link CommandLauncher}s. When called:
 * <ol>
 * <li>Initialises the commandExecutor</li>
 * <li>Uses the {@link Ls} to create a list of files in case a wildcard is used. For instance using server.log* can return more files such as
 * server.log, server.log.gz, etc</li>
 * <li>Prepares grep commands ({@link GzGrepCommand} for compressed archives and {@link SimpleGrepCommand} for plain text ones)</li>
 * <li>Executes each grep command</li>
 * <li>Releases commandExecutor resources</li>
 * </ol>
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
public class CommandsCallable implements Callable<List<CommandResponse>> {

	private final Profile profile;
	private final List<Command> commands;

	public CommandsCallable(Profile profile, List<Command> commands) {
		this.profile = profile;
		this.commands = commands;
	}

	@Override
	public List<CommandResponse> call() {
		List<CommandResponse> results = new ArrayList<CommandResponse>();
		ExecutorService executorService = null;
		CompletionService<CommandResponse> completionService = null;
		try {
			executorService = Executors.newFixedThreadPool(ForkController.maxCommandTaskThreads(profile, commands.size()));
			completionService = new ExecutorCompletionService<CommandResponse>(executorService);
			for (Command command : commands) {
				completionService.submit(new CommandCallable(profile, command));
			}
			for (int i = 0; i < commands.size(); i++) {
				results.add(completionService.take().get());
			}
		} catch (Exception e) {
			throw new RuntimeException("Error when executing the CommandLauncher", e);
		} finally {
			if (executorService != null) {
				executorService.shutdownNow();
			}
		}
		return results;
	}

}
