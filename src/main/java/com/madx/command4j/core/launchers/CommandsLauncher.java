package com.madx.command4j.core.launchers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.time.StopWatch;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandResponse;
import com.madx.command4j.core.response.CommandsResponses;
import com.madx.command4j.core.task.CommandsCallable;
import com.madx.command4j.core.utils.pool.StackSessionPool;
import com.madx.command4j.core.utils.thread.ForkController;

/**
 * This class run one GrepTask Thread for each grepRequest.
 * 
 * @author Daniele Maddaluno
 *
 */
@ThreadSafe
public class CommandsLauncher implements Launcher<CommandsResponses, List<Profile>, List<Command>>{
	
	private final StopWatch clock;
	
	public CommandsLauncher() {
		this.clock = new StopWatch();
	}

	@Override
	public CommandsResponses launch(List<Profile> profiles, List<Command> commands) {
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
			throw new RuntimeException("Error when executing the GrepTask", e);
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

}
