package com.cooksys.ftd.assignments.concurrency;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;

public class Client implements Runnable {

	private final int port;
	private final String host;
	private boolean disabled;
	private int maxInstances;
	private SpawnStrategy spawnStrategy;
	private List<ClientInstanceConfig> instances;
	
	public static AtomicInteger numInstances = new AtomicInteger(0);
	
    public Client(ClientConfig config) {
        port = config.getPort();
        host = config.getHost();
        disabled = config.isDisabled();
        maxInstances = config.getMaxInstances();
        spawnStrategy = config.getSpawnStrategy();
        instances = config.getInstances();
    }

    @Override
    public void run() {
    	if(!disabled) {
    		while(!instances.isEmpty()) {
    			if(maxInstances < 0 || numInstances.get() <= maxInstances) {
    				switch(spawnStrategy) {
    				case NONE :
    					return;
    				case PARALLEL :
    					numInstances.incrementAndGet();
        				try {
							new Thread(new ClientInstance(instances.remove(0), port, host)).start();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					break;
    				case SEQUENTIAL :
    					numInstances.incrementAndGet();
    					try {
							new ClientInstance(instances.remove(0), port, host).run();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					break;
    				default :
    					return;
    				}
    			}
    		}
    	}
    }
}
