package com.dark.client;

import com.dark.client.Card.Rank;
import com.dark.client.Card.Suit;
//import application.Card;
//import application.Player;
//import application.Card.Rank;
//import application.Card.Suit;
import javafx.geometry.Pos;
//import application.Player;
//import application.PlayerPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * 
 * @author Kalina
 *
 */
public class View {
	VBox root = new VBox();
	HBox menu = new HBox();
	
	TopMenu topMenu = new TopMenu();
	
	StackPane tichuTable = new StackPane();
	BorderPane tichu = new BorderPane();
	BorderPane login = new BorderPane();
	
	
	VBox vbox = new VBox();
	HBox hbox = new HBox();

	Button allowButton = new Button("Allow");
	Button btnChat = new Button("Chat Box");

	Player[] players = {new Player("North"),new Player("East"),new Player("South"),new Player("West")};
	PlayerPane[] playerPanes = {new PlayerPane(),new PlayerPane(),new PlayerPane(),new PlayerPane()};
	
	HBox chatBox=new HBox();
	VBox chatPane=new VBox();
	HBox connectBox=new HBox();
	
	protected Stage stage;
//	private Model model;

	// Top controls
	VBox loginBox = new VBox();
	Label lblIpAddress = new Label("IP Address");
	TextField txtIpAddress = new TextField();
	Label lblPort = new Label("Port");
	TextField txtPort = new TextField();
	Label lblName = new Label("Username:");
	TextField txtName = new TextField();
	Label lblPass = new Label("Password:");
	TextField txtPass = new TextField();
	Label lblRepeat = new Label("Password repeat:");
	TextField txtRepeat = new TextField();
	Label lblEmpty = new Label("-");
	Button btnConnect = new Button("Login");//Connect
	Button btnRegister = new Button("Register");
	Button btnNew = new Button("New");
	BorderPane buttons = new BorderPane();
	
	// Chat area
	TextArea txtChatArea = new TextArea();
		
	// Bottom controls
	TextField txtChatMessage = new TextField();
	Button btnSend = new Button("Send");
	
//	String fileName = "tichu.jpg";
//	Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/" + fileName));
//	ImageView imv = new ImageView(image);

	
	public View(Stage stage, Model model) {
	
		menu.getChildren().addAll(allowButton);
		topMenu.setPrefWidth(root.getWidth());
		
		root.getChildren().addAll(topMenu.getMenuBar(),menu,tichuTable);
		
		//Fix the host number (maybe better to be set on each player manualy)
		txtIpAddress.setText("localhost");
		txtPort.setText("8585");
		
		//dealing, init
		for(int i=0;i<players.length;i++) {
    		Card card;
    		Suit s = Suit.Jade;
    		s.ordinal();
    		for(int j=0;j<Player.HAND_SIZE;j++) {
    			card = new Card(Suit.values()[i],Rank.values()[j]);
    			players[i].addCard(card);
    		}

			playerPanes[i].setPlayer(players[i]);
			playerPanes[i].getPlayButton().setDisable(true);
			playerPanes[i].getPassButton().setDisable(true);
		}

		//add and align
		
		for(int i=0;i<4;i++) {
			playerPanes[i].setPrefWidth(450);
			playerPanes[i].setMinWidth(450);
			playerPanes[i].setMaxWidth(450);
			playerPanes[i].setPrefHeight(230);
			playerPanes[i].setMinHeight(230);
			playerPanes[i].setMaxHeight(230);
		}

		tichu.setTop(playerPanes[0]);
		BorderPane.setAlignment(playerPanes[0],Pos.CENTER);

		tichu.setRight(playerPanes[1]);
		
		BorderPane.setAlignment(playerPanes[2],Pos.CENTER);
		
		tichu.setLeft(playerPanes[3]);

		vbox.getChildren().addAll(playerPanes[2],hbox);
		tichu.setBottom(vbox);
		vbox.setPrefWidth(450);
		vbox.setMinWidth(450);
		vbox.setMaxWidth(450);
		vbox.setPrefHeight(250);
		vbox.setMinHeight(250);
		vbox.setMaxHeight(250);
		BorderPane.setAlignment(vbox,Pos.CENTER);

		txtChatMessage.setPrefWidth(385);
		txtChatMessage.setMinWidth(385);
		txtChatMessage.setMaxWidth(385);

		chatBox.getChildren().addAll(txtChatMessage,btnSend);

		connectBox.getChildren().addAll(btnChat);
		connectBox.setAlignment(Pos.CENTER_RIGHT);
		chatPane.getChildren().addAll(connectBox,txtChatArea,chatBox);
		chatPane.setPrefWidth(430);
		chatPane.setMinWidth(430);
		chatPane.setMaxWidth(430);
		chatPane.setPrefHeight(20);
		chatPane.setMinHeight(20);
		chatPane.setMaxHeight(20);
		
		login.setPrefWidth(tichuTable.getWidth());
		login.setMaxWidth(tichuTable.getWidth());
		login.setMinWidth(tichuTable.getWidth());
		login.setPrefHeight(tichuTable.getHeight());
		login.setMaxHeight(tichuTable.getHeight());
		login.setMinHeight(tichuTable.getHeight());

		buttons.setLeft(btnConnect);
		buttons.setCenter(btnRegister);
		buttons.setRight(btnNew);
		
//		imv.setPreserveRatio(true);
//		//this.setGraphic(imv);
		
		loginBox.getChildren().addAll(lblName,txtName,lblPass,txtPass,lblRepeat,txtRepeat,lblEmpty,buttons);
//		login.setLeft(imv);
//		login.setRight(loginBox);
		login.setCenter(loginBox);
		lblRepeat.visibleProperty().set(false);
		txtRepeat.visibleProperty().set(false);
		btnRegister.visibleProperty().set(false);
		btnRegister.disableProperty().set(true);
		
		tichu.disableProperty().set(true);
		chatPane.disableProperty().set(true);
		menu.disableProperty().set(true);
		tichu.visibleProperty().set(false);
		chatPane.visibleProperty().set(false);
		menu.visibleProperty().set(false);
		tichuTable.getChildren().addAll(tichu,chatPane,login);
		StackPane.setAlignment(chatPane,Pos.BOTTOM_RIGHT);
		
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("poker.css").toExternalForm());
		stage.setScene(scene);
		stage.setMaximized(true);
				
