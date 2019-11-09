package DarkTichu.model;

import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class represents a deck of playing cards. When initially created, the
 * deck is filled and shuffled. Later, the deck can be refilled and reshuffled
 * by calling the "shuffle" method.
 */
public class DeckOfCards {
	private final ArrayList<Card> cards = new ArrayList<>();
	private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();
	private static String[][] highCards = { // high cards
			{ "KC", "6S", "QS", "JD", "TS" }, { "JC", "5C", "QH", "7H", "TC" }, { "2H", "5H", "6C", "JS", "QH" },
			{ "AD", "JS", "2H", "TD", "QD" } // 1 winner
	};
	private static String[][] highCardsSplit = { // high cards
			{ "KC", "3S", "QS", "6S", "TS" }, { "AS", "JH", "2S", "5S", "QH" }, // tie 1
			{ "2H", "5H", "6C", "JH", "4H" }, { "AH", "JD", "2D", "5C", "QD" } // tie 2
	};
	private static String[][] pair = { //
			{ "9D", "KS", "2S", "9S", "TS" }, { "QC", "KC", "QH", "JD", "TH" }, // 1 winner
			{ "KH", "8H", "JH", "8D", "7H" }, { "AD", "TC", "KD", "TD", "QD" } };
	private static String[][] pairSplit = { { "QD", "KS", "QS", "JS", "TS" }, // tie 1
			{ "QC", "KC", "QH", "JD", "TH" }, // tie 2
			{ "KH", "8H", "JH", "8D", "QH" }, { "AD", "TC", "KD", "TD", "QD" } };
	private static String[][] twoPair = { // 2x2
			{ "QD", "KS", "QS", "KH", "TS" }, // 1 winner
			{ "5C", "5D", "8C", "8H", "JC" }, { "JS", "8H", "JH", "JD", "QH" }, { "2D", "2C", "4D", "4D", "KD" } };
	private static String[][] twoPairSplit = { { "QD", "KS", "QS", "KH", "TS" }, // tie 1
			{ "QC", "KC", "QH", "KD", "TH" }, // tie 2
			{ "7H", "8H", "JH", "8D", "QH" }, { "AD", "TC", "KD", "TD", "QD" } };
	private static String[][] threeOfAKind = { { "AD", "KS", "AS", "AH", "TS" }, // winner
			{ "9C", "9S", "9D", "AH", "TC" }, { "KH", "8H", "8C", "8D", "QH" }, { "AC", "2C", "KD", "2D", "2D" } };

	private static String[][] straight = { //
			{ "4C", "5S", "6S", "7S", "8S" }, //
			{ "6C", "7C", "8H", "9H", "TC" }, //
			{ "TH", "9D", "JH", "KH", "QD" }, //
			{ "AD", "QS", "KC", "TS", "JD" } // winner
	};

	private static String[][] straightSplit = { //
			{ "4C", "6S", "8S", "7S", "5S" }, // tie 2
			{ "8D", "7C", "6H", "5H", "4D" }, // tie 1
			{ "5D", "4S", "3H", "7H", "6C" }, //
			{ "2D", "3S", "5C", "4H", "6D" } //
	};
	private static String[][] flush = { { "4C", "KC", "6C", "TC", "8C" }, // winner
			{ "8S", "3S", "5S", "JS", "7S" }, //
			{ "2H", "5H", "3H", "4H", "9H" }, //
			{ "2D", "5D", "8D", "TD", "JD" } //
	};
	private static String[][] flushSplit = { //
			{ "4S", "6S", "8S", "7S", "JS" }, // tie
			{ "4D", "6D", "8D", "7D", "JD" }, // tie
			{ "7H", "6H", "2H", "8H", "9H" }, //
			{ "TC", "9C", "8C", "2C", "3C" } //
	};
	private static String[][] fullHouse = { //
			{ "KC", "KS", "KH", "TC", "TS" }, //
			{ "3S", "3C", "3H", "4H", "4C" }, //
			{ "AC", "AS", "AH", "TH", "TD" }, // winner
			{ "5D", "5S", "5C", "7H", "7H" } //
	};
	private static String[][] fourOfAKind = { //
			{ "AC", "AS", "AH", "AD", "5S" }, // winner
			{ "KS", "KC", "KH", "KD", "4D" }, //
			{ "2H", "2H", "2H", "2H", "9H" }, //
			{ "9D", "9S", "9C", "9H", "6D" } //
	};
	private static String[][] straightFlush = { //
			{ "4S", "5S", "6S", "7S", "8S" }, //
			{ "3H", "4H", "5H", "6H", "7H" }, //
			{ "2D", "3D", "4D", "5D", "6D" }, //
			{ "AC", "QC", "KC", "TC", "JC" } // winner
	};
	private static String[][] straightFlushSplit = { //
			{ "5S", "6S", "8S", "7S", "9S" }, // tie1
			{ "8D", "7D", "6D", "5D", "9D" }, // tie2
			{ "7H", "6H", "5H", "8H", "4H" }, //
			{ "3C", "7C", "4C", "6C", "5C" } //
	};
	private static String[][] royalFlush = { //
			{ "AS", "KS", "QS", "JS", "TS" }, // winner
			{ "3S", "4C", "9H", "6H", "7C" }, //
			{ "2H", "7H", "3H", "4H", "9H" }, //
			{ "AD", "2S", "KC", "5H", "JD" } //
	};
	private static String[][] royalFlushSplit = { //
			{ "AS", "KS", "QS", "JS", "TS" }, //
			{ "AD", "KD", "QD", "JD", "TD" }, // tie2
			{ "AH", "KH", "QH", "JH", "TH" }, // tie3
			{ "AC", "KC", "QC", "JC", "TC" } // tie4 { "TC", "JC", "QC", "KC", "AC" }
	};

