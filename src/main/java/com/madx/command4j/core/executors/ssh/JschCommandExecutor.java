package com.madx.command4j.core.executors.ssh;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.madx.command4j.core.Command;
import com.madx.command4j.core.executors.CommandExecutor;
import com.madx.command4j.core.model.ServerDetails;
import com.madx.command4j.core.utils.pool.StackSessionPool;

/**
 * The SshCommandExecutor uses the net.schmizz.sshj library to execute remote commands.
 * <ol>
 * <li>Establish a connection using the credential in the {@link serverDetails}</li>
 * <li>Opens a session channel</li>
 * <li>Execute a command on the session</li>
 * <li>Closes the session</li>
 * <li>Disconnects</li>
 * </ol>
 * 
 * @author Marco Castigliego
 */
public class JschCommandExecutor extends CommandExecutor {

	public JschCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public CommandExecutor execute(Command command) {
		Session session = null;
		Channel channel = null;
		try {

			session = StackSessionPool.getInstance().getPool().borrowObject(serverDetails);
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command.toString());
			channel.setXForwarding(true);
			channel.setInputStream(null);
			InputStream in = channel.getInputStream();
			channel.connect();
			result.append(IOUtils.toString(in, StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new RuntimeException("ERROR: Unrecoverable error when performing remote command " + e.getMessage(), e);
		} finally {
			if (null != channel && channel.isConnected()) {
				channel.disconnect();
			}
			if (null != session) {
				try {
					StackSessionPool.getInstance().getPool().returnObject(serverDetails, session);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return this;
	}

}
