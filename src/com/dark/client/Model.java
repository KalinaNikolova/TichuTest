package com.dark.client;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import com.dark.common.ChatMsg;
import com.dark.common.JoinMsg;
import com.dark.common.Message;
import com.dark.common.PosMsg;
import com.dark.common.TurnMsg;

import javafx.beans.property.SimpleStringProperty;
// this class has been taken from ch.fhnw.richards.lecture14_chatLab.v3_client
//and modified to fit our project
/**
 * Note: One error in this implementation: The *display* of received messages is triggered
 * by a ChangeListener attached to the SimpleStringProperty. If the newly received message
 * is *identical* to the current contents of the SimpleStringProperty, then there is no
 * change, and the new (duplicate) message is not displayed.
 */
public class Model {
	protected SimpleStringProperty newestMessage = new SimpleStringProperty();
	protected SimpleStringProperty newestName = new SimpleStringProperty();
	protected SimpleStringProperty newestCards = new SimpleStringProperty();
	protected Turn newestTurn = new Turn();
	
	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private String name;
	private int position;

	public void connect(String ipAddress, int Port, String name) {
		logger.info("Connect");
		this.name = name;
		try {
			socket = new Socket(ipAddress, Port);

			// Create thread to read incoming messages
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (true) {
						Message message = Message.receive(socket);
						if (message instanceof ChatMsg) {				
							ChatMsg msg = (ChatMsg) message;
							newestMessage.set(""); // erase previous message
							newestMessage.set(msg.getName() + ": " + msg.getContent());
						} else if (message instanceof JoinMsg) {
							JoinMsg msg = (JoinMsg)message;
							newestName.set(msg.getName());
						} else if (message instanceof TurnMsg) {
							TurnMsg msg = (TurnMsg)message;
							newestTurn.setTurn(msg.getPosition());
						} else if (message instanceof PosMsg) {
							PosMsg msg = (PosMsg)message;
							position=msg.getPosition();
						}
					}
				}
			};
			Thread t = new Thread(r);
			t.start();

			// Send join message to the server
			Message msg = new JoinMsg(name);
			msg.send(socket);
		} catch (Exception e) {
			logger.warning(e.toString());
		}
	}

	public void disconnect() {
		logger.info("Disconnect");
		if (socket != null)
			try {
				socket.close();
			} catch (IOException e) {
				// Uninteresting
			}
	}
	
	public void sendMessage(String message) {	
		if(message.equals("pass")) {	
				logger.info("Send turn");
				TurnMsg msg = new TurnMsg(position, message);
				msg.send(socket);
		}else {
			logger.info("Send message");
			Message msg = new ChatMsg(name, message);
			msg.send(socket);
		}

	}
}