	private static String[][] different = { // different kind of cards
			{ "KC", "KS", "5S", "JS", "TS" }, // 2
			{ "QS", "QC", "QH", "JH", "TC" }, // 3
			{ "2H", "5H", "3H", "4H", "9H" }, // flush
			{ "AD", "AS", "AC", "6H", "6D" } // 3+2 full house WINNER
	};
	private static String[][] differentSplit = { // high cards
			{ "KC", "KS", "QH", "JH", "TS" }, { "AS", "JS", "9S", "TS", "QS" }, // tie 1
			{ "2H", "5H", "6C", "JH", "4H" }, { "AD", "JD", "9D", "TD", "QD" } // tie 2
	};

	/**
	 * We only ever have one deck of cards, so we do not set an ID attribute.
	 */
	public DeckOfCards() {
		shuffle();
	}

	// This is where we store the translated hands

	ArrayList<ArrayList<Card>> highCardsHands;
	ArrayList<ArrayList<Card>> highCardsSplitHands;
	ArrayList<ArrayList<Card>> pairHands;
	ArrayList<ArrayList<Card>> pairSplitHands;
	ArrayList<ArrayList<Card>> twoPairHands;
	ArrayList<ArrayList<Card>> twoPairSplitHands;
	ArrayList<ArrayList<Card>> threeOfAKindHands;
	ArrayList<ArrayList<Card>> straightHands;
	ArrayList<ArrayList<Card>> straightSplitHands;
	ArrayList<ArrayList<Card>> flushHands;
	ArrayList<ArrayList<Card>> flushSplitHands;
	ArrayList<ArrayList<Card>> fullHouseHands;
	ArrayList<ArrayList<Card>> fourOfAKindHands;
	ArrayList<ArrayList<Card>> straightFlushHands;
	ArrayList<ArrayList<Card>> straightFlushSplitHands;
	ArrayList<ArrayList<Card>> royalFlushHands;
	ArrayList<ArrayList<Card>> royalFlushSplitHands;
	ArrayList<ArrayList<Card>> differentHands;
	ArrayList<ArrayList<Card>> differentSplitHands;

	/**
	 * The makeHands method prepares the translated hands.
	 */
	public void makeHands() {
		highCardsHands = makeHands(highCards);
		highCardsSplitHands = makeHands(highCardsSplit);
		pairHands = makeHands(pair);
		pairSplitHands = makeHands(pairSplit);
		twoPairHands = makeHands(twoPair);
		twoPairSplitHands = makeHands(twoPairSplit);
		threeOfAKindHands = makeHands(threeOfAKind);
		straightHands = makeHands(straight);
		straightSplitHands = makeHands(straightSplit);
		flushHands = makeHands(flush);
		flushSplitHands = makeHands(flushSplit);
		fullHouseHands = makeHands(fullHouse);
		fourOfAKindHands = makeHands(fourOfAKind);
		straightFlushHands = makeHands(straightFlush);
		straightFlushSplitHands = makeHands(straightFlushSplit);
		royalFlushHands = makeHands(royalFlush);
		royalFlushSplitHands = makeHands(royalFlushSplit);
		differentHands = makeHands(different);
		differentSplitHands = makeHands(differentSplit);
	}

