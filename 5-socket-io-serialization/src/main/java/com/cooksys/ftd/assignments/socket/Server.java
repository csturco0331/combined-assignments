package com.cooksys.ftd.assignments.socket;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Server implements Runnable {

	/**
	 * Reads a {@link Student} object from the given file path
	 *
	 * @param studentFilePath
	 *            the file path from which to read the student config file
	 * @param jaxb
	 *            the JAXB context to use during unmarshalling
	 * @return a {@link Student} object unmarshalled from the given file path
	 */
	public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException {
		File file = new File(studentFilePath);
		if (jaxb == null)
			jaxb = JAXBContext.newInstance(Student.class);
		Unmarshaller jaxBUnmarshaller = jaxb.createUnmarshaller();
		return (Student) jaxBUnmarshaller.unmarshal(file);
	}

	/**
	 * The server should load a
	 * {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" property of the
	 * embedded {@link com.cooksys.ftd.assignments.socket.model.LocalConfig}
	 * object to create a server socket that listens for connections on the
	 * configured port.
	 *
	 * Upon receiving a connection, the server should unmarshal a
	 * {@link Student} object from a file location specified by the config's
	 * "studentFilePath" property. It should then re-marshal the object to xml
	 * over the socket's output stream, sending the object to the client.
	 *
	 * Following this transaction, the server may shut down or listen for more
	 * connections.
	 */
	public void run() {

		try {

			// JAXB setup
			JAXBContext jaxb = JAXBContext.newInstance(Student.class, Config.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// config file
			File file = new File("config/config.xml");

			// unmarshal config file to get configuration settings
			Config config = (Config) unmarshaller.unmarshal(file);

			// create the server socket
			ServerSocket ss = new ServerSocket(config.getLocal().getPort());

			// wait for client to connect and get socket from client
			System.out.println("Waiting for connection....");
			Socket s = ss.accept();
			System.out.println("Accepted connection from " + s.getLocalAddress().toString());

			// unmarshall student, then marshall it again, sending it to the
			// sockets outputstream
			Student student = (Student) unmarshaller.unmarshal(new File(config.getStudentFilePath()));
			marshaller.marshal(student, s.getOutputStream());

			// close the serversocket
				s.close();
				ss.close();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
