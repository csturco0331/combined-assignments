package com.cooksys.serialization.assignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.serialization.assignment.model.Contact;
import com.cooksys.serialization.assignment.model.Instructor;
import com.cooksys.serialization.assignment.model.Session;
import com.cooksys.serialization.assignment.model.Student;

public class Main {

	/**
	 * Creates a {@link Student} object using the given studentContactFile. The
	 * studentContactFile should be an XML file containing the marshaled form of
	 * a {@link Contact} object.
	 *
	 * @param studentContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Student} object built using the {@link Contact} data in
	 *         the given file
	 */
	public static Student readStudent(File studentContactFile, JAXBContext jaxb) throws JAXBException, IOException {
		if (!studentContactFile.getName().endsWith(".xml"))
			throw new IOException("readStudent: File is not an xml");
		if (jaxb == null)
			jaxb = JAXBContext.newInstance(Contact.class);
		Unmarshaller jaxBUnmarshaller = jaxb.createUnmarshaller();
		return new Student((Contact) jaxBUnmarshaller.unmarshal(studentContactFile));
	}

	/**
	 * Creates a list of {@link Student} objects using the given directory of
	 * student contact files.
	 *
	 * @param studentDirectory
	 *            the directory of student contact files to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a list of {@link Student} objects built using the contact files
	 *         in the given directory
	 */
	public static List<Student> readStudents(File studentDirectory, JAXBContext jaxb)
			throws JAXBException, IOException {
		if (!studentDirectory.isDirectory())
			throw new IOException("readStudents: File is not a directory");
		if (jaxb == null)
			jaxb = JAXBContext.newInstance(Contact.class);
		Unmarshaller jaxBUnmarshaller = jaxb.createUnmarshaller();
		List<Student> results = new ArrayList<>();
		for (File file : studentDirectory.listFiles()) {
			if (file.getName().endsWith(".xml")) {
				results.add(new Student((Contact) jaxBUnmarshaller.unmarshal(file)));
			}
		}
		return results; // TODO
	}

	/**
	 * Creates an {@link Instructor} object using the given
	 * instructorContactFile. The instructorContactFile should be an XML file
	 * containing the marshaled form of a {@link Contact} object.
	 *
	 * @param instructorContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return an {@link Instructor} object built using the {@link Contact} data
	 *         in the given file
	 */
	public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxb)
			throws JAXBException, IOException {
		if (!instructorContactFile.getName().endsWith(".xml"))
			throw new IOException("readStudent: File is not an XML");
		if (jaxb == null)
			jaxb = JAXBContext.newInstance(Contact.class);
		Unmarshaller jaxBUnmarshaller = jaxb.createUnmarshaller();
		return new Instructor((Contact) jaxBUnmarshaller.unmarshal(instructorContactFile));
	}

	/**
	 * Creates a {@link Session} object using the given rootDirectory. A
	 * {@link Session} root directory is named after the location of the
	 * {@link Session}, and contains a directory named after the start date of
	 * the {@link Session}. The start date directory in turn contains a
	 * directory named `students`, which contains contact files for the students
	 * in the session. The start date directory also contains an instructor
	 * contact file named `instructor.xml`.
	 *
	 * @param rootDirectory
	 *            the root directory of the session data, named after the
	 *            session location
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Session} object built from the data in the given
	 *         directory
	 */
	public static Session readSession(File rootDirectory, JAXBContext jaxb) throws JAXBException, IOException {
		if (!rootDirectory.isDirectory())
			throw new IOException("readSession: File is not a directory");
		if (jaxb == null)
			jaxb = JAXBContext.newInstance(Contact.class);
		Session result = new Session();
		result.setLocation(rootDirectory.getName());
		result.setStartDate(rootDirectory.listFiles()[0].getName());
		result.setInstructor(readInstructor(rootDirectory.listFiles()[0].listFiles()[0], jaxb));
		result.setStudents(readStudents(rootDirectory.listFiles()[0].listFiles()[1], jaxb));
		return result; // TODO
	}

	/**
	 * Writes a given session to a given XML file
	 *
	 * @param session
	 *            the session to write to the given file
	 * @param sessionFile
	 *            the file to which the session is to be written
	 * @param jaxb
	 *            the JAXB context to use
	 */
	public static void writeSession(Session session, File sessionFile, JAXBContext jaxb)
			throws JAXBException, IOException {
		if (!sessionFile.getName().endsWith(".xml")) 
			throw new IOException("writeSession: File is not an XML");
		if(jaxb == null)
			jaxb = JAXBContext.newInstance(Session.class);
		Marshaller marshaller = jaxb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(session, sessionFile);
	}

	/**
	 * Main Method Execution Steps: 1. Configure JAXB for the classes in the
	 * com.cooksys.serialization.assignment.model package 2. Read a session
	 * object from the <project-root>/input/memphis/ directory using the methods
	 * defined above 3. Write the session object to the
	 * <project-root>/output/session.xml file.
	 *
	 * JAXB Annotations and Configuration: You will have to add JAXB annotations
	 * to the classes in the com.cooksys.serialization.assignment.model package
	 *
	 * Check the XML files in the <project-root>/input/ directory to determine
	 * how to configure the {@link Contact} JAXB annotations
	 *
	 * The {@link Session} object should marshal to look like the following:
	 * <session location="..." start-date="..."> <instructor> <contact>...
	 * </contact> </instructor> <students> ...
	 * <student> <contact>...</contact> </student> ... </students> </session>
	 */
	public static void main(String[] args) {

		try {
			JAXBContext jaxb = JAXBContext.newInstance(Contact.class, Session.class);
			File file = new File("input/memphis");
			Session session = readSession(file, jaxb);
			writeSession(session, new File("output/session.xml"), jaxb);
		} catch (JAXBException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}
}
