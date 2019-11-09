package DarkTichu.view;

import java.util.ArrayList;
import java.util.Random;

import DarkTichu.model.Card;
import DarkTichu.model.Card.Rank;
import DarkTichu.model.Card.Suit;
import DarkTichu.model.DarkTichuModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DarkTichuView {
	private DarkTichuModel model;
	private ControlArea controls;
	private TopMenu topMenu;
	private TilePane players;
	private BorderPane root;
	private Stage stage;
	// center cards
	
	public DarkTichuView(Stage stage, DarkTichuModel model) {
		this.model = model;
		this.stage = stage;
		this.model = model;
		this.stage = stage;
		// Create all of the player panes we need, and put them into TilePane
		players = new TilePane();
		for (int i = 0; i < model.getPlayersCount(); i++) {
			addPlayer(model, i);
		}

		// Create the control area
		controls = new ControlArea();
		controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in the logic
		//
		// Menubar
		topMenu = new TopMenu();

		// Put players and controls into a BorderPane
		root = new BorderPane();

		root.setTop(topMenu.getMenuBar());
		root.setCenter(players);
		root.setBottom(controls);

		// Create new stage
		createMainStage(randomCss());
	}

	// Create the main stage
	public void createMainStage(String css) {
		Scene scene = new Scene(this.root, 1600, 650);
		scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
		stage.setTitle("Dark Tichu");
		stage.setScene(scene);
		stage.setResizable(false);
		getRemovePlayerItem().setDisable(true);
		stage.show();
	}

	// Create stage for the Menu Bar-Help
	public void createMenuStage(String css, Label lbl, HBox hBox) {
		Scene scene = new Scene(hBox);
		Stage menuStage = new Stage();
		hBox.getChildren().add(lbl);
		scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
		menuStage.setTitle("Tichu Rules");
		menuStage.setScene(scene);
		menuStage.show();
	}

	// create random css each time when the program run
	public static String randomCss() {
		ArrayList<String> myCss = new ArrayList<>();
		myCss.add("yellow.css");
		myCss.add("blue.css");
		myCss.add("poker.css");
		Random gen = new Random();
		int myGen = gen.nextInt(3);
		String css = myCss.get(myGen);
		return css;
	}

	// Add new player
	public void addPlayer(DarkTichuModel model, int i) {
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
		players.getChildren().add(pp);
	}

	// Remove player
	public void removePlayer() {
		players.getChildren().remove(players.getChildren().size() - 1);
	}

	// All getters and setters
	public void setTopMenu(TopMenu topMenu) {
		this.topMenu = topMenu;
	}

	public ControlArea getControls() {
		return controls;
	}

	public void setControls(ControlArea controls) {
		this.controls = controls;
	}

	public DarkTichuModel getModel() {
		return model;
	}

	public void setModel(DarkTichuModel model) {
		this.model = model;
	}

	public TilePane getPlayers() {
		return players;
	}

	public void setPlayers(TilePane players) {
		this.players = players;
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
		return topMenu.removePlayer;
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

	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) players.getChildren().get(i);
	}

	public Button getShuffleButton() {
		return controls.btnShuffle;
	}

	public Button getDealButton() {
		return controls.btnDeal;
	}

	public Button getWinnerButton() {
		return controls.btnWinner;
	}

	public Button getAddPlayerButton() {
		return controls.btnAddPlayer;
	}

	public Label getWinnerLabel() {
		return controls.lblWinner;
	}



}






