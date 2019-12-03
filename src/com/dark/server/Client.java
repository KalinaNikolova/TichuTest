package com.dark.server;
/**
 * This class has been taken from ch.fhnw.richards.lecture14_chatLab.v3_commons;
 * Small changes in the checks of the type of message;
 */
import java.io.IOException;
import java.net.Socket;

import com.dark.common.ChatMsg;
import com.dark.common.JoinMsg;
import com.dark.common.Message;
import com.dark.common.TurnMsg;

public class Client {
	private Socket socket;
	private String name = "<new>";
//	private Model model;

	protected Client(Model model, Socket socket) {
//		this.model = model;
		this.socket = socket;

		// Create thread to read incoming messages
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while(true) {
					Message msg = Message.receive(socket);
					if (msg instanceof ChatMsg) {				
						model.broadcast((ChatMsg) msg);
					} else if (msg instanceof JoinMsg) {
						Client.this.name = ((JoinMsg) msg).getName();
						model.broadcast((JoinMsg) msg);
					} else if (msg instanceof TurnMsg) {
						model.broadcast((TurnMsg) msg);
					}
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}
	public void send(Message msg) {
		msg.send(socket);
	}
	public void stop() {
		try {
			socket.close();
		} catch (IOException e) {
			// Uninteresting
		}
	}

	@Override
	public String toString() {
		return name + ": " + socket.toString();
	}
}
