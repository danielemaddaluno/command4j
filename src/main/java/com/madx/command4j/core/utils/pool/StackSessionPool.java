package com.madx.command4j.core.utils.pool;

import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import com.jcraft.jsch.Session;
import com.madx.command4j.core.model.ServerDetails;

/**
 * Pool controller. This class exposes the org.apache.commons.pool.KeyedObjectPool class.
 * 
 * @see https://www.javacodegeeks.com/2013/02/pool-of-ssh-connections-using-apache-keyedobjectpool.html
 * @author Marco Castigliego
 * @author Daniele Maddaluno
 */
public class StackSessionPool {

	private KeyedObjectPool<ServerDetails, Session> pool;
	
    private static final StackSessionPool instance = new StackSessionPool();

    private StackSessionPool(){
    	startPool();
    }

    public static StackSessionPool getInstance() {
        return instance;
    }

	/**
	 * @return the org.apache.commons.pool.KeyedObjectPool class
	 */
	public KeyedObjectPool<ServerDetails, Session> getPool() {
		return pool;
	}

	/**
	 * @return the org.apache.commons.pool.KeyedObjectPool class
	 */
	public void startPool() {
		// replaced this from 1.x: new StackKeyedObjectPool<ServerDetails, Session>(new SessionFactory(), 1);
		GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
		config.setMaxTotalPerKey(1);
		pool = new GenericKeyedObjectPool<ServerDetails, Session>(new SessionFactory(), config);
	}

}
