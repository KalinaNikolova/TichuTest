/**
 * 
 */
package test.com.dark.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dark.server.Card;
import com.dark.server.HandType;


public class HandTypeTest {
	// We define the hands using abbreviations. The code at the bottom
	// of this class can translate one of these strings into a card.
	//
	// Another method takes a set of five cards, and translates the whole hand
	//
	// Yet another method does this for a whole set of hands
	private static String[][] singleCards = { 
			{ "2S", "2w", "5S", "4S", "7S", "6S", "TS", "8S", "9S", "JS", "AS", "KS", "QS"},  //A
			{ "3w", "3S", "4w", "5w", "6w", "Kw", "Qw", "Aw", "7w", "8w", "9w", "Tw", "Jw"}, //A
			{ "AJ", "TJ", "9J", "8J", "7J", "6J", "5P", "5J", "2J", "3J", "QJ", "KJ", "2J" }, //A
			{ "JP", "QP", "KP", "AP", "2P", "3P", "4J", "4P", "7P", "6P", "8P", "TP", "9P" } }; //A

	private static String[][] onePairs = { 
			{ "2S", "2w", "5S", "4S", "7S", "6S", "TS", "8S", "9S", "JS", "AS", "KS", "QS"},  //2,2
			{ "3w", "3S", "4w", "5w", "6w", "Kw", "Qw", "Aw", "7w", "8w", "9w", "Tw", "Jw"}, //3,3
			{ "AJ", "TJ", "9J", "8J", "7J", "6J", "5P", "5J", "2J", "3J", "QJ", "KJ", "2J" }, //5,5
			{ "JP", "QP", "KP", "AP", "2P", "3P", "4J", "4P", "7P", "6P", "8P", "TP", "9P" } }; //4,4

	private static String[][] trio =  { 
			{ "2S", "2w", "2P", "4S", "7S", "6S", "TS", "8S", "9S", "JS", "7J", "KS", "QS"}, //222   
			{ "3w", "3S", "4w", "5w", "6w", "Kw", "Qw", "5J", "7w", "8w", "9w", "Tw", "3P"}, //333   
			{ "AJ", "TJ", "9J", "8J", "AS", "6J", "5P", "Aw", "2J", "3J", "QJ", "KJ", "2J" }, //AAA	 
			{ "JP", "QP", "KP", "AP", "2P", "Jw", "4J", "4P", "7P", "6P", "8P", "TP", "JS" } }; //JJJ 
	private static String[][] fourOfAKind = { 
			{ "2S", "2w", "2P", "2J", "7S", "6S", "TS", "8S", "9S", "JS", "AS", "KS", "QS"},  //A,A,A,A
			{ "3w", "3S", "4w", "5w", "6w", "Kw", "Qw", "Aw", "7w", "8w", "9w", "Tw", "Jw"}, //
			{ "AJ", "TJ", "9J", "8J", "7J", "6J", "5P", "5J", "2J", "3J", "QJ", "KJ", "2J" }, //
			{ "JP", "QP", "KP", "AP", "2P", "3P", "4J", "4P", "7P", "6P", "8P", "TP", "9P" } }; //
	 
	private static String[][] fullHouse = { 
			{ "2S", "2w", "2P", "4S", "4w", "6S", "TS", "8S", "9S", "JS", "AS", "KS", "QS"}, //22244
			{ "3w", "3S", "4w", "5w", "6w", "Kw", "Qw", "Aw", "7w", "8w", "9w", "Tw", "Jw"}, //
			{ "AJ", "TJ", "9J", "8J", "7J", "6J", "5P", "5J", "2J", "3J", "QJ", "KJ", "2J" }, //
			{ "JP", "QP", "KP", "AP", "JP", "3P", "4J", "4P", "7P", "6P", "8P", "TP", "9P" } }; //

