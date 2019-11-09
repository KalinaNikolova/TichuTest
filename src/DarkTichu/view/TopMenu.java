package DarkTichu.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopMenu extends MenuBar {
	private MenuBar menuBar = new MenuBar();

	// Create Menu Bar
	// Menu File
	Menu file = new Menu("File");
	MenuItem newGame = new MenuItem("New Game");
	MenuItem removePlayer = new MenuItem("Remove player");
	// Trial
	Menu trial = new Menu("Trial");
	// High Cards
	Menu sub1 = new Menu("High Card");
	MenuItem highCards = new MenuItem("One winner");
	MenuItem highCardsSplit = new MenuItem("Split pots");

	// Pair
	Menu sub2 = new Menu("One Pair");
	MenuItem pair = new MenuItem("One winner");
	MenuItem pairSplit = new MenuItem("Split pots");

	// 2 pairs
	Menu sub3 = new Menu("Two Pair");
	MenuItem twoPair = new MenuItem("One winner");
	MenuItem twoPairSplit = new MenuItem("Split pots");
	// 3 of a kind ; only 1 winner
	MenuItem threeOfKind = new MenuItem("3 of a Kind");

	// Straight
	Menu sub4 = new Menu("Straight");
	MenuItem straight = new MenuItem("One Winner");
	MenuItem straightSplit = new MenuItem("Split pots");
	// Flush
	Menu sub5 = new Menu("Flush");
	MenuItem flush = new MenuItem("One winner");
	MenuItem flushSplit = new MenuItem("Split pots");
	// FullHouse
	MenuItem fullHouse = new MenuItem("Full House");
	// FourOfAKind
	MenuItem fourOfAKind = new MenuItem("4 of a Kind");
	// StraightFlush
	Menu sub6 = new Menu("Straight Flush");
	MenuItem straightFlush = new MenuItem("One winner");
	MenuItem straightFlushSplit = new MenuItem("Split pots");
	// RoyalFlush
	Menu sub7 = new Menu("Royal Flush");
	MenuItem royalFlush = new MenuItem("One winner");
	MenuItem royalFlushSplit = new MenuItem("Split pots");
	// combinations of different hands
	Menu sub8 = new Menu("Differet HandTypes");
	MenuItem different = new MenuItem("One winner");
	MenuItem differentSplit = new MenuItem("Split pots");

	MenuItem close = new MenuItem("Close");
	// Menu Help
	Menu help = new Menu("Help");
	MenuItem about = new MenuItem("About");
	Menu subtMenu = new Menu("Rules");
	MenuItem hands = new MenuItem("Hands");
	MenuItem genRuls = new MenuItem("General Rules");

	public TopMenu() {
		super();
		// add to the SubMenus menuItems
		subtMenu.getItems().addAll(hands, genRuls);// About submenu
		// trial submenu
		sub1.getItems().addAll(highCards, highCardsSplit);
		sub2.getItems().addAll(pair, pairSplit);
		sub3.getItems().addAll(twoPair, twoPairSplit);
		sub4.getItems().addAll(straight, straightSplit);
		sub5.getItems().addAll(flush, flushSplit);
		sub6.getItems().addAll(straightFlush, straightFlushSplit);
		sub7.getItems().addAll(royalFlush, royalFlushSplit);
		sub8.getItems().addAll(different, differentSplit);

		// Add MenuItem to each Menu
		file.getItems().addAll(newGame, removePlayer, close);
		trial.getItems().addAll(sub1, sub2, sub3, threeOfKind, sub4, sub5, fullHouse, fourOfAKind, sub6, sub7, sub8);
		help.getItems().addAll(subtMenu, about);
		menuBar.getMenus().addAll(file, trial, help);

	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

}