		this.stage = stage;
//		this.model = model;

		stage.setTitle("Tichu Client");
	}
	public void createMenuStage(String css, Label lbl, ScrollPane sp) {
		Scene scene = new Scene(sp,600,400);
		Stage menuStage = new Stage();
		sp.setContent(lbl);
		scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
		menuStage.setTitle("Tichu Rules");
		menuStage.setScene(scene);
		menuStage.show();
	}
	protected void start() {
		stage.show();

	}
	
	// GET SET
	// All getters and setters
		public void setTopMenu(TopMenu topMenu) {
			this.topMenu = topMenu;
		}
		public MenuItem getAboutItem() {
			return topMenu.about;
		}

		public MenuItem getGenRulesItem() {
			return topMenu.genRuls;
		}

		public MenuItem getHandsItem() {
			return topMenu.hands;
		}

		public MenuItem getNewGameItem() {
			return topMenu.newGame;
		}

		public MenuItem getCloseItem() {
			return topMenu.close;
		}

		public MenuItem getRemovePlayerItem() {
			return topMenu.somethingElse;
		}

		public MenuItem getHighCardsItem() {
			return topMenu.highCards;
		}

		public MenuItem getHighCardsSplitItem() {
			return topMenu.highCardsSplit;
		}

		public MenuItem getPairItem() {
			return topMenu.pair;
		}

		public MenuItem getPairSplitItem() {
			return topMenu.pairSplit;
		}

		public MenuItem getTwoPairItem() {
			return topMenu.twoPair;
		}

		public MenuItem getTwoPairSplitItem() {
			return topMenu.twoPairSplit;
		}

		public MenuItem getThreeOfAKindItem() {
			return topMenu.threeOfKind;
		}

		public MenuItem getStraightItem() {
			return topMenu.straight;
		}

		public MenuItem getStraightSplitItem() {
			return topMenu.straightSplit;
		}

		public MenuItem getFlushItem() {
			return topMenu.flush;
		}

		public MenuItem getFlushSplitItem() {
			return topMenu.flushSplit;
		}

		public MenuItem getFullHouseItem() {
			return topMenu.fullHouse;
		}

		public MenuItem getFourOfAKindItem() {
			return topMenu.fourOfAKind;
		}

		public MenuItem getStraightFlushItem() {
			return topMenu.straightFlush;
		}

		public MenuItem getStraightFlushSplitItem() {
			return topMenu.straightFlushSplit;
		}

		public MenuItem getRoyalFlushItem() {
			return topMenu.royalFlush;
		}

		public MenuItem getRoyalFlushSplitItem() {
			return topMenu.royalFlushSplit;
		}

		public MenuItem getDifferentItem() {
			return topMenu.different;
		}

		public MenuItem getDifferentSplitItem() {
			return topMenu.differentSplit;
		}

		public TopMenu getTopMenu() {
			return topMenu;
		}
}
