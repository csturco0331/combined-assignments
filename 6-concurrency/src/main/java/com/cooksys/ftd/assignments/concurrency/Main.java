package com.cooksys.ftd.assignments.concurrency;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.cooksys.ftd.assignments.concurrency.model.config.Config;

public class Main {

    /**
     * First, load a {@link com.cooksys.ftd.assignments.concurrency.model.config.Config} object from
     * the <project-root>/config/config.xml file.
     *
     * If the embedded {@link com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig} object
     * is not disabled, create a {@link Server} object with the server config and spin off a thread to run it.
     *
     * If the embedded {@link com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig} object
     * is not disabled, create a {@link Client} object with the client config and spin off a thread to run it.
     * @throws JAXBException 
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws JAXBException, IOException, InterruptedException {
    	
    	Config config = Config.load(new File("config/config.xml").toPath());
    	Thread server = new Thread(new Server(config.getServer()));
    	Thread client = new Thread(new Client(config.getClient()));
    	if(!config.getServer().isDisabled())
    		server.start();
    	if(!config.getClient().isDisabled())
    		client.start();
    	client.join();
    	System.out.println("Client has closed");
    	server.join();
    	System.out.println("server stopped");
    }
}
