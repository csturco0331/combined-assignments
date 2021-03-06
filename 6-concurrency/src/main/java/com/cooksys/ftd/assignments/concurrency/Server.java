package com.cooksys.ftd.assignments.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBException;

import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;

public class Server implements Runnable {

	private final int maxClients;
	private static ServerSocket ss;

	static public AtomicInteger numClients = new AtomicInteger(0);

	public Server(ServerConfig config) throws IOException {
		this.maxClients = config.getMaxClients();
		ss = new ServerSocket(config.getPort());
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (maxClients < 0 || numClients.get() < maxClients) {
					Socket s = ss.accept();
					numClients.incrementAndGet();
					new Thread(new ClientHandler(s)).start();
					System.out.println("Connection established with " + s.getInetAddress());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
