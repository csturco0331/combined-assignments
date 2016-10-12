package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.Response;

public class ClientInstance implements Runnable {

	private final int delay;
	private final List<Request> requests;
	private final Socket socket;
	private final BufferedReader input;
	private StringReader inputReader;
	private final BufferedWriter output;
	private final StringWriter outputWriter;
	
    public ClientInstance(ClientInstanceConfig config, int port, String host) throws IOException {
        delay = config.getDelay();
        requests = config.getRequests();
        socket = new Socket(host, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		outputWriter = new StringWriter();
    }

    @Override
    public void run() {
        try {
        	
        	JAXBContext jaxb = JAXBContext.newInstance(Request.class, Response.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Marshaller marshaller = jaxb.createMarshaller();
			//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			for(Request request : requests) {
				Thread.sleep(delay);
				//System.out.println(request.getType());		
				marshaller.marshal(request, outputWriter);
				output.write(outputWriter.toString());
				outputWriter.getBuffer().setLength(0);
				output.newLine();
				output.flush();
				//System.out.println("Testing");
				//System.out.println("Instance" + socket.getLocalSocketAddress()+" : "+socket.getRemoteSocketAddress());
				inputReader = new StringReader(input.readLine());
				Response response = (Response)unmarshaller.unmarshal(inputReader);
				System.out.println("Client : Response " + response.getData() + " recieved");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("ClientInstance is disconnecting");
			try {
				socket.close();
				input.close();
				output.close();
				inputReader.close();
				outputWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
    }
}
