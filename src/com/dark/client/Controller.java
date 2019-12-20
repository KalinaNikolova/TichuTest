package com.dark.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Scanner;

import com.dark.server.Card;
import com.dark.server.HandType;
import com.dark.server.Card.Rank;
import com.dark.server.Card.Suit;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class Controller implements Observer{
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		// Generate top menu stages with rules and about
		view.getGenRulesItem().setOnAction(e -> genRules());
		view.getAboutItem().setOnAction(e -> about());
		view.getHandsItem().setOnAction(e -> hands());

		// set on action button play sound
		view.play.setOnAction(e->{
			if(view.play.getText()=="Sound ON") {
				view.stopMusic();
				view.play.setText("Sound OFF");
				view.play.setStyle("-fx-text-fill:white");
			}
			else{
				view.play();
				view.play.setText("Sound ON");	
				view.play.setStyle("-fx-text-fill:red");
			}
		});
		
		
		// Login button
		view.btnLogin.setOnAction( event -> {
			boolean userFound=false;
			try(Scanner in = new Scanner(new File("users.txt"))){
				while(in.hasNextLine()) {
					String line = in.nextLine();
					String[] userAndPass= line.split(":");// store in users.txt name and pass, e.g-> a:1
					
					//checking if user is registered ->isFoud
					if(view.txtName.getText().equals(userAndPass[0])&&view.txtPass.getText().equals(userAndPass[1])) {
						userFound=true;
					}	
					
					String info = "";
					for(PlayerPane playerPane:view.playerPanes) {
						String[] names = playerPane.getName().split(":");
						if(names.length>1)
						 info+=names[1]+",";
						if(names.length>1&&names[1].equals(userAndPass[0])) {
							userFound=false;		
							
						}
					}
					view.txtRepeat.setText("Names:"+info);

				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			
			//if user is found disable first screen properties and enable second sreen properties
			if(userFound) {
				view.btnLogin.setDisable(true);
				String ipAddress = view.txtIpAddress.getText();
				int port = Integer.parseInt(view.txtPort.getText());
				String name = view.txtName.getText();
				model.connect(ipAddress, port, name);
				
				view.stage.setTitle(view.stage.getTitle()+" : "+name+" is playing");// show on the stage title who`s turn is

				view.login.disableProperty().set(true);
				view.login.visibleProperty().set(false);
				view.tichu.disableProperty().set(false);
				view.chatPane.disableProperty().set(false);
				view.tichu.visibleProperty().set(true);
				view.chatPane.visibleProperty().set(true);
				view.chatPane2.disableProperty().set(false);
				view.chatPane2.visibleProperty().set(true);
				
			// login successful alert message
				Alert info2 = new Alert(AlertType.WARNING);
				info2.setTitle("Login Successful");
				info2.setHeaderText("Please press OK to proceed!");
				info2.showAndWait();
			}
			
			// Warning for wrong user-name or password
			if(!userFound) {
				Alert info1 = new Alert(AlertType.WARNING);
				info1.setTitle("Error");
				info1.setHeaderText("Check username or Password");
				info1.setContentText("If you are a new user please register!");
				info1.showAndWait();
			}

			
		});
		
		// Show form for register user
		view.btnNew.setOnAction( event -> {
			view.lblRepeat.visibleProperty().set(true);
			view.txtRepeat.visibleProperty().set(true);
			view.btnRegister.visibleProperty().set(true);
			view.btnNew.visibleProperty().set(false);
			view.btnRegister.disableProperty().set(false);
		});
		
		//Register new user
		view.btnRegister.setOnAction( event -> {
			if(view.txtName.getText()!=null&&!view.txtName.getText().equals("")&&view.txtPass.getText().equals(view.txtRepeat.getText())&&view.txtPass.getText()!=null&&!view.txtPass.getText().equals("")) {
				
				//check if the file users exist and if not create it
				boolean found = false;
				try(Scanner reader = new Scanner(new File("users.txt"))){
					while(reader.hasNextLine()) {
						String line=reader.nextLine();
						String[] strings = line.split(":");
						if(view.txtName.getText().equals(strings[0])){
							found = true;
							break;
						}
					}
				} catch (FileNotFoundException e3) {
					
					e3.printStackTrace();
				}
				if(!found) {
					try(PrintWriter writer = new PrintWriter(new FileWriter("users.txt",true))){
						writer.println(view.txtName.getText()+":"+view.txtPass.getText());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					
					// if the registration is successful 
					Alert in4 = new Alert(AlertType.WARNING);
					in4.setTitle("Congradulations!!");
					in4.setHeaderText("Registration complete\nPlease press the login button to sign-in!!");

					in4.showAndWait();	
				}
			}
		});
		
		
		
      //	in case of user pressing exit button 	
		view.stage.setOnCloseRequest( event ->{
			Alert in4 = new Alert(AlertType.CONFIRMATION);
			in4.setTitle("Confirmation ");
			in4.setContentText("Are you sure you want to EXIT?");
			Optional<ButtonType> result = in4.showAndWait();
			if (result.get() == ButtonType.OK){
			    Platform.exit();
			} else {
			    if(result.get() == ButtonType.CANCEL) {
			    	event.consume();
			    }
			}
			
		} );
		
		 //	in case of user pressing exit button at any time
		view.stage.setOnCloseRequest( event ->{
		exitConfirmation(event);
			
		} );
		
		//button send of chat messages
		view.btnSend.setOnAction( event -> {
			model.sendMessage(view.txtChatMessage.getText());
			view.txtChatMessage.setText("");//added
		});
		
		//set all players Pass button
		for(PlayerPane playerPane:view.playerPanes) {
			playerPane.getPassButton().setOnAction(e->{
				model.sendMessage("pass");
				
				//properties pf PASS and PLAY are initially disabled
				for(PlayerPane plPane:view.playerPanes) {
					plPane.getPassButton().disableProperty().set(true);
					plPane.getPlayButton().disableProperty().set(true);
					
					//Players get initially all 13 cards
					for(int j=0;j<plPane.getHboxCards().getChildren().size();j++) {
						if(((CardLabel)plPane.getHboxCards().getChildren().get(j)).getGraphic()!=null) {
							
							/* the idea is that allowed cards are in yellow (check in css) the rest can not be chosen in this round
							according to the rules -> not implemented yet
							*/
							if(plPane.getHboxCards().getChildren().get(j).getStyleClass().contains("allowed"))
							plPane.getHboxCards().getChildren().get(j).getStyleClass().remove("allowed");
							
						}
					}
				}
			});
			
			//Set Play cards button
			playerPane.getPlayButton().setOnAction(e->{
				model.sendMessage("pass");// if cards a re played goes to the next player
				
				//disable PASS and PLAY when player already played 
				for(PlayerPane plPane:view.playerPanes) {
					plPane.getPassButton().disableProperty().set(true);
					plPane.getPlayButton().disableProperty().set(true);
					
					
					for(int j=0;j<plPane.getHboxCards().getChildren().size();j++) {
						if(((CardLabel)plPane.getHboxCards().getChildren().get(j)).getGraphic()!=null) {

						}
					}
				}
				
				//Choose, play and put on table cards
				Player table = new Player("Table");
				boolean isMoved=false;
				isMoved=false;
				
				for(int i=0;i<4;i++) {
					
					ArrayList<Card> cards = new ArrayList<>(13);
					for(int j=0;j<view.playerPanes[i].getHboxCards().getChildren().size();j++) {
						System.out.println("R:259 size of array playerpanes initialy  13: "+view.playerPanes[i].getHboxCards().getChildren().size());
						//if cards are clicked they are selected to be played
						if(view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().contains("clicked")) {//take first player his pane and check if clicked j= 1st card
							System.out.println("R:262 or this IS MAYBE the card selected?:"+ view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().contains("clicked"));
							CardLabel cardLabel = (CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j);
							System.out.println("R:264 THIS IS MAYBE the card selected?:"+ cardLabel.getCard());
//								if(cardLabel.getCard()) {
//								
//							}
							table.addCard(cardLabel.getCard());
							cards.add(cardLabel.getCard());
							
							//remove selected cards from the players cards
							view.players[i].getCards().remove(cardLabel.getCard());
						
							isMoved=true;
						}
					}
					// put selected cards on the table 
					if(isMoved) {
						
						TablePane tablePane = new TablePane();//
						tablePane.setPlayer(table);
						tablePane.topPlayer.setVisible(false);//
						tablePane.getStyleClass().add("table");//
						view.tichu.setCenter(tablePane);
						tablePane.setId("tablePane");
						tablePane.setPrefWidth(450);
						tablePane.setMinWidth(450);
						tablePane.setMaxWidth(450);
						view.tichu.setAlignment(tablePane,Pos.CENTER);
						view.playerPanes[i].setPlayer(view.players[i]);
					}
					// check again if isMoved ==true -> for testing purpose
					if(isMoved)
						//remove all markers : yellow cards and borders of clicked cards
					for(int j=0;j<13;j++) {
						((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(0);
						view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("clicked");
						view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("allowed");
					}
					
					// check again if isMoved ==true -> for testing purpose
					//set next player turn
					if(isMoved) {
						
						Button button = (Button)e.getSource();
						BorderPane borderPane = (BorderPane)button.getParent();
						PlayerPane userPane = (PlayerPane)borderPane.getParent();
						String playerPosition = userPane.getName().split(":")[0];
						int index=-1;
						switch(playerPosition) {
						case "North":index=0;break;
						case "East":index=1;break;
						case "South":index=2;break;
						case "West":index=3;break;
						}

						model.sendMsg(table.getCards(),index); // send Message to the server
					}
					
				}
				
			});
		}
		
		//send a message to the chat
		model.newestMessage.addListener( (o, oldValue, newValue) -> {
			if (!newValue.isEmpty()) { // Ignore empty messages
				view.txtChatArea.appendText(newValue + "\n");
				if(view.chatPane.getMaxHeight()!=200&&!view.btnChat.getStyleClass().contains("yellow"))
					view.btnChat.getStyleClass().add("yellow");
			}
		} );
		
		//who sends the message
		model.newestName.addListener( (o, oldValue, newValue) -> {
			Platform.runLater(() -> {
				String[] names = newValue.split(":");
				for(int i=0;i<4;i++) {
					if(!view.playerPanes[i].getName().contains(":")) {
					
						view.playerPanes[i].setName(view.playerPanes[i].getName()+":"+names[i]);
					}				
				}

			});
		});
		///////////////////////////////////////////////////TEST DO NOT REMOVE YET
		//what cards were played
//		model.newestCards.addListener( (o, oldValue, newValue) -> {
//			Platform.runLater(() -> {
//				String[] names = newValue.split("]");
//				Player[] players=new Player[4];
//				for(int i=0;i<players.length;i++) {
//		    		Card card;
//		    		Suit s = Suit.Jade;
//		    		s.ordinal();
//		    		for(int j=0;j<Player.HAND_SIZE;j++) {
//		    			card = new Card(Suit.values()[i],Rank.values()[j]);
//		    			players[i].addCard(card);
//		    		}
//
//					view.playerPanes[i].setPlayer(players[i]);
//				}
//				for(int i=0;i<4;i++) {
//					if(!view.playerPanes[i].getName().contains(":"))
//						view.playerPanes[i].setName(view.playerPanes[i].getName()+":"+names[i]);
//				}
//			});
//		});
		
		model.newestTurn.addObserver(this);
		model.newestMove.addObserver(this);
		model.newestDeal.addObserver(this);
		model.newestDealAll.addObserver(this);
		
		//Animate chat and yellow button when msg was send
		view.btnChat.setOnAction(e->{
			Platform.runLater(() -> {
				if(view.chatPane.getMaxHeight()!=200) {
					view.chatPane.setPrefHeight(200);
					view.chatPane.setMinHeight(200);
					view.chatPane.setMaxHeight(200);
					if(view.btnChat.getStyleClass().contains("yellow"))
						view.btnChat.getStyleClass().remove("yellow");
					TranslateTransition tt=new TranslateTransition(Duration.millis(500), view.chatPane);
					tt.setFromY(300);
					tt.setToY(0);
					tt.play();
				}else {

					TranslateTransition tt=new TranslateTransition(Duration.millis(500), view.chatPane);
					tt.setFromY(0);
					tt.setToY(180);
					tt.play();
					tt.onFinishedProperty().set(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent actionEvent) {
							view.chatPane.setPrefHeight(199);
							view.chatPane.setMinHeight(199);
							view.chatPane.setMaxHeight(199);
						}		
					});
				}
			});
		});
		
		/*Animated chat with Guests watching the game-> each >4 client is a guest that can watch and write 
		 * in a separate chat. Disable chat of the players. Players on the table should not be able to enter and see all cards (TO be implemented)*/
		view.btnChat2.setOnAction(e->{
			Platform.runLater(() -> {
				if(view.chatPane2.getMaxHeight()!=200) {
					view.chatPane2.setPrefHeight(200);
					view.chatPane2.setMinHeight(200);
					view.chatPane2.setMaxHeight(200);
					if(view.btnChat2.getStyleClass().contains("yellow"))
						view.btnChat2.getStyleClass().remove("yellow");
					TranslateTransition tt=new TranslateTransition(Duration.millis(500), view.chatPane2);
					tt.setFromY(300);
					tt.setToY(0);
					tt.play();
				}else {

					TranslateTransition tt=new TranslateTransition(Duration.millis(500), view.chatPane2);
					tt.setFromY(0);
					tt.setToY(180);
					tt.play();
					tt.onFinishedProperty().set(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent actionEvent) {
							view.chatPane2.setPrefHeight(199);
							view.chatPane2.setMinHeight(199);
							view.chatPane2.setMaxHeight(199);
						}		
					});
				}
			});
		});
		
	
		// set the clicked cards 
		for(int mi=0;mi<4;mi++) {
			for(int mj=0;mj<13;mj++) {
				final int i = mi;
				final int j = mj;
				// if the cards are allowed to play, the player is able to click (select) them -> TO be implemented
				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseClicked(e -> {
					boolean isSelected= false;
					if(((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic()!=null) {//click on the card trigger
						isSelected= true;
						//unable cards which can not be played in this combination
						if(isSelected ) {
							Object o= view.playerPanes[i].getHboxCards().getChildren().get(j) ;
							
						}
						
						if(view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().contains("clicked")) {
							view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("clicked");// if click again remove it
							//allow all cards again
							
						}else {
							if(view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().contains("allowed"))
								view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().add("clicked");
						}
					}
				});
				
				
				
				//To make the cards move UP when mouse is moved onto them -> -25
				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseEntered(e -> {
					
					if(((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic()!=null) {
						if(view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().contains("allowed"))//
							((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(-25);

					}
				});
				
				//to put the cards down when mouse is onto them -> return to 0
				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseExited(e -> {
					
					if(((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic()!=null) {
						
							((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(0);

					}
				});
			}
		}
	}
//	public boolean checkHand() {
//		for(int i=0;i<13;i++) {
//			
//		}
//		
//		return true;
//	}
//	public void allowCards() {
//		
//	String cards= "";
//		switch (cards) {		
//
//		 
//        case "StraightFlush": 		//  higher rank wins
//        case "Trio":
//        case "Sequence":
//        case "FourOFAKind":
//           
//        {
//	}
        ////////////////////////////////////////////////////////// TO TEST
        public static boolean isOnePair(ArrayList<Card> cards) {
            int[] valueCards = HandType.getValueList(cards);
            
            boolean isOnePair=false;
            
                  if(cards.size()==2 && (valueCards[0]==valueCards[1])){
		
             isOnePair=true;        
            }
                  return isOnePair;
	}
        
        
        	public static boolean isTrio(ArrayList<Card> cards) {
                    
                     int[] valueCards = HandType.getValueList(cards);
            
            boolean isTrio=false;
                    
                 if(cards.size()==3 && (valueCards[0]==valueCards[1]) && (valueCards[1]==valueCards[2])){
                        
                     isTrio=true;
                        
                    }

		return isTrio;

	}
                
                	public static boolean isFullHouse(ArrayList<Card> cards) {

		// suit doesn't matter only rank, 3 of the same rank and 2 of the same rank

		int[] valueCards = HandType.getValueList(cards);

		for (int i = 0; i < cards.size(); i++) {
	//		valueCards[i] = cards.get(i).getOrdinal();
		}
		Arrays.sort(valueCards);

		boolean isFullHouse = false;

		if (valueCards[0] == valueCards[1]) {
			// check if third card is the same or the next 3 are the same
			if (valueCards[1] == valueCards[2]) {
				if (valueCards[3] == valueCards[4])
					isFullHouse = true;
			} else if (valueCards[2] == valueCards[3] && valueCards[3] == valueCards[4])
				isFullHouse = true;
		}

		return isFullHouse;
	}
                
                

	public static boolean isSequenceOfPair(ArrayList<Card> cards) {
		// Clone the cards, because we will be altering the list
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

		// Find the first pair; if found, remove the cards from the list
		boolean firstPairFound = false;
		for (int i = 0; i < clonedCards.size() - 1 && !firstPairFound; i++) {
			for (int j = i + 1; j < clonedCards.size() && !firstPairFound; j++) {
				if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
					firstPairFound = true;
					clonedCards.remove(j); // Remove the later card
					clonedCards.remove(i); // Before the earlier one
				}
			}
		}
		// If a first pair was found, see if there is a second pair
		return firstPairFound && isOnePair(clonedCards);
	}
        
        public static boolean isSequence(ArrayList<Card> cards) {
            boolean isSequence=false;
            int[] valueCards = HandType.getValueList(cards);

                    
                 if(cards.size()>=5&&isStraight(cards)){
                     
                        isSequence=true;
                        
                    }

		return isSequence;
            
        }


	public static boolean isStraight(ArrayList<Card> cards) { //check if is sequence 

		boolean isStraight = false;
		if (!HandType.checkFlush(cards) && HandType.checkStraight(cards))
			isStraight = true;

		return isStraight;

	}

	public static boolean isFlush(ArrayList<Card> cards) {

		boolean isFlush = false;

		if (HandType.checkFlush(cards) && !HandType.checkStraight(cards))
			isFlush = true;

		return isFlush;

	}

	public static boolean isFourOfAKind(ArrayList<Card> cards) {
		return findSameOfAKind(cards, 4);
	}

	public static boolean isStraightFlush(ArrayList<Card> cards) {
		boolean isStraightFlush = false;
                
                if(cards.size()>=5){

		if (HandType.checkFlush(cards) && HandType.checkStraight(cards))
			isStraightFlush = true;
                
                }

		return isStraightFlush;
	}


	private static boolean checkFlush(ArrayList<Card> cards) {

		boolean isFlush = false;
		boolean allSuit = true;

		for (int i = 0; i < cards.size() - 1; i++) {
			for (int j = i + 1; j < cards.size(); j++) {

				if (cards.get(i).getSuit() == cards.get(j).getSuit() && allSuit) {
					isFlush = true;

				} else {
					allSuit = false;
					isFlush = false;
				}
			}
		}

		return isFlush;
	}

	private static boolean checkStraight(ArrayList<Card> cards) {

		int[] valueCards = HandType.getValueList(cards);

		boolean isStraight = false;

		for (int i = 0; i < valueCards.length - 1; i++) {
			if (valueCards[i] + 1 == valueCards[i + 1]) {
				isStraight = true;
			} else {
				isStraight = false;
				break;
			}
		}
		return isStraight;
	}
	
	
	public static boolean findSameOfAKind(ArrayList<Card> cards, int num) {
		int count = 1;
		boolean found = false;
		for (int i = 0; i < cards.size() - 1 && !found; i++) {
			count = 1;
			for (int j = i + 1; j < cards.size() && !found; j++) {
				if (cards.get(i).getRank() == cards.get(j).getRank())
					count++;
			}
			if (count >= num) {
				found = true;
			}
		}
		return found;
	}
	/////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////REMOVE//////////////////////////////////////////////////////////////
	
	
	//Set top menu items
	public void hands() {
		String fileName = "hands.txt";
		printFile(fileName);
	}

	public void genRules() {
		String fileName = "generalRules.txt";
		printFile(fileName);
	}

	public void about() {
		String fileName = "about.txt";
		printFile(fileName);
	}

	// take txt file from images folder
	private void printFile(String fileName) {
		String text = "";
		Scanner scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("tichu/images/" + fileName));
		String line;
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			text += "\t" + line + "\t\n";
		}
		Label lbl = new Label(text);
		view.createMenuStage("topMenuStage.css", lbl, new ScrollPane());
	}
	
	// to not repeat code 
	public void exitConfirmation(Event event) {
		Alert in4 = new Alert(AlertType.CONFIRMATION);
		in4.setTitle("Confirmation ");
		in4.setContentText("Are you sure you want to EXIT?");
		Optional<ButtonType> result = in4.showAndWait();
		if (result.get() == ButtonType.OK){
		    Platform.exit();
		} else {
		    if(result.get() == ButtonType.CANCEL) {
		    	event.consume();
		    }
		}
	}
	
	

	//Update the view of the players
	@Override
	public void update(Observable newest, Object newValue) {
		Platform.runLater(() -> {
			
			//Check what was the change send a proper message 
			if(newest instanceof Turn) {
				view.playerPanes[(int) newValue].getPassButton().disableProperty().set(false);
				view.playerPanes[(int) newValue].getPlayButton().disableProperty().set(false);
				//mmmm
				for(int j=0;j<view.playerPanes[(int) newValue].getHboxCards().getChildren().size();j++) {
					if(((CardLabel)view.playerPanes[(int) newValue].getHboxCards().getChildren().get(j)).getGraphic()!=null) {
						if(!view.playerPanes[(int) newValue].getHboxCards().getChildren().get(j).getStyleClass().contains("allowed"))
						view.playerPanes[(int) newValue].getHboxCards().getChildren().get(j).getStyleClass().add("allowed");
						
					}
				}

			}else if(newest instanceof Move) {
				Move move = (Move)newest;
				int i = move.getIndex();
				ArrayList<Card> cards = move.getCards();
				Player table = new Player("Table");
				boolean isMoved=false;
				for(Card card:cards) {
					table.addCard(card);
					view.players[i].getCards().remove(card);
				}

				if(view.players[i].getCards().size()<13||table.getCards().size()>0) {
					TablePane tablePane = new TablePane();
					tablePane.setPlayer(table);
					tablePane.topPlayer.setVisible(false);//
					tablePane.getStyleClass().add("table");//
					view.tichu.setCenter(tablePane);
					tablePane.setPrefWidth(450);
					tablePane.setMinWidth(450);
					tablePane.setMaxWidth(450);
					view.tichu.setAlignment(tablePane,Pos.CENTER);
					view.playerPanes[i].setPlayer(view.players[i]);
				}
				
				
				//display cards of a player
			}else if(newest instanceof Deal) {
				Deal deal = (Deal)newest;
				int i = deal.getIndex();
				if(i<4) {
					ArrayList<Card> cards = deal.getCards();
					String hand="";
					for(int j=0;j<Player.HAND_SIZE;j++) {
		    			Card card = cards.get(j);
		    			view.players[i].addCard(card);
		    			hand+=card.toString()+",";
		    		}
					view.playerPanes[i].updatePlayerDisplay();
				}
				
			//Deal all to show the cards of all players 
			}else if(newest instanceof DealAll) {
				DealAll deal = (DealAll)newest;
				int i = deal.getIndex();
				
				ArrayList<ArrayList<Card>> cards = deal.getCards();
					String hands="";
					for(int k=0;k<4;k++) {
						hands+="[";
						for(int j=0;j<cards.get(k).size();j++) {//removed
			    			Card card = cards.get(k).get(j);
			    			view.players[k].addCard(card);
			    			hands+=card.toString()+",";
			    		}
						hands+="],";
						view.playerPanes[k].updatePlayerDisplay();
					}
					
					ArrayList<Card> tableCards = deal.getTable();
					
					Player table = new Player("Table");
			
				if(tableCards!=null) {
						for(Card card:tableCards) {
							table.addCard(card);

						}
						
							//update the table
							TablePane tablePane = new TablePane();
							tablePane.setPlayer(table);
							tablePane.topPlayer.setVisible(false);//
							tablePane.getStyleClass().add("table");//
							view.tichu.setCenter(tablePane);
							tablePane.setPrefWidth(450);
							tablePane.setMinWidth(450);
							tablePane.setMaxWidth(450);
							view.tichu.setAlignment(tablePane,Pos.CENTER);
	
					}
		
					view.txtChatMessage.disableProperty().set(true);//ne disabled

					view.btnSend.disableProperty().set(true);

			}

		});
		
	}
	
}