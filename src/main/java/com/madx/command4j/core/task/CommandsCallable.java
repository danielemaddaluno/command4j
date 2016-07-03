package com.madx.command4j.core.task;

import java.util.List;
import java.util.concurrent.Callable;

import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.core.Command;
import com.madx.command4j.core.launchers.CommandLauncher;
import com.madx.command4j.core.launchers.Launcher;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.response.CommandResponse;

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
	private final Launcher<List<CommandResponse>, Profile, List<Command>> grepTaskExecutor;
	
//	private final GrepRequest grepRequest;
//	private final List<String> matchingFiles;
//	private final List<CommandGrep> grepCommandsList;
//	private final FileList fileList;
//
//	public GrepTask(GrepRequest grepRequest) {
//		this.grepRequest = grepRequest;
//		this.matchingFiles = new ArrayList<String>();
//		this.grepCommandsList = new ArrayList<CommandGrep>();
//		// TODO here grepCommandsList is passed by reference and then update in the method prepareGrepCommands... try to make it better
//		this.grepTaskExecutor = new GrepTaskExecutor(grepCommandsList);
//		this.fileList = new FileList();
//	}

	public CommandsCallable(Profile profile, List<Command> commands) {
		this.profile = profile;
		this.commands = commands;
		this.grepTaskExecutor = new CommandLauncher();
	}

	@Override
	public List<CommandResponse> call() {
//		listMatchingFiles();
//		prepareGrepCommands();
		return grepTaskExecutor.launch(profile, commands);
	}

//	/**
//	 * In case the file to grep contains a wildcard (EXAMPLE server.log*), 
//	 * we run first an LS command to separate each file which will then be treated
//	 * in a separated Grepresult
//	 */
//	private void listMatchingFiles() {
//		matchingFiles.addAll(fileList.list(grepRequest));
//	}
//
//	private void prepareGrepCommands() {
//		for (String filename : matchingFiles) {
//			if (filename.trim().isEmpty()) {
//				continue;
//			}
//			CommandGrep grepCommand;
//			if (isGz(filename)) {
//				grepCommand = new GzGrepCommand(grepRequest, filename);
//			} else {
//				grepCommand = new SimpleGrepCommand(grepRequest, filename);
//			}
//			grepCommand.setContextControls(grepRequest.getContextControls());
//			grepCommand.setTailContextControls(grepRequest.getTailContextControls());
//			grepCommandsList.add(grepCommand);
//		}
//	}
//
//	private boolean isGz(String matchingFile) {
//		return matchingFile.endsWith(".gz");
//	}

}