//
//
//public class DarkTichuView {
//	private DarkTichuModel model;
//	private Stage primaryStage;
//	private BorderPane root;
//	private TopMenuNEW topMenu;
//	private BorderPane bpCenter;
//	private ControlArea controlArea;
//	private BorderPane bpCenter2;
//	private HBox mainPlayerHb;
//	private VBox playerLeft;
//	private HBox playerTop;
//	private VBox playerRight;
//	
//	public DarkTichuView(Stage primaryStage, DarkTichuModel model) {
//		this.model = model;
//		this.primaryStage = primaryStage;
//		topMenu= new TopMenuNEW();
//		root = new BorderPane();
//		bpCenter= new BorderPane();
//		controlArea = new ControlArea();
//		playerTop= new HBox();
//		bpCenter2 = new BorderPane();
//		mainPlayerHb = new HBox();
//		playerLeft= new VBox();
//		playerRight = new VBox();
//	
//		
//		
//		playerLeft.getChildren().add(setPlayersImage());
//		playerRight.getChildren().add(new Label ("Right Player"));
//		playerTop.getChildren().addAll(new Label(""),new Label ("Team Player"));
//		mainPlayerHb.getChildren().add(new Label("Main Player"));
//		topMenu.setId("root");
//		topMenu.setStyle("topMenuStage.css");
//		
//		
//		playerTop.setAlignment(Pos.CENTER);
//		playerLeft.setAlignment(Pos.CENTER);
//		playerRight.setAlignment(Pos.CENTER_RIGHT);
//		mainPlayerHb.setAlignment(Pos.TOP_CENTER);
//		
//		playerTop.getChildren().add(new Label("Now playing: ---"));
//		
//		
//		root.setTop(topMenu.getMenuBar());
//		
//		root.setCenter(bpCenter);
//		root.setBottom(controlArea);
//		root.autosize();
//	
//		bpCenter.setTop(playerTop);
//		bpCenter.setCenter(bpCenter2);
//		bpCenter.setBottom(mainPlayerHb);
//		bpCenter2.setLeft(playerLeft);
//		bpCenter2.setRight(playerRight);
//		//bpCenter2.setCenter(new Region());
//	
//		inicialScene();
//		
//		
//	}
//	public CardLabel setPlayersImage() {
//		CardLabel card= new CardLabel();
//		card.setCard(new Card(Suit.Pagodas, Rank.Two));
//		return card;
//		
//	}
////		Image image = new Image(
////				getClass().getClassLoader().getResourceAsStream("dark_Tichu/images/SW_Queen.jpg"));
////		ImageView imv = new ImageView(image);
////		imv.fitWidthProperty().bind(this.widthProperty());
////		imv.fitHeightProperty().bind(this.heightProperty());
////		this.setGraphic(imv);
////		return imv;
//
//	
//	
//	// Create inicial players Scene
//	public void inicialScene() {
//		Scene scene = new Scene(root,1100,800);
//		scene.getStylesheets().add(getClass().getResource("blue.css").toExternalForm());
//		primaryStage.setTitle("Tichu");
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}
//	
//	
//	// All Getters & Setters
//
//	public BorderPane getRoot() {
//		return root;
//	}
//
//	public TopMenuNEW getTopMenu() {
//		return topMenu;
//	}
//
//	public void setTopMenu(TopMenuNEW topMenu) {
//		this.topMenu = topMenu;
//	}
//
//	public BorderPane getBpCenter() {
//		return bpCenter;
//	}
//
//	public ControlArea getControlArea() {
//		return controlArea;
//	}
//
//	public void setControlArea(ControlArea controlArea) {
//		this.controlArea = controlArea;
//	}
//
//	public HBox getPlayerTop() {
//		return playerTop;
//	}
//
//	public void setPlayerTop(HBox playerTop) {
//		this.playerTop = playerTop;
//	}
//
//	public BorderPane getBpCenter2() {
//		return bpCenter2;
//	}
//
//
//	public HBox getMainPlayerHb() {
//		return mainPlayerHb;
//	}
//
//	public void setMainPlayerHb(HBox mainPlayerHb) {
//		this.mainPlayerHb = mainPlayerHb;
//	}
//
//	public VBox getPlayerLeft() {
//		return playerLeft;
//	}
//
//	public void setPlayerLeft(VBox playerLeft) {
//		this.playerLeft = playerLeft;
//	}
//
//	public VBox getPlayerRight() {
//		return playerRight;
//	}
//
//	public void setPlayerRight(VBox playerRight) {
//		this.playerRight = playerRight;
//	}
//
//	public DarkTichuModel getModel() {
//		return model;
//	}
//
//	public void setModel(DarkTichuModel model) {
//		this.model = model;
//	}
//
//	public Stage getPrimaryStage() {
//		return primaryStage;
//	}
//
//	public void setPrimaryStage(Stage primaryStage) {
//		this.primaryStage = primaryStage;
//	}
//	public MenuItem getAboutItem() {
//		return topMenu.about;
//	}
//	public MenuItem getGenRulesItem() {
//		return topMenu.genRuls;
//	}
//	public MenuItem getHandsItem() {
//		return topMenu.hands;
//	}
//	public MenuItem getNewGameItem() {
//		return topMenu.newGame;
//	}
//	public MenuItem getExitItem() {
//		return topMenu.exit;
//	}
//	
//}


	