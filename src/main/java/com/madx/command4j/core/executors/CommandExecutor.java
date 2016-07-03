package com.madx.command4j.core.executors;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.executors.local.LocalCommandExecutor;
import com.madx.command4j.core.executors.ssh.JschCommandExecutor;
import com.madx.command4j.core.model.ServerDetails;

/**
 * Base class for executing commands against a Server
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
public abstract class CommandExecutor {

	protected final ServerDetails serverDetails;

	protected final StringBuilder result;

	public CommandExecutor(ServerDetails serverDetails) {
		this.serverDetails = serverDetails;
		this.result = new StringBuilder();
	}

	public abstract CommandExecutor execute(Command command);

	/**
	 * @return the result of the command in a String format
	 */
	public String andReturnResult() {
		return result.toString();
	}
	
	/**
	 * Based on the server details, it returns {@link LocalCommandExecutor} if the host is "localhost" or "127.0.0.1" otherwise return
	 * {@link JschCommandExecutor}
	 * 
	 * @param serverDetails
	 * @return {@link CommandExecutor}
	 */
	public static CommandExecutor getCommandExecutor(ServerDetails serverDetails) {
		CommandExecutor commandExecutor = null;
		if (serverDetails.isLocalhost()) {
			commandExecutor = new LocalCommandExecutor(serverDetails);
		} else {
			commandExecutor = new JschCommandExecutor(serverDetails);
		}
		return commandExecutor;
	}

}
