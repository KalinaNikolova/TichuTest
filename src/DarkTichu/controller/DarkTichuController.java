package DarkTichu.controller;

import java.util.ArrayList;
import java.util.Scanner;

import DarkTichu.model.Card;
import DarkTichu.model.DarkTichuModel;
import DarkTichu.model.DeckOfCards;
import DarkTichu.model.Player;
import DarkTichu.view.DarkTichuView;
import DarkTichu.view.PlayerPane;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.util.Duration;



public class DarkTichuController {
	private DarkTichuModel model;
	private DarkTichuView view;

	public DarkTichuController(DarkTichuModel model, DarkTichuView view) {
		this.model = model;
		this.view = view;
		
		view.getShuffleButton().setOnAction(e ->shuffle());
		view.getDealButton().setOnAction(e -> deal());
		view.getWinnerButton().setOnAction(e -> win());
		view.getAddPlayerButton().setOnAction(e -> addPlayer());
		view.getAboutItem().setOnAction(e -> about());
		view.getGenRulesItem().setOnAction(e -> genRules());
		view.getHandsItem().setOnAction(e -> hands());
		view.getNewGameItem().setOnAction(e -> {
			newGame();
			enablePlayerBtn();
		});
		view.getRemovePlayerItem().setOnAction(e -> removePlayer());
		view.getHighCardsItem().setOnAction(e -> loadFromUser("highCards"));
		view.getHighCardsSplitItem().setOnAction(e -> loadFromUser("highCardsSplit"));
		view.getPairItem().setOnAction(e -> loadFromUser("pair"));
		view.getPairSplitItem().setOnAction(e -> loadFromUser("pairSplit"));
		view.getTwoPairItem().setOnAction(e -> loadFromUser("twoPair"));
		view.getTwoPairSplitItem().setOnAction(e -> loadFromUser("twoPairSplit"));
		view.getThreeOfAKindItem().setOnAction(e -> loadFromUser("threeOfAKind"));
		view.getStraightItem().setOnAction(e -> loadFromUser("straight"));
		view.getStraightSplitItem().setOnAction(e -> loadFromUser("straightSplit"));
		view.getFlushItem().setOnAction(e -> loadFromUser("flush"));
		view.getFlushSplitItem().setOnAction(e -> loadFromUser("flushSplit"));
		view.getFullHouseItem().setOnAction(e -> loadFromUser("fullHouse"));
		view.getFourOfAKindItem().setOnAction(e -> loadFromUser("fourOfAKind"));
		view.getStraightFlushItem().setOnAction(e -> loadFromUser("straightFlush"));
		view.getStraightFlushSplitItem().setOnAction(e -> loadFromUser("straightFlushSplit"));
		view.getRoyalFlushItem().setOnAction(e -> loadFromUser("royalFlush"));
		view.getRoyalFlushSplitItem().setOnAction(e -> loadFromUser("royalFlushSplit"));
		view.getDifferentItem().setOnAction(e -> loadFromUser("different"));
		view.getDifferentSplitItem().setOnAction(e -> loadFromUser("differentSplit"));
		view.getCloseItem().setOnAction(e -> close());
	}

	// load specific hands for the Trial Menu
	private void loadFromUser(String hands) {
		for (int i = 0; i < model.getPlayersCount(); i++) {
			Player p = model.getPlayer(i);
			p.discardHand();
			PlayerPane pp = view.getPlayerPane(i);
			pp.updatePlayerDisplay();
		}
		model.getDeck().loadFromFile(hands);
	}

	/**
	 * Remove all cards from players hands, and shuffle the deck
	 */
	private void shuffle() {
	
		for (int i = 0; i < model.getPlayersCount(); i++) {
			Player p = model.getPlayer(i);
			p.discardHand();
			PlayerPane pp = view.getPlayerPane(i);
			pp.updatePlayerDisplay();
			updateWinnerLabel();
			if (model.getPlayersCount() == 4) {
				view.getAddPlayerButton().setDisable(true);
			}
		}

		model.getDeck().shuffle();
		
	}

	/**
	 * Deal each player five cards, then evaluate the two hands
	 */
	private void deal() {
		int cardsRequired = model.getPlayersCount() * Player.HAND_SIZE;
		DeckOfCards deck = model.getDeck();
		if (cardsRequired <= deck.getCardsRemaining()) {
			for (int i = 0; i < model.getPlayersCount(); i++) {
				Player p = model.getPlayer(i);
				p.discardHand();
				for (int j = 0; j < Player.HAND_SIZE; j++) {
					Card card = deck.dealCard();
					p.addCard(card);
				}
				p.evaluateHand();
				PlayerPane pp = view.getPlayerPane(i);
				pp.updatePlayerDisplay();
				updateWinnerLabel();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR, "Not enough cards - shuffle first");
			alert.showAndWait();
		}
	}

	private void win() { // get winner label
		try {
			String content = ("");

			view.getWinnerLabel().setStyle("-fx-text-fill: #FB0505");
			content = model.win();
			animation("Winner: " + content);

			ArrayList<Integer> wnumber = new ArrayList<>();

			for (char ch : content.toCharArray()) {
				if (Character.isDigit(ch)) {
					wnumber.add(Character.getNumericValue(ch));
				}
			}

			for (int i = 0; i < wnumber.size(); i++) {
				view.getPlayerPane((wnumber.get(i) - 1)).highlight();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "DEAL cards first and then check the WINNER!");
			alert.showAndWait();
		}

	}

	// add player and disable or enable the button
	private void addPlayer() {

		if (model.getPlayersCount() < 4) {
			model.addPlayer();
			view.addPlayer(model, model.getPlayersCount() - 1);
			if (view.getRemovePlayerItem().isDisable()) {
				view.getRemovePlayerItem().setDisable(false);
			}
			if (model.getPlayersCount() == 4) {
				view.getAddPlayerButton().setDisable(true);
			}
		}
	}

	// Remove player from the Menu and disable or enable the Item
	private void removePlayer() {
		if (model.getPlayersCount() > 2) {
			model.removePlayer();
			view.removePlayer();
			if (view.getAddPlayerButton().isDisabled()) {
				enablePlayerBtn();
			}
			if (model.getPlayersCount() == 2) {
				view.getRemovePlayerItem().setDisable(true);
			}
		}
	}

	public void enablePlayerBtn() {
		view.getAddPlayerButton().setDisable(false);
	}

	// start new game
	public void newGame() {
		shuffle();
	}

	public void close() {
		Platform.exit();
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
		Scanner scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("poker/images/" + fileName));
		String line;
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			text += "\t" + line + "\t\n";
		}
		Label lbl = new Label(text);
		view.createMenuStage("topMenuStage.css", lbl, new HBox());
	}

	// Show animation on the winner label
	public void animation(String content) {

		Transition animation = new Transition() {
			{
				setCycleDuration(Duration.millis(2000));
			}

			protected void interpolate(double split) {
				final int length = content.length();
				final int n = Math.round(length * (float) split);
				view.getWinnerLabel().setText(content.substring(0, n));
			}
		};
		animation.play();
	}

	// Update label
	public void updateWinnerLabel() {
		view.getWinnerLabel().setText("");
	}

	}	


