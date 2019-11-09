package DarkTichu.model;

import java.util.ArrayList;

public class TieBreak {

	private ArrayList<Card> deck1;
	private ArrayList<Card> deck2;
	private String handtype;
	private int p1;
	private int p2;
	private int winner = 0;
	private final static int tieNr = -1;

	public TieBreak(ArrayList<Card> deck1, ArrayList<Card> deck2, String handtype) {
		this.deck2 = deck1; // set the correct deck, has to be reversed
		this.deck1 = deck2;
		this.handtype = handtype;

	}

	public int getWinnerTie() {

		int[] valueList1 = HandType.getValueList(deck1);
		int[] valueList2 = HandType.getValueList(deck2);
		valueList1 = HandType.getValueList(deck1);
		valueList2 = HandType.getValueList(deck2);

		switch (handtype) {

		case "Straight":
		case "StraightFlush": // higher rank wins

		{
			p1 = TieBreak.getHighestCards(deck1);
			p2 = TieBreak.getHighestCards(deck2);

			if (p1 > p2) {
				winner = 1;
			} else if (p1 < p2) {
				winner = 2;
			} else {
				winner = tieNr;
			}
		}
			break;

		case "HighCard":
		case "Flush": {

			winner = TieBreak.getWDeck(valueList1, valueList2);

		}

			break;

		case "OnePair": // First compare the pairs and the the next highest kicker,
		case "ThreeOfAKind": // if necessary compare the next kicker.
		case "FourOfAKind": {
			if (TieBreak.getPair(valueList1) > TieBreak.getPair(valueList2)) {
				winner = 1;
			} else if (TieBreak.getPair(valueList1) < TieBreak.getPair(valueList2)) {
				winner = 2;
			} else
				winner = TieBreak.getWDeck(valueList1, valueList2);
		}

			break;

		case "TwoPair": { // first check higher pair, then the second pair

			if (TieBreak.getFirstPair(valueList1) > TieBreak.getFirstPair(valueList2)) {
				winner = 1;
			} else if (TieBreak.getFirstPair(valueList1) < TieBreak.getFirstPair(valueList2)) {
				winner = 2;
			}

			else if (TieBreak.getPair(valueList1) > TieBreak.getPair(valueList2)) {
				winner = 1;
			} else if (TieBreak.getPair(valueList1) < TieBreak.getPair(valueList2)) {
				winner = 2;
			}

			else {
				winner = TieBreak.getWDeck(valueList1, valueList2);

			}
		}

			break;

		case "FullHouse": { // highest three matching cards win

			int nr1 = 0;
			int nr2 = 0;

			for (int i = 0; i < 2; i++) {
				if (i == 0 && valueList1[0] == valueList1[1] && valueList1[0] == valueList1[2]) {
					nr1 = valueList1[0];
				} else if (i == 0) {
					nr1 = valueList1[4];
				}

				else if (i == 1 && valueList2[0] == valueList2[1] && valueList2[0] == valueList2[2]) {
					nr2 = valueList2[0];
				}

				else if (i == 1) {
					nr2 = valueList2[4];
				}
			}
			if (nr1 > nr2) {
				winner = 1;
			} else if (nr1 < nr2) {
				winner = 2;
			} else {
				winner = tieNr;

			}
		}
			break;

		case "RoyalFlush": { // always a tie
			winner = tieNr;
		}

		}

		return winner;

	}

	private static int getHighestCards(ArrayList<Card> deck) {
		int[] valueList = new int[5];
		int highestNr;
		valueList = HandType.getValueList(deck);
		highestNr = valueList[4];

		return highestNr;
	}

	public static int getPair(int[] ans) {
		int temp = 0;

		for (int i = 0; i < ans.length - 1; i++) {

			if (ans[i] == ans[i + 1]) {
				temp = ans[i];
				break;
			}
		}
		return temp;
	}

	public static int getFirstPair(int[] ans) {
		int temp = 0;

		for (int i = 4; i > 0; i++) {

			if (ans[i] == ans[i - 1]) {
				temp = ans[i];
				break;
			}
		}
		return temp;
	}

	public static int getWDeck(int[] list1, int[] list2) { // compares all cards, returns winner deck in case of tie -1
		int winner = 0;
		for (int i = list1.length - 1; i >= 0; i--) {
			if (list1[i] > list2[i]) {
				winner = 1;
				break;
			} else if (list1[i] < list2[i]) {
				winner = 2;
				break;
			} else
				winner = tieNr;
		}

		return winner;
	}

}
