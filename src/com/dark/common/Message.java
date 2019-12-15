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

public abstract class Message implements Serializable{
	/**
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
		//OutputStreamWriter out;
		ObjectOutputStream out;
		try {
			//out = new OutputStreamWriter(socket.getOutputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			logger.info("Sending message: " + this.toString());
			//out.write(this.toString() + "\n");
			out.writeObject(this);
			out.flush();
		} catch (IOException e) {
			logger.warning(e.toString());
		}
	}
	
	public static Message receive(Socket socket) throws ClassNotFoundException {
		//BufferedReader in;
		ObjectInputStream in;
		Message msg = null;
		try {
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			in = new ObjectInputStream(socket.getInputStream());
			//String msgText = in.readLine(); // Will wait here for complete line
			msg = (Message)in.readObject(); // delete
			String msgText = msg.toString();
			logger.info("Receiving message: " + msgText);
			
//			// Parse message
//			String[] parts = msgText.split("\\|");
//			if (parts[0].equals(MessageType.Join.toString())) {
//				msg = new JoinMsg(parts[1]);
//			} else if (parts[0].equals(MessageType.Chat.toString())) {
//				msg = new ChatMsg(parts[1], parts[2]);
//			} else if (parts[0].equals(MessageType.Turn.toString())) {
//				msg = new TurnMsg(Integer.parseInt(parts[1]), parts[2]);
//			}  else if (parts[0].equals(MessageType.Pos.toString())) {
//				msg = new PosMsg(Integer.parseInt(parts[1]));
//			}			
		} catch (IOException e) {
			logger.warning(e.toString());
		}
		return msg;
	}

	public MessageType getType() {
		return type;
	}
}