	private static String[][] sequenceOfPair = { 
			{ "2S", "2w", "3P", "3J", "7S", "6S", "TS", "8S", "9S", "JS", "AS", "KS", "QS"},  //223344
			{ "3w", "3S", "4w", "5w", "6w", "Kw", "Qw", "Aw", "7w", "8w", "9w", "Tw", "Jw"}, //
			{ "AJ", "TJ", "9J", "8J", "7J", "6J", "5P", "5J", "2J", "3J", "QJ", "KJ", "2J" }, //
			{ "JP", "QP", "KP", "AP", "2P", "3P", "4J", "4P", "7P", "6P", "8P", "TP", "9P" } }; //
	private static String[][] sequence = {  
			{ "2S", "2w", "3P", "3J", "7S", "6S", "TS", "8S", "9S", "JS", "AS", "KS", "QS"},  //23456
			{ "3w", "3S", "4w", "5w", "6w", "Kw", "Qw", "Aw", "7w", "8w", "9w", "Tw", "Jw"}, //
			{ "AJ", "TJ", "9J", "8J", "7J", "6J", "5P", "5J", "2J", "3J", "QJ", "KJ", "2J" }, //
			{ "JP", "QP", "KP", "AP", "2P", "3P", "4J", "4P", "7P", "6P", "8P", "TP", "9P" } }; //

	
	// This is where we store the translated hands
	ArrayList<ArrayList<Card>> singleCardHands;
	ArrayList<ArrayList<Card>> onePairHands;
	ArrayList<ArrayList<Card>> sequenceOfPairHands;
	ArrayList<ArrayList<Card>> trioHands;
	ArrayList<ArrayList<Card>> fourOfAKindHands;
	ArrayList<ArrayList<Card>> fullHouseHands;
	ArrayList<ArrayList<Card>> sequenceHands;
	
