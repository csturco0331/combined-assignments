package com.cooksys.ftd.assignments.socket;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client implements Runnable {

	/**
	 * The client should load a
	 * {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" and "host"
	 * properties of the embedded
	 * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to
	 * create a socket that connects to a {@link Server} listening on the given
	 * host and port.
	 *
	 * The client should expect the server to send a
	 * {@link com.cooksys.ftd.assignments.socket.model.Student} object over the
	 * socket as xml, and should unmarshal that object before printing its
	 * details to the console.
	 */
	public void run() {

		try {
			// JAXB setup
			JAXBContext jaxb = JAXBContext.newInstance(Student.class, Config.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();

			// config file
			File file = new File("config/config.xml");

			// unmarshal config file to get configuration settings
			Config config = (Config) unmarshaller.unmarshal(file);
			while (true) {
				try {
					Socket s = new Socket(config.getRemote().getHost(), config.getRemote().getPort());
					Student student = (Student) unmarshaller.unmarshal(s.getInputStream());
					System.out.println(student);

					s.close();
				} catch (ConnectException e) {
					try {
						Thread.sleep(1000*1);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					continue;
				}
				break;
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
