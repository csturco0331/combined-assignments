package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.time.LocalTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.RequestType;
import com.cooksys.ftd.assignments.concurrency.model.message.Response;

public class ClientHandler implements Runnable {
	
	private final Socket client;
	private final String id;
	private final BufferedReader input;
	private StringReader inputReader;
	private final BufferedWriter output;
	private StringWriter outputWriter;

	public ClientHandler(Socket client) throws IOException {
		this.client = client;
		id = "Server " + client.getLocalSocketAddress();
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		outputWriter = new StringWriter();
	}

	@Override
	public void run() {
		try {
			
			JAXBContext jaxb = JAXBContext.newInstance(Request.class, Response.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Marshaller marshaller = jaxb.createMarshaller();
			//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			while(!client.isClosed()) {	
				System.out.println(id + " Waiting for request");
				//System.out.println(client.getLocalSocketAddress()+" : "+client.getRemoteSocketAddress());
				inputReader = new StringReader(input.readLine());
				Request request = (Request) unmarshaller.unmarshal(inputReader);
				RequestType message = request.getType();
				System.out.println(id + " Recieved " + message + " request");
				switch(message) {
				case DONE :
					System.out.println(id + " Sending DONE response");
					marshaller.marshal(new Response("Goodbye", RequestType.DONE, true), outputWriter);
					output.write(outputWriter.toString());
					outputWriter.getBuffer().setLength(0);
					output.newLine();
					output.flush();
					return;
				case TIME :
					System.out.println(id + " Sending TIME response");
					marshaller.marshal(new Response(LocalTime.now().toString(), RequestType.TIME, true), outputWriter);
					output.write(outputWriter.toString());
					outputWriter.getBuffer().setLength(0);
					output.newLine();
					output.flush();
					break;
				case IDENTITY :
					System.out.println(id + " Sending IDENTITY response");
					Response response = new Response(client.getLocalAddress().toString(), RequestType.IDENTITY, true);
					marshaller.marshal(response, outputWriter);
					output.write(outputWriter.toString());
					outputWriter.getBuffer().setLength(0);
					output.newLine();
					output.flush();
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
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
