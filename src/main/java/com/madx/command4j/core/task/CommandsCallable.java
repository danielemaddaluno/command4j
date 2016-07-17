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
import com.madx.command4j.core.CommandDemux;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandResponse;

/**
 * Callable class used to run {@link Command}s. When called:
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
 * @author Daniele Maddaluno
 */
public class CommandsCallable implements Callable<List<CommandResponse>> {

	private final Profile profile;
	private final List<Command> commands;

	public CommandsCallable(Profile profile, List<Command> commands) {
		this.profile = profile;
		this.commands = commands;
	}

	@Override
	public List<CommandResponse> call() throws Exception {
		List<CommandResponse> results = new ArrayList<CommandResponse>();

		for(Command command : commands){
			// if the command does not contain a regex simply execute it synchonously
			if(!command.containsRegex()){
				results.add(new CommandCallable(profile, command).call());
			// otherwise split the command in all the commands from regex and execute all the commands
			} else {
				List<Command> regexCommands = CommandDemux.commandsDemux(profile, command);
				ExecutorService executorService = null;
				CompletionService<CommandResponse> completionService = null;
				try {
					executorService = Executors.newFixedThreadPool(1); // FIXME correct with something like: Executors.newFixedThreadPool(ForkController.maxCommandTaskThreads(profile, regexCommands.size()));
					completionService = new ExecutorCompletionService<CommandResponse>(executorService);
					for (Command regexCommand : regexCommands) {
						completionService.submit(new CommandCallable(profile, regexCommand));
					}
					for (int i = 0; i < regexCommands.size(); i++) {
						results.add(completionService.take().get());
					}
				} catch (Exception e) {
					throw new RuntimeException("Error when executing the CommandLauncher", e);
				} finally {
					if (executorService != null) {
						executorService.shutdownNow();
					}
				}
			}
		}
		return results;
	}

}
