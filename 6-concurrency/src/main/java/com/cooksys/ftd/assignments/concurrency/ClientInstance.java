package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.Response;
import com.google.gson.Gson;

public class ClientInstance implements Runnable {

	private final int delay;
	private final List<Request> requests;
	private final Socket socket;
	private final BufferedReader input;
	private final BufferedWriter output;
	private final Gson gson = new Gson();
	
    public ClientInstance(ClientInstanceConfig config, int port, String host) throws IOException, JAXBException {
        delay = config.getDelay();
        requests = config.getRequests();
        socket = new Socket(host, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    private void close() throws IOException {
    	socket.close();
		input.close();
		output.close();
    }
    
    private void sendRequest(Request request) throws JAXBException, IOException {
		output.write(gson.toJson(request));
		output.newLine();
		output.flush();
    }

    private void getResponse() throws IOException, JAXBException {
		Response response = gson.fromJson(input.readLine(), Response.class);
		System.out.println("Client : Response " + response.getData() + " recieved");
    }
    
    @Override
    public void run() {
        try {			
			for(Request request : requests) {
				Thread.sleep(delay);
				sendRequest(request);
				getResponse();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("ClientInstance is disconnecting");
			try {
				close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
    }
}