	/**
	 * The makeHands method is called before each test method, and prepares the
	 * translated hands. We recreate these for each test method, in case the test
	 * method damages the data.
	 */
	@Before
	public void makeHands() {
		 singleCardHands=makeHands(singleCards);
		 onePairHands=makeHands(onePairs);
		 sequenceOfPairHands=makeHands(sequenceOfPair);
		 trioHands=makeHands(trio);
		 fourOfAKindHands=makeHands(fourOfAKind);
		fullHouseHands=makeHands(fullHouse);
		sequenceHands=makeHands(sequence);
		
	}
	/**
	 * This is a test method for the isOnePair method in HandType. We expect all
	 * HighCard hands to be false, all OnePair hands to be true, all TwoPair hands
	 * to be true, etc.
	 */
	@Test
	public void testIsOnePair() {
		for (ArrayList<Card> hand : singleCardHands) {
			assertFalse(HandType.isOnePair(hand));
		}
		for (ArrayList<Card> hand : onePairHands) {
			assertTrue(HandType.isOnePair(hand));
		}
		for (ArrayList<Card> hand : sequenceOfPairHands) {
			assertTrue(HandType.isOnePair(hand)); // Two-pair contains a pair
		}
		for (ArrayList<Card> hand : trioHands) {
			assertTrue(HandType.isOnePair(hand));
		}
		for (ArrayList<Card> hand : fourOfAKindHands) {
			assertFalse(HandType.isOnePair(hand));
		}
		for (ArrayList<Card> hand : sequenceHands) {
			assertFalse(HandType.isOnePair(hand));
		}

	}

//	/**
//	 * This is the test method for the isTwoPair in HandType.
//	 */
//	@Test
//	public void testIsTwoPair() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isSequenceOfPair(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isTrio(hand));
//		}
//		for (ArrayList<Card> hand : sequenceOfPairHands) {
//			assertFalse(HandType.isTrio(hand));
//		}
//		for (ArrayList<Card> hand : trioHands) {
//			assertFalse(HandType.isTrio(hand));
//		}
//		for (ArrayList<Card> hand : fourOfAKindHands) {
//			assertTrue(HandType.isOnePair(hand));
//		}
//		for (ArrayList<Card> hand : sequenceHands) {
//			assertFalse(HandType.isSequenceOfPair(hand));
//		}
//
//	}

//	/**
//	 * This is the test method for the isThreeOfAKind in HandType.
//	 */
//	@Test
//	public void testIsThreeOfAKind() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isThreeOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isThreeOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : twoPairHands) {
//			assertFalse(HandType.isThreeOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : threeOfAKindHands) {
//			assertTrue(HandType.isThreeOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : fourOfAKindHands) {
//			assertTrue(HandType.isThreeOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : sequence) {
//			assertFalse(HandType.isThreeOfAKind(hand));
//		}
//
//	}
//
//	/**
//	 * This is the test method for the isFourOfAKind in HandType.
//	 */
//	@Test
//	public void testIsFourOfAKind() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isFourOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isFourOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : twoPairHands) {
//			assertFalse(HandType.isFourOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : threeOfAKindHands) {
//			assertFalse(HandType.isFourOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : fourOfAKindHands) {
//			assertTrue(HandType.isFourOfAKind(hand));
//		}
//		for (ArrayList<Card> hand : sequence) {
//			assertFalse(HandType.isFourOfAKind(hand));
//		}
//
//	}
//
//	/**
//	 * This is the test method for the flush in HandType.
//	 */
//	@Test
//	public void testFlush() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isFlush(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isFlush(hand));
//		}
//		for (ArrayList<Card> hand : twoPairHands) {
//			assertFalse(HandType.isFlush(hand));
//		}
//		for (ArrayList<Card> hand : threeOfAKindHands) {
//			assertFalse(HandType.isFlush(hand));
//		}
//		for (ArrayList<Card> hand : fourOfAKindHands) {
//			assertFalse(HandType.isFlush(hand));
//		}
//		for (ArrayList<Card> hand : sequence) {
//			assertTrue(HandType.isFlush(hand));
//		}
//
//	}
//
//	/**
//	 * This is the test method for the straight in HandType.
//	 */
//	@Test
//	public void testStraight() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isStraight(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isStraight(hand));
//		}
//		for (ArrayList<Card> hand : twoPairHands) {
//			assertFalse(HandType.isStraight(hand));
//		}
//		for (ArrayList<Card> hand : threeOfAKindHands) {
//			assertFalse(HandType.isStraight(hand));
//		}
//		for (ArrayList<Card> hand : fourOfAKindHands) {
//			assertFalse(HandType.isStraight(hand));
//		}
//		for (ArrayList<Card> hand : sequence) {
//			assertFalse(HandType.isStraight(hand));
//		}
//		for (ArrayList<Card> hand : straightHands) {
//			assertTrue(HandType.isStraight(hand));
//		}
//
//	}
//
//	/**
//	 * This is the test method for the straight in HandType.
//	 */
//	@Test
//	public void testStraightFlush() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isStraightFlush(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isStraightFlush(hand));
//		}
//		for (ArrayList<Card> hand : twoPairHands) {
//			assertFalse(HandType.isStraightFlush(hand));
//		}
//		for (ArrayList<Card> hand : threeOfAKindHands) {
//			assertFalse(HandType.isStraightFlush(hand));
//		}
//		for (ArrayList<Card> hand : fourOfAKindHands) {
//			assertFalse(HandType.isStraightFlush(hand));
//		}
//		for (ArrayList<Card> hand : sequence) {
//			assertFalse(HandType.isStraightFlush(hand));
//		}
//		for (ArrayList<Card> hand : straightHands) {
//			assertFalse(HandType.isStraightFlush(hand));
//		}
//		for (ArrayList<Card> hand : straightFlushHands) {
//			assertTrue(HandType.isStraightFlush(hand));
//		}
//
//	}
//
//	/**
//	 * This is the test method for the straight in HandType.
//	 */
//	@Test
//	public void testFullHouse() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : twoPairHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : threeOfAKindHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : fourOfAKindHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : sequence) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : straightHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : straightFlushHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : fullHouseHands) {
//			assertTrue(HandType.isFullHouse(hand));
//		}
//		for (ArrayList<Card> hand : royalFlushHands) {
//			assertFalse(HandType.isFullHouse(hand));
//		}
//
//	}
//
//	@Test
//	public void testIsRoyalFlush() {
//		for (ArrayList<Card> hand : singleCardHands) {
//			assertFalse(HandType.isRoyalFlush(hand));
//		}
//		for (ArrayList<Card> hand : onePairHands) {
//			assertFalse(HandType.isRoyalFlush(hand));
//		}
//		for (ArrayList<Card> hand : royalFlushHands) {
//			assertTrue(HandType.isRoyalFlush(hand)); // Two-pair contains a pair
//		}
//	}

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
		char r = in.charAt(0);
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
		if (s == 'w') 
			suit = Card.Suit.Swords;
		if (s == 'S')
			suit = Card.Suit.Stars;
		if (s == 'P')
			suit = Card.Suit.Pagodas;

		return new Card(suit, rank);
	}
}