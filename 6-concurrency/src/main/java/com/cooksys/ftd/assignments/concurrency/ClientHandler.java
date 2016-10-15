package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalTime;

import javax.xml.bind.JAXBException;

import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.RequestType;
import com.cooksys.ftd.assignments.concurrency.model.message.Response;
import com.google.gson.Gson;

public class ClientHandler implements Runnable {
	
	private final Socket client;
	private final String id;
	private final BufferedReader input;
	private final BufferedWriter output;
	private final Gson gson = new Gson();

	public ClientHandler(Socket client) throws IOException, JAXBException {
		this.client = client;
		id = "Server " + client.getLocalSocketAddress();
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	}
	
    private void close() throws IOException {
    	client.close();
		input.close();
		output.close();
    }
	
	private void sendResponce(String string, Response response) throws JAXBException, IOException {
		System.out.println(string);
		output.write(gson.toJson(response));
		output.newLine();
		output.flush();
	}

	@Override
	public void run() {
		try {			
			while(!client.isClosed()) {	
				switch(gson.fromJson(input.readLine(), Request.class).getType()) {
				case DONE :
					sendResponce(id + " Sending DONE response", new Response("Goodbye", RequestType.DONE, true));
					return;
				case TIME :
					sendResponce(id + " Sending TIME response", new Response(LocalTime.now().toString(), RequestType.TIME, true));
					break;
				case IDENTITY :
					sendResponce(id + " Sending IDENTITY response", new Response(client.getLocalAddress().toString(), RequestType.IDENTITY, true));
					break;
				default :
					return;
				}
			}
		}
		catch (NullPointerException e) {
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println(id + " has disconnected");
			Server.numClients.decrementAndGet();
			try {
				close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
