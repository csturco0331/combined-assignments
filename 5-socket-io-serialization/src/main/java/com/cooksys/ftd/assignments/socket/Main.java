package com.cooksys.ftd.assignments.socket;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Thread server = new Thread(new Server());
		Thread client = new Thread(new Client());
		
		
		client.start();
		Thread.sleep(1000*10);
		server.start();
	}

}