	/**
	 * How many cards are left in the deck?
	 */
	public SimpleIntegerProperty getCardsRemainingProperty() {
		return cardsRemaining;
	}

	public int getCardsRemaining() {
		return cardsRemaining.get();
	}

	/**
	 * Gather all 52 cards, and shuffle them
	 */
	public void shuffle() {
		// Remove all cards
		cards.clear();

		// Add all 52 cards
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Rank rank : Card.Rank.values()) {
				Card card = new Card(suit, rank);
				cards.add(card);
			}
		}

		// Shuffle
		Collections.shuffle(cards);
		cardsRemaining.setValue(cards.size());
	}

	/**
	 * Take one card from the deck and return it
	 * 
	 * This is an example of conditional assignment
	 */
	public Card dealCard() {
		Card card = (cards.size() > 0) ? cards.remove(cards.size() - 1) : null;
		cardsRemaining.setValue(cards.size());
		return card;
	}

	// load cards for each type of hands for the Trial Menu
	public void loadFromFile(String hands) {
		cards.clear();
		makeHands();
		switch (hands) {
		case "highCards":
			for (ArrayList<Card> hand : highCardsHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			System.out.println(cards.size());
			break;
		case "highCardsSplit":
			for (ArrayList<Card> hand : highCardsSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "pair":
			for (ArrayList<Card> hand : pairHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "pairSplit":
			for (ArrayList<Card> hand : pairSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "twoPair":
			for (ArrayList<Card> hand : twoPairHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "twoPairSplit":
			for (ArrayList<Card> hand : twoPairSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "threeOfAKind":
			for (ArrayList<Card> hand : threeOfAKindHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "straight":
			for (ArrayList<Card> hand : straightHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "straightSplit":
			for (ArrayList<Card> hand : straightSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "flush":
			for (ArrayList<Card> hand : flushHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "flushSplit":
			for (ArrayList<Card> hand : flushSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "fullHouse":
			for (ArrayList<Card> hand : fullHouseHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "fourOfAKind":
			for (ArrayList<Card> hand : fourOfAKindHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "straightFlush":
			for (ArrayList<Card> hand : straightFlushHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "straightFlushSplit":
			for (ArrayList<Card> hand : straightFlushSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "royalFlush":
			for (ArrayList<Card> hand : royalFlushHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "royalFlushSplit":
			for (ArrayList<Card> hand : royalFlushSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "different":
			for (ArrayList<Card> hand : differentHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		case "differentSplit":
			for (ArrayList<Card> hand : differentSplitHands) {
				for (Card card : hand) {
					cards.add(card);
				}
			}
			break;
		}
		cardsRemaining.setValue(cards.size());

	}

	/**
	 * Make an ArrayList of hands from an array of string-arrays
	 */
	private ArrayList<ArrayList<Card>> makeHands(String[][] handsIn) {
		ArrayList<ArrayList<Card>> handsOut = new ArrayList<>();
		for (String[] hand : handsIn) {
			handsOut.add(makeHand(hand));
		}
		return handsOut;
	}

	/**
	 * Make a hand (ArrayList<Card>) from an array of 5 strings
	 */
	private ArrayList<Card> makeHand(String[] inStrings) {
		ArrayList<Card> hand = new ArrayList<>();
		for (String in : inStrings) {
			hand.add(makeCard(in));
		}
		return hand;
	}

	/**
	 * Create a card from a 2-character String. First character is the rank (2-9, T,
	 * J, Q, K, A) Second character is the suit (C, D, H, S)
	 * 
	 * No validation or error handling!
	 */
	private Card makeCard(String in) {
		char r = in.charAt(2);
		Card.Rank rank = null;
		if (r <= '9')
			rank = Card.Rank.values()[r - '0' - 2];
		else if (r == 'T')
			rank = Card.Rank.Ten;
		else if (r == 'J')
			rank = Card.Rank.Jack;
		else if (r == 'Q')
			rank = Card.Rank.Queen;
		else if (r == 'K')
			rank = Card.Rank.King;
		else if (r == 'A')
			rank = Card.Rank.Ace;

		char s = in.charAt(1);
		Card.Suit suit = null;
		if (s == 'J')
			suit = Card.Suit.Jade;
		if (s == 'P')
			suit = Card.Suit.Pagodas;
		if (s == 'S')
			suit = Card.Suit.Stars;
		if (s == 'W')
			suit = Card.Suit.Swords;

		return new Card(suit, rank);
	}
}
