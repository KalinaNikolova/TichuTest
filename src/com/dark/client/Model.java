package com.dark.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import com.dark.common.ChatMsg;
import com.dark.common.DealAllMsg;
import com.dark.common.DealMsg;
import com.dark.common.JoinMsg;
import com.dark.common.Message;
import com.dark.common.PlayMsg;
import com.dark.common.PosMsg;
import com.dark.common.TurnMsg;
import com.dark.server.Card;
import com.dark.server.Client;
import com.dark.server.Teams;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	protected Move newestMove = new Move();
	protected Deal newestDeal = new Deal();
	protected DealAll newestDealAll = new DealAll();
	protected ObservableList<String>guests=FXCollections.observableArrayList();
	
	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private String name;
	private int position;

	public void connect(String ipAddress, int Port, String name) {
		logger.info("Connect client[ "+name+" ]");
		this.name = name;
		try {
			socket = new Socket(ipAddress, Port);

			// Create thread to read incoming messages
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (true) {
						Message message=null;//null
						try {
							message = Message.receive(socket);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
						} else if (message instanceof DealMsg) {
							DealMsg msg = (DealMsg)message;

							newestDeal.setCards(msg.getCards());///////////////
							newestDeal.setIndex(msg.getPosition());
						} else if (message instanceof DealAllMsg) {
							DealAllMsg msg = (DealAllMsg)message;

							guests.add("guest"+(guests.size()+1));
							newestDealAll.setTable(msg.getTable());
							newestDealAll.setCards(msg.getCards());///////////////
							newestDealAll.setIndex(msg.getPosition());//last!!!
							
						} 
						else if (message instanceof PlayMsg) {
							PlayMsg msg = (PlayMsg)message;
							newestMove.setCards(msg.getCards());///////////////the cards on the table,e.g-> [2stars, 5stars]
							newestMove.setIndex(msg.getPosition());///////// which player put those cards
						
							
							
							
							
							//TEST try later
							//Teams.team(msg.getPosition(), msg.getCards());
						
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
			
			}catch (IOException ex) {
	            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);    
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
	// sending msg to the server what cards and which player played on the table
	public void sendMsg(ArrayList<Card> cards,int index) {
		logger.info("Send turn");
		
		/* send the msg with the cards on the table to the server
		 * E.g, -> [3swords, 3stars, 4swords], 0
		 */
		PlayMsg msg = new PlayMsg(cards, index); 
	
		msg.send(socket); //msg:Play|[3swords, 3stars, 4swords]
	}
}
