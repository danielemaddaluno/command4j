package com.madx.command4j.core.utils.pool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.madx.command4j.core.executors.ssh.JschUserInfo;
import com.madx.command4j.core.model.ServerDetails;

/**
 * This class is used to handle ssh Session inside the pool.
 * @author Daniele Maddaluno
 */
public class SessionFactory extends BaseKeyedPooledObjectFactory<ServerDetails, Session> {

	@Override
	public Session create(ServerDetails serverDetails) throws Exception {
		Session session = null;
		try {
			JSch jsch = new JSch();
			if (serverDetails.getPrivateKeyLocation() != null) {
				jsch.addIdentity(serverDetails.getPrivateKeyLocation());
			}
			session = jsch.getSession(serverDetails.getUser(), serverDetails.getHost(), serverDetails.getPort());
			session.setConfig("StrictHostKeyChecking", "no"); //
			UserInfo userInfo = new JschUserInfo(serverDetails.getUser(), serverDetails.getPassword());
			session.setUserInfo(userInfo);
			session.setTimeout(60000);
			session.setPassword(serverDetails.getPassword());
			session.connect();
		} catch (Exception e) {
			throw new RuntimeException(
					"ERROR: Unrecoverable error when trying to connect to serverDetails :  "
							+ serverDetails, e);
		}
		return session;
	}

	@Override
	public PooledObject<Session> wrap(Session value) {
		return new DefaultPooledObject<Session>(value);
	}
	
	/**
	 * This is called when closing the pool object
	 */
	@Override
	public void destroyObject(ServerDetails serverDetails, PooledObject<Session> session) {
		session.getObject().disconnect();
	}

}
