package com.dark.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.dark.client.Card.Rank;
import com.dark.client.Card.Suit;
import com.sun.javafx.geom.transform.GeneralTransform3D;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Controller implements Observer {
	// private Model model;
	private View view;

	public Controller(Model model, View view) {
		// this.model = model;
		this.view = view;

		view.getGenRulesItem().setOnAction(e -> genRules());
		view.getAboutItem().setOnAction(e -> about());
		view.getHandsItem().setOnAction(e -> hands());

		view.play.setOnAction(e -> {
			boolean play = true;
			if (view.play.getText() == "ON") {
				view.mediaPlayer.setVolume(100);

			} else {
				view.play.setText("OFF");
				view.mediaPlayer.setVolume(0);

			}

		});
		view.btnConnect.setOnAction(event -> {
			boolean userFound = false;
			try (Scanner in = new Scanner(new File("users.txt"))) {
				while (in.hasNextLine()) {
					String line = in.nextLine();
					String[] userAndPass = line.split(":");
					if (view.txtName.getText().equals(userAndPass[0])
							&& view.txtPass.getText().equals(userAndPass[1])) {
						userFound = true;
					}
					// go to 308 and and ServeClient 36
					String info = "";
					for (PlayerPane playerPane : view.playerPanes) {
						String[] names = playerPane.getName().split(":");
						if (names.length > 1)
							info += names[1] + ",";
						if (names.length > 1 && names[1].equals(userAndPass[0])) {
							userFound = false;
						}
					}
					view.txtRepeat.setText("Names:" + info);
					view.info1.setText("Names:" + info);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			if (userFound) {
				view.btnConnect.setDisable(true);
				String ipAddress = view.txtIpAddress.getText();
				int port = Integer.parseInt(view.txtPort.getText());
				String name = view.txtName.getText();
				model.connect(ipAddress, port, name);

				view.stage.setTitle(view.stage.getTitle() + " : " + name + " is playing");

				view.login.disableProperty().set(true);
				view.login.visibleProperty().set(false);
				view.tichu.disableProperty().set(false);
				view.chatPane.disableProperty().set(false);
				view.tichu.visibleProperty().set(true);
				view.chatPane.visibleProperty().set(true);
				view.chatPane2.disableProperty().set(false);
				view.chatPane2.visibleProperty().set(true);
			}

		});

		view.btnNew.setOnAction(event -> {
			view.lblRepeat.visibleProperty().set(true);
			view.txtRepeat.visibleProperty().set(true);
			view.btnRegister.visibleProperty().set(true);
			view.btnRegister.disableProperty().set(false);
		});

		view.btnRegister.setOnAction(event -> {
			if (view.txtName.getText() != null && !view.txtName.getText().equals("")
					&& view.txtPass.getText().equals(view.txtRepeat.getText()) && view.txtPass.getText() != null
					&& !view.txtPass.getText().equals("")) {
				// view.txtRepeat.setText(view.txtName.getText()+":"+view.txtPass.getText());
				boolean found = false;
				try (Scanner reader = new Scanner(new File("users.txt"))) {
					while (reader.hasNextLine()) {
						String line = reader.nextLine();
						String[] strings = line.split(":");
						if (view.txtName.getText().equals(strings[0])) {
							found = true;
							break;
						}
					}
				} catch (FileNotFoundException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				if (!found) {
					try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true))) {
						writer.println(view.txtName.getText() + ":" + view.txtPass.getText());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		});

		view.stage.setOnCloseRequest(event -> model.disconnect());

		view.btnSend.setOnAction(event -> {
			model.sendMessage(view.txtChatMessage.getText());
			view.txtChatMessage.setText("");// added
		});

		for (PlayerPane playerPane : view.playerPanes) {
			playerPane.getPassButton().setOnAction(e -> {
				model.sendMessage("pass");

				for (PlayerPane plPane : view.playerPanes) {
					plPane.getPassButton().disableProperty().set(true);
					plPane.getPlayButton().disableProperty().set(true);
					//
					for (int j = 0; j < plPane.getHboxCards().getChildren().size(); j++) {
						if (((CardLabel) plPane.getHboxCards().getChildren().get(j)).getGraphic() != null) {
							if (plPane.getHboxCards().getChildren().get(j).getStyleClass().contains("allowed"))
								plPane.getHboxCards().getChildren().get(j).getStyleClass().remove("allowed");
						
						}
					}
				}
			});
			playerPane.getPlayButton().setOnAction(e -> {
				model.sendMessage("pass");

				for (PlayerPane plPane : view.playerPanes) {
					plPane.getPassButton().disableProperty().set(true);
					plPane.getPlayButton().disableProperty().set(true);
					//
					for (int j = 0; j < plPane.getHboxCards().getChildren().size(); j++) {
						if (((CardLabel) plPane.getHboxCards().getChildren().get(j)).getGraphic() != null) {
							
						}
					}
				}

				//////////////
				Player table = new Player("Table");
				boolean isMoved = false;/// declaration only
				isMoved = false;
				// for(int mi=0;mi<4;mi++) {
				for (int i = 0; i < 4; i++) {
					// final int i = mi;
					ArrayList<Card> cards = new ArrayList<>(13);//
					for (int j = 0; j < view.playerPanes[i].getHboxCards().getChildren().size(); j++) {

						if (view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass()
								.contains("clicked")) {
							CardLabel cardLabel = (CardLabel) view.playerPanes[i].getHboxCards().getChildren().get(j);
							table.addCard(cardLabel.getCard());
							cards.add(cardLabel.getCard());// Ќе може ли направо table, не, за да не зависим от GUI
							// cardLabel.
							// System.out.println("clicked");
							view.players[i].getCards().remove(cardLabel.getCard());
							// view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("clicked");//
							isMoved = true;// depends on the last card
						}
					}
					if (isMoved) {
						view.info1.setText("size:" + table.getCards().size());
						// PlayerPane tablePane = new PlayerPane();
						TablePane tablePane = new TablePane();//
						tablePane.setPlayer(table);
						tablePane.topPlayer.setVisible(false);//
						tablePane.getStyleClass().add("table");//
						view.tichu.setCenter(tablePane);
						tablePane.setPrefWidth(450);
						tablePane.setMinWidth(450);
						tablePane.setMaxWidth(450);
						view.tichu.setAlignment(tablePane, Pos.CENTER);
						view.playerPanes[i].setPlayer(view.players[i]);
					}
					if (isMoved)
						for (int j = 0; j < 13; j++) {////////// clear only moved
							((CardLabel) view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(0);
							view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("clicked");// ??
							view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("allowed");
						}

					//
					if (isMoved) {

						Button button = (Button) e.getSource();
						BorderPane borderPane = (BorderPane) button.getParent();
						PlayerPane userPane = (PlayerPane) borderPane.getParent();
						String playerPosition = userPane.getName().split(":")[0];
						int index = -1;
						switch (playerPosition) {
						case "North":
							index = 0;
							break;
						case "East":
							index = 1;
							break;
						case "South":
							index = 2;
							break;
						case "West":
							index = 3;
							break;
						}
						view.info1.setText("i" + index + "size:" + table.getCards().size());
						model.sendMsg(table.getCards(), index);
					}

				}

				//////////////

			});
		}

		model.newestMessage.addListener((o, oldValue, newValue) -> {
			if (!newValue.isEmpty()) { // Ignore empty messages
				view.txtChatArea.appendText(newValue + "\n");
				if (view.chatPane.getMaxHeight() != 200 && !view.btnChat.getStyleClass().contains("yellow"))
					view.btnChat.getStyleClass().add("yellow");
			}
		});
		model.newestName.addListener((o, oldValue, newValue) -> {
			Platform.runLater(() -> {
				String[] names = newValue.split(":");
				for (int i = 0; i < 4; i++) {
					if (!view.playerPanes[i].getName().contains(":")) {
						// view.playerPanes[i].getp.setName(view.playerPanes[i].getName()+":"+names[i]
						view.playerPanes[i].setName(view.playerPanes[i].getName() + ":" + names[i]);
					}
				}
		
			});
		});
		model.newestCards.addListener((o, oldValue, newValue) -> {
			Platform.runLater(() -> {
				String[] names = newValue.split("]");
				Player[] players = new Player[4];
				for (int i = 0; i < players.length; i++) {
					Card card;
					Suit s = Suit.Jade;
					s.ordinal();
					for (int j = 0; j < Player.HAND_SIZE; j++) {
						card = new Card(Suit.values()[i], Rank.values()[j]);
						players[i].addCard(card);
					}

					view.playerPanes[i].setPlayer(players[i]);
				}
				for (int i = 0; i < 4; i++) {
					if (!view.playerPanes[i].getName().contains(":"))
						view.playerPanes[i].setName(view.playerPanes[i].getName() + ":" + names[i]);
				}
			});
		});
		model.newestTurn.addObserver(this);
		model.newestMove.addObserver(this);
		model.newestDeal.addObserver(this);
		model.newestDealAll.addObserver(this);

		view.btnChat.setOnAction(e -> {
			Platform.runLater(() -> {
				if (view.chatPane.getMaxHeight() != 200) {
					view.chatPane.setPrefHeight(200);
					view.chatPane.setMinHeight(200);
					view.chatPane.setMaxHeight(200);
					if (view.btnChat.getStyleClass().contains("yellow"))
						view.btnChat.getStyleClass().remove("yellow");
					TranslateTransition tt = new TranslateTransition(Duration.millis(500), view.chatPane);
					tt.setFromY(300);
					tt.setToY(0);
					tt.play();
				} else {

					TranslateTransition tt = new TranslateTransition(Duration.millis(500), view.chatPane);
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
		view.btnChat2.setOnAction(e -> {
			Platform.runLater(() -> {
				if (view.chatPane2.getMaxHeight() != 200) {
					view.chatPane2.setPrefHeight(200);
					view.chatPane2.setMinHeight(200);
					view.chatPane2.setMaxHeight(200);
					if (view.btnChat2.getStyleClass().contains("yellow"))
						view.btnChat2.getStyleClass().remove("yellow");
					TranslateTransition tt = new TranslateTransition(Duration.millis(500), view.chatPane2);
					tt.setFromY(300);
					tt.setToY(0);
					tt.play();
				} else {

					TranslateTransition tt = new TranslateTransition(Duration.millis(500), view.chatPane2);
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

		for (int mi = 0; mi < 4; mi++) {
			for (int mj = 0; mj < 13; mj++) {
				final int i = mi;
				final int j = mj;
				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseClicked(e -> {

					if (((CardLabel) view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic() != null) {

						if (view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass()
								.contains("clicked")) {
							view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().remove("clicked");
						} else {
							if (view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass()
									.contains("allowed"))
								view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().add("clicked");
						}
					}
				});

				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseEntered(e -> {

					if (((CardLabel) view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic() != null) {
						if (view.playerPanes[i].getHboxCards().getChildren().get(j).getStyleClass().contains("allowed"))//
							((CardLabel) view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(-25);

					}
				});

				view.playerPanes[i].getHboxCards().getChildren().get(j).setOnMouseExited(e -> {

					if (((CardLabel) view.playerPanes[i].getHboxCards().getChildren().get(j)).getGraphic() != null) {

						((CardLabel) view.playerPanes[i].getHboxCards().getChildren().get(j)).setLayoutY(0);

					}
				});
			}
		}
	}

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

	@Override
	public void update(Observable newest, Object newValue) {
		Platform.runLater(() -> {
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
				//view.info.setText("i:"+i+"size:"+cards.size());
				Player table = new Player("Table");
				boolean isMoved=false;
				for(Card card:cards) {
					table.addCard(card);
					view.players[i].getCards().remove(card);
				}
				view.info2.setText("i:"+i+"size:"+cards.size()+"?"+(view.players[i].getCards().size()<13)+"?"+(table.getCards().size()>0));
//				view.info.setText(view.info.getText()+(view.players[i].getCards().size()<13||table.getCards().size()>0));
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
		    			//view.playerPanes[i].updatePlayerDisplay();
		    		}
					view.playerPanes[i].updatePlayerDisplay();
					view.info1.setText(hand);
					view.info2.setText("dealed");
				}

				
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
					view.info2.setText(hands);
					
			
					ArrayList<Card> tableCards = deal.getTable();
					//view.info.setText("i:"+i+"size:"+cards.size());
					Player table = new Player("Table");
//?					boolean isMoved=false;
				if(tableCards!=null) {
						for(Card card:tableCards) {
							table.addCard(card);
//							view.players[i].getCards().remove(card);
						}

							TablePane tablePane = new TablePane();
							tablePane.setPlayer(table);
							tablePane.topPlayer.setVisible(false);//
							tablePane.getStyleClass().add("table");//
							view.tichu.setCenter(tablePane);
							tablePane.setPrefWidth(450);
							tablePane.setMinWidth(450);
							tablePane.setMaxWidth(450);
							view.tichu.setAlignment(tablePane,Pos.CENTER);
	//?						view.playerPanes[i].setPlayer(view.players[i]);
	//?					}
					}
					//view.txtChatMessage.setDisable(true);
					view.txtChatMessage.disableProperty().set(true);//ne disabled
//					view.txtChatMessage.visibleProperty().set(false);
					view.btnSend.disableProperty().set(true);
		
			}

		});
		
	}
	
}
