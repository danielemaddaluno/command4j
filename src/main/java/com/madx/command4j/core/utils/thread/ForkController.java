package com.madx.command4j.core.utils.thread;

import org.apache.commons.lang3.ObjectUtils;

import com.madx.command4j.core.model.Profile;

/**
 * This to control the maximum number of threads to be used when forking tasks
 * 
 * @author Daniele Maddaluno
 */
public class ForkController {

	private static int MAX_GREPTASK_THREADS = 10;
	private static int MAX_COMMANDEXECUTORTASK_THREADS = 5;

	public static int maxCommandTaskThreads(Profile profile, int totGrepTasks) {
		return ObjectUtils.min(profile.getMaxThreads(), totGrepTasks, MAX_GREPTASK_THREADS);
	}

	public static int maxCommandExecutorTaskThreads(int totGrepTasks) {
		return Math.min(totGrepTasks, MAX_COMMANDEXECUTORTASK_THREADS);
	}
}
