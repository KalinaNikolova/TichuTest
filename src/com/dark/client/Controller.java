package com.dark.client;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.dark.client.Card.Rank;
import com.dark.client.Card.Suit;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Controller implements Observer{
//	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
//		this.model = model;
		this.view = view;
		
		view.btnConnect.setOnAction( event -> {
			boolean userFound=false;
			try(Scanner in = new Scanner(new File("users.txt"))){
				while(in.hasNextLine()) {
					String line = in.nextLine();
					String[] userAndPass= line.split(":");
					if(view.txtName.getText().equals(userAndPass[0])&&view.txtPass.getText().equals(userAndPass[1])) {
						userFound=true;
					}	

					for(PlayerPane playerPane:view.playerPanes) {
						String[] names = playerPane.getName().split(":");
						if(names.length>1&&names[1].equals(userAndPass[0])) {
							userFound=false;
						}
					}
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			if(userFound) {
				view.btnConnect.setDisable(true);
				String ipAddress = view.txtIpAddress.getText();
				int port = Integer.parseInt(view.txtPort.getText());
				String name = view.txtName.getText();
				model.connect(ipAddress, port, name);
				
				view.stage.setTitle(view.stage.getTitle()+" : "+name+" is playing");

				view.login.disableProperty().set(true);
				view.login.visibleProperty().set(false);
				view.tichu.disableProperty().set(false);
				view.chatPane.disableProperty().set(false);
				view.tichu.visibleProperty().set(true);
				view.chatPane.visibleProperty().set(true);
			}
			
		});
		
		view.btnNew.setOnAction( event -> {
			view.lblRepeat.visibleProperty().set(true);
			view.txtRepeat.visibleProperty().set(true);
			view.btnRegister.visibleProperty().set(true);
			view.btnRegister.disableProperty().set(false);
		});
		
		view.btnRegister.setOnAction( event -> {
			if(view.txtName.getText()!=null&&view.txtName.getText()!=""&&view.txtPass.getText().equals(view.txtRepeat.getText())&&view.txtPass.getText()!=null&&view.txtPass.getText()!="") {
				view.txtRepeat.setText("Y");
				try(PrintWriter writer = new PrintWriter(new FileWriter("users.txt",true))){
					writer.println(view.txtName.getText()+":"+view.txtPass.getText());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		
		view.stage.setOnCloseRequest( event -> model.disconnect() );
		
		view.btnSend.setOnAction( event -> {
			model.sendMessage(view.txtChatMessage.getText());
			view.txtChatMessage.setText("");//added
		});
		
		for(PlayerPane playerPane:view.playerPanes) {
			playerPane.getPassButton().setOnAction(e->{
				model.sendMessage("pass");
				
				for(PlayerPane plPane:view.playerPanes) {
					plPane.getPassButton().disableProperty().set(true);
					plPane.getPlayButton().disableProperty().set(true);
				}
			});
			playerPane.getPlayButton().setOnAction(e->{
				model.sendMessage("pass");
				
				for(PlayerPane plPane:view.playerPanes) {
					plPane.getPassButton().disableProperty().set(true);
					plPane.getPlayButton().disableProperty().set(true);
				}
			});
		}
		
		model.newestMessage.addListener( (o, oldValue, newValue) -> {
			if (!newValue.isEmpty()) { // Ignore empty messages
				view.txtChatArea.appendText(newValue + "\n");
				if(view.chatPane.getMaxHeight()!=200&&!view.btnChat.getStyleClass().contains("yellow"))
					view.btnChat.getStyleClass().add("yellow");
			}
		} );
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
		model.newestCards.addListener( (o, oldValue, newValue) -> {
			Platform.runLater(() -> {
				String[] names = newValue.split("]");
				Player[] players=new Player[4];
				for(int i=0;i<players.length;i++) {
		    		Card card;
		    		Suit s = Suit.Jade;
		    		s.ordinal();
		    		for(int j=0;j<Player.HAND_SIZE;j++) {
		    			card = new Card(Suit.values()[i],Rank.values()[j]);
		    			players[i].addCard(card);
		    		}

					view.playerPanes[i].setPlayer(players[i]);
				}
				for(int i=0;i<4;i++) {
					if(!view.playerPanes[i].getName().contains(":"))
						view.playerPanes[i].setName(view.playerPanes[i].getName()+":"+names[i]);
				}
			});
		});
		model.newestTurn.addObserver(this);

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
			
		for(int mi=0;mi<4;mi++) {
			for(int mj=0;mj<13;mj++) {
				final int i = mi;
				final int j = mj;
				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseClicked(e -> {
					
					if(((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic()!=null) {
						
						if(view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().contains("clicked")) {
							view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("clicked");
						}else {
								view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().add("clicked");
						}
					}
				});
				
				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseEntered(e -> {
					
					if(((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic()!=null) {
							((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(-25);

					}
				});
				
				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseExited(e -> {
					
					if(((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic()!=null) {
						
							((CardLabel)view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(0);

					}
				});
			}
		}
	}

	@Override
	public void update(Observable newstTurn, Object newValue) {
		Platform.runLater(() -> {
				view.playerPanes[(int) newValue].getPassButton().disableProperty().set(false);
				view.playerPanes[(int) newValue].getPlayButton().disableProperty().set(false);
		});
		
	}
}
