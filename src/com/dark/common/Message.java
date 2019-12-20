package com.dark.common;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class Message implements Serializable{// using serialization
	/**
	 * The body of this class was taken from WI_SoftwareEngineering-master.zip from Bradley Richards
	 * chatLab examples
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger("");
	
	protected MessageType type;
	
	public Message(MessageType type) {
		this.type = type;
	}
	
	public void send(Socket socket) {
		//try with resources
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());// Using streams
			logger.info("Sending message: " + this.toString());
			out.writeObject(this);
			out.flush();
		} catch (IOException e) {
			logger.warning(e.toString());
		}
	}
	
	public static Message receive(Socket socket) throws ClassNotFoundException {
		ObjectInputStream in;
		Message msg = null;
		try {
			
			in = new ObjectInputStream(socket.getInputStream());// using stream
			msg = (Message)in.readObject(); // Will wait here for complete line
			String msgText = msg.toString();
			logger.info("Receiving message: " + msgText);
			
			
		} catch (IOException e) {
			logger.warning(e.toString());
		}
		return msg;
	}

	public MessageType getType() {
		return type;
	}
}
