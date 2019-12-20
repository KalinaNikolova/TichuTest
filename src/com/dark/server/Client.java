package com.dark.server;

import java.io.IOException;
import java.net.Socket;

import com.dark.common.ChatMsg;
import com.dark.common.JoinMsg;
import com.dark.common.Message;
import com.dark.common.PlayMsg;
import com.dark.common.TurnMsg;
/**
 *Body taken from WI_SoftwareEngineering-master.zip Bradley Richards chatLab
 */
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
					Message msg=null;//null;
					try {
						msg = Message.receive(socket);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (msg instanceof ChatMsg) {				
						model.broadcast((ChatMsg) msg);
					} else if (msg instanceof JoinMsg) {

							model.broadcast((JoinMsg) msg);

							
					} else if (msg instanceof TurnMsg) {
						model.broadcast((TurnMsg) msg);
					} else if (msg instanceof PlayMsg) {
						model.broadcast((PlayMsg) msg);
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
