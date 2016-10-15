package com.cooksys.ftd.assignments.concurrency;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;

public class Client implements Runnable {

	private final int port;
	private final String host;
	private final int maxInstances;
	private final SpawnStrategy spawnStrategy;
	private final List<ClientInstanceConfig> instances;
	
	public static AtomicInteger numInstances = new AtomicInteger(0);
	
    public Client(ClientConfig config) {
        port = config.getPort();
        host = config.getHost();
        maxInstances = config.getMaxInstances();
        spawnStrategy = config.getSpawnStrategy();
        instances = config.getInstances();
    }

    @Override
    public void run() {
		try {
			while(!instances.isEmpty()) {
				if(maxInstances < 0 || numInstances.get() < maxInstances) {
					switch(spawnStrategy) {
					case NONE :
						return;
					case PARALLEL :
						numInstances.incrementAndGet();
						new Thread(new ClientInstance(instances.remove(0), port, host)).start();
						break;
					case SEQUENTIAL :
						numInstances.incrementAndGet();
						new ClientInstance(instances.remove(0), port, host).run();
						break;
					default :
						return;
					}
				}
			}
		} catch (Exception e) {
	    	e.printStackTrace();
	    }
    } 
}
