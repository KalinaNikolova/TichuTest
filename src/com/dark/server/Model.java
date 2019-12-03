package com.dark.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import com.dark.client.Player;
import com.dark.common.ChatMsg;
import com.dark.common.JoinMsg;
import com.dark.common.PosMsg;
import com.dark.common.TurnMsg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * This class has been taken from
 * ch.fhnw.richards.lecture14_chatLab.v3_server
 * Some changes made in order to be adapted to our project
 */
public class Model {
	protected final ObservableList<Client> clients = FXCollections.observableArrayList();

	Player[] players = {new Player("North"),new Player("East"),new Player("South"),new Player("West")};
	
	private final Logger logger = Logger.getLogger("");
	private ServerSocket listener;
	private volatile boolean stop = false;
	
//	private int counter=0;
	private String names="";

	public void startServer(int port) {
		logger.info("Start server");
		try {
			listener = new ServerSocket(port, 10, null);
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						try {
							Socket socket = listener.accept();
							Client client = new Client(Model.this, socket);
							clients.add(client);
							logger.info("clients.size()="+clients.size());
							if(clients.size()==4) {
								broadcast(new TurnMsg(3,"pass"));
							}
							broadcast(new PosMsg(clients.size()-1));
						} catch (Exception e) {
							logger.info(e.toString());
						}
					}
				}
			};
			Thread t = new Thread(r, "ServerSocket");
			t.start();
		} catch (IOException e) {
			logger.info(e.toString());
		}
	}

	public void stopServer() {
		logger.info("Stop all clients");
		for (Client c : clients) c.stop();

		logger.info("Stop server");
		stop = true;
		if (listener != null) {
			try {
				listener.close();
			} catch (IOException e) {
				// Uninteresting
			}
		}
	}

	public void broadcast(ChatMsg outMsg) {
		logger.info("Broadcasting message to clients");
		for (Client c : clients) {
			c.send(outMsg);
		}
	}
	public void broadcast(JoinMsg outMsg) {
		if(names.equals(""))
			names+=outMsg.getName();
		else
			names+=":"+outMsg.getName();
		outMsg.setName(names);
		logger.info("Broadcasting message to clients");
		for (Client c : clients) {
			c.send(outMsg);
		}
	}
	public void broadcast(TurnMsg outMsg) {
		logger.info("Broadcasting message to clients");
		clients.get((outMsg.getPosition()+1)%4).send(new TurnMsg((outMsg.getPosition()+1)%4,outMsg.getContent()));
	}
	public void broadcast(PosMsg outMsg) {
		logger.info("Broadcasting position to client");
		clients.get(outMsg.getPosition()).send(outMsg);
	}
}
