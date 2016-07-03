package com.madx.command4j.core.launchers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.concurrent.ThreadSafe;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandResponse;
import com.madx.command4j.core.task.CommandCallable;
import com.madx.command4j.core.utils.thread.ForkController;

/**
 * This class run one CommandExecutorTask Thread for each file existing in the grepCommandsList
 * 
 * @author Daniele Maddaluno
 *
 */
@ThreadSafe
public class CommandLauncher implements Launcher<List<CommandResponse>, Profile, List<Command>>{

	@Override
	public List<CommandResponse> launch(Profile profile, List<Command> commands) {
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
