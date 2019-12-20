package com.dark.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.dark.client.Player;
import com.dark.common.ChatMsg;
import com.dark.common.DealAllMsg;
import com.dark.common.DealMsg;
import com.dark.common.JoinMsg;
import com.dark.common.PlayMsg;
import com.dark.common.PosMsg;
import com.dark.common.TurnMsg;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 *Body taken from WI_SoftwareEngineering-master.zip Bradley Richards chatLab and enhanced for Tichu project by Kalina
 */
public class Model {
	
	protected Card cards;
	protected final ObservableList<Client> clients = FXCollections.observableArrayList();

	Player[] players = {new Player("North"),new Player("East"),new Player("South"),new Player("West")};
	private DeckOfCards deck;
	
	private ArrayList<Card> tableCards;
	
	private final Logger logger = Logger.getLogger("");
	private ServerSocket listener;
	private volatile boolean stop = false;
	
//	private int counter=0;
	private String names="";

	public void startServer(int port) {
		deck = new DeckOfCards();
		tableCards = null;
		
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
		Platform.exit();
		logger.info("Stop server");
		stop = true;
		if (listener != null) {
			try {
				Platform.exit();
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
		ArrayList<ArrayList<Card>> hands= new ArrayList<>();
		if(clients.size()==4) {	
			shuffle();
			deal();
			DealMsg[] dealMessages= new DealMsg[4];
			for(int i=0;i<4;i++) {
				
				dealMessages[i] = new DealMsg(players[i].getCards(),i);
				//send cliend DealMsg with his cards
			}
			
			broadcast(dealMessages);
			broadcast(new TurnMsg(3,"pass"));
		}
		if(clients.size()>4) {
			for(int i=0;i<4;i++) {
				ArrayList<Card> hand= new ArrayList<>();
				hand = players[i].getCards();
				hands.add(hand);
			}
			logger.info("%%%%%%%%%% SEND:  "+this.tableCards+"   %%%%%%%%%%%%%%%");
			DealAllMsg dealAllMsg = new DealAllMsg(hands,clients.size()-1,this.tableCards);//i not becessary
				
			//send cliend DealMsg with his cards
			broadcast(dealAllMsg);
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
	public void broadcast(DealMsg[] outMsg) {
		
		for(int i=0;i<4;i++) {//4
			logger.info("Dealing cards to client "+i);
			clients.get(i).send(outMsg[i]);
			//send cliend DealMsg with his cards
		}

		
	}
	public void broadcast(DealAllMsg outMsg) {
		logger.info("%%%%%%%%%%%%%%%   "+this.tableCards+"   %%%%%%%%%%%%%%%");	
		logger.info("%%%%%%%%%%%%%%%   "+outMsg.getCards()+"   %%%%%%%%%%%%%%%");	

		clients.get(outMsg.getPosition()).send(outMsg);

		
	}
	public void broadcast(PlayMsg outMsg) {//receive the cards
		logger.info("Broadcasting played cards to clients....."+outMsg.getCards());// Logger to check which cards were played on table
		//send all players msg with played cards,e.d: INFO: Sending message: Play|[2stars, 5stars] to all clients
		clients.get(outMsg.getPosition()).send(outMsg);//send
		
		this.tableCards=outMsg.getCards();

                
		for (int i=0;i<clients.size();i++) {
			if(i!=outMsg.getPosition()) {///////// send all players the cards played
				clients.get(i).send(outMsg);
			
			}
		}
		
		/* logger to display which cards are on the table now e.g:
		 * %%%%%%%% [2stars, 5pagodas, queenjade, kingpagodas, acepagodas] %%%%%%
		 */
		logger.info("%%%%%%%%%%%%%%%   "+this.tableCards+"   %%%%%%%%%%%%%%%");
	}	

	public DeckOfCards getDeck() {
		return deck;
	}
    private void shuffle() {


    	getDeck().shuffle();
    }
    private void deal() {

    	int cardsRequired = 4*13;
    	DeckOfCards deck = getDeck();
    	if (cardsRequired <= deck.getCardsRemaining()) {
        	for (int i = 0; i < 4; i++) {
        		Player p = players[i];
        		p.discardHand();
        		for (int j = 0; j < 13; j++) {
        			Card card = deck.dealCard();
        			p.addCard(card);
        		}
        		
        	}
    	} else {
            Alert alert = new Alert(AlertType.ERROR, "Not enough cards - shuffle first");
            alert.showAndWait();
    	}
    }
	
}