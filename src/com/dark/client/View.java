package com.dark.client;

import java.io.File;

import com.dark.client.Card.Rank;
import com.dark.client.Card.Suit;
import com.sun.javafx.iio.gif.GIFImageLoader2;

//import application.Card;
//import application.Player;
//import application.Card.Rank;
//import application.Card.Suit;
import javafx.geometry.Pos;
//import application.Player;
//import application.PlayerPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
/**
 * 
 * @author Kalina
 *
 */
public class View {
	VBox root = new VBox();
	HBox menu = new HBox();
	
	TextField info1 = new TextField();
	TextField info2 = new TextField(); 
	
	TopMenu topMenu = new TopMenu();
	
	StackPane tichuTable = new StackPane();
	BorderPane tichu = new BorderPane();
	BorderPane login = new BorderPane();
	
	
	VBox vbox = new VBox();
	HBox hbox = new HBox();

	Button allowButton = new Button("Allow");
	Button btnChat = new Button("Chat Box");
	Button btnChat2 = new Button("Guests");
	ListView guestList = new ListView();
	
	
	Player[] players = {new Player("North"),new Player("East"),new Player("South"),new Player("West")};
	PlayerPane[] playerPanes = {new PlayerPane(),new PlayerPane(),new PlayerPane(),new PlayerPane()};
	
	HBox chatBox=new HBox();
	VBox chatPane=new VBox();
	HBox connectBox=new HBox();
	HBox chatBox2=new HBox();
	VBox chatPane2=new VBox();
	HBox connectBox2=new HBox();
	
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
	PasswordField txtPass = new PasswordField();
	Label lblRepeat = new Label("Password repeat:");
//	TextField txtRepeat = new TextField();
	PasswordField txtRepeat = new PasswordField();
	Label lblEmpty = new Label("-");
	Button btnConnect = new Button("Login");//Connect
	Button btnRegister = new Button("Register");
	Button btnNew = new Button("New");
	BorderPane buttons = new BorderPane();
	Button play= new Button("ON");
	MediaPlayer mediaPlayer;
	Slider volume;
	
	
	ToggleGroup group= new ToggleGroup();
	Button testToggle= new Button();
	
	// Chat area
	TextArea txtChatArea = new TextArea();
	TextArea txtChatArea2 = new TextArea();

	// Bottom controls
	TextField txtChatMessage = new TextField();
	TextField txtChatMessage2 = new TextField();
	Button btnSend = new Button("Send");
	Button btnSend2 = new Button("Send");
	
//	String fileName = "tichu.jpg";
//	Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/" + fileName));
//	ImageView imv = new ImageView(image);

	
	public View(Stage stage, Model model) {
		volume= new Slider(0.0, 1.0, 1.0);
		
		
		menu.getChildren().addAll(allowButton,info1,info2);//
		topMenu.setPrefWidth(root.getWidth());
		//Image backgrounfGif= new Image("dragon-42.gif");
		volume.setMaxWidth(20);
		lblName.setStyle("-fx-text-fill:white");
		lblPass.setStyle("-fx-text-fill:white");
		
		
		root.getChildren().addAll(topMenu.getMenuBar(),play, volume,menu,tichuTable);
		
		//Fix the host number (maybe better to be set on each player manualy)
		txtIpAddress.setText("localhost");
		txtPort.setText("8585");
		
		
		//dealing, init
		for(int i=0;i<players.length;i++) {
//    		Card card;
//    		Suit s = Suit.Jade;
//    		s.ordinal();
//    		for(int j=0;j<Player.HAND_SIZE;j++) {
//    			card = new Card(Suit.values()[i],Rank.values()[j]);
//    			players[i].addCard(card);
//    		}

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
		txtChatMessage2.setPrefWidth(385);
		txtChatMessage2.setMinWidth(385);
		txtChatMessage2.setMaxWidth(385);

		chatBox.getChildren().addAll(txtChatMessage,btnSend);
		chatBox2.getChildren().addAll(txtChatMessage2,btnSend2);
	
		
		connectBox.getChildren().addAll(btnChat);
		connectBox.setAlignment(Pos.CENTER_RIGHT);
		chatPane.getChildren().addAll(connectBox,txtChatArea,chatBox);
		chatPane.setPrefWidth(430);
		chatPane.setMinWidth(430);
		chatPane.setMaxWidth(430);
		chatPane.setPrefHeight(20);
		chatPane.setMinHeight(20);
		chatPane.setMaxHeight(20);
		connectBox2.getChildren().addAll(btnChat2);
		connectBox2.setAlignment(Pos.CENTER_LEFT);
		chatPane2.getChildren().addAll(connectBox2,guestList,chatBox2);
		chatPane2.setPrefWidth(430);
		chatPane2.setMinWidth(430);
		chatPane2.setMaxWidth(430);
		chatPane2.setPrefHeight(20);
		chatPane2.setMinHeight(20);
		chatPane2.setMaxHeight(20);
		guestList.setPrefWidth(100);//
		guestList.setPrefHeight(70);//
//		list.getChildren().add(guestList);//
		guestList.setItems(model.guests);
		
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
		chatPane2.disableProperty().set(true);//
		menu.disableProperty().set(true);
		tichu.visibleProperty().set(false);
		chatPane.visibleProperty().set(false);
		chatPane2.visibleProperty().set(false);//
		menu.visibleProperty().set(false);
		tichuTable.getChildren().addAll(tichu,chatPane,chatPane2,login);
		StackPane.setAlignment(chatPane,Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(chatPane2,Pos.BOTTOM_LEFT);
		
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("tichu.css").toExternalForm());

		playMusic( "DRAGON Dark Apocalyptic Battle.mp3");
		
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.getIcons().add(new Image("tichu/images/dragon.png"));
		
		
		this.stage = stage;
//		this.model = model;

		stage.setTitle("Tichu Client");
	}
	

	// Create stages for the Top Menu
	public void createMenuStage(String css, Label lbl, ScrollPane sp) {
		Scene scene = new Scene(sp,600,400);
		Stage menuStage = new Stage();
		sp.setContent(lbl);
		scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
		
		menuStage.setScene(scene);
		menuStage.getIcons().add(new Image("tichu/images/Notebook Emoji.png")); 
		menuStage.setTitle("Tichu Rules");
		menuStage.show();
	}
	/**
	 * 
	 */
	public void playMusic(String musicFile) {
		File file= new File(musicFile);
		Media sound = new Media(file.toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}
	
	public void stopMusic() {
		mediaPlayer.stop();
	}
	
	
	protected void start() {
		stage.show();

	}
	
	
	// GET SET
	// All getters and setters
	
	public VBox getRoot() {
		return this.root;
	}
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

	

		public Slider getVolume() {
			return volume;
		}

		public void setVolume(Slider volume) {
			this.volume = volume;
		}

	
	


		public MediaPlayer getMediaPlayer() {
			return mediaPlayer;
		}


		public void setMediaPlayer(MediaPlayer mediaPlayer) {
			this.mediaPlayer = mediaPlayer;
		}

	
		
}
