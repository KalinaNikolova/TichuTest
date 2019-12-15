package com.dark.server;

import java.util.ArrayList;
import java.util.Collections;

import com.dark.client.Card;

//import org.junit.Before;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class represents a deck of playing cards. When initially created, the deck is filled and shuffled.
 * Later, the deck can be refilled and reshuffled by calling the "shuffle" method.
 */
public class DeckOfCards {
    private final ArrayList<Card> cards = new ArrayList<>();//
    private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();

    /**
     * We only ever have one deck of cards, so we do not set an ID attribute.
     */
    public DeckOfCards() {
        shuffle();
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
//	private String[][] playersCards = {
//			{ "2S", "9C", "3H", "5D", "7H" },//high card
//			{ "7S", "5C", "5H", "JD", "JH" },//2 pairs
//			{ "2S", "3D", "4S", "5S", "6S" },//straight
//			{ "AS", "AC", "QH", "JD", "QH" }//2 pairs
//			};
//	private String[][] playersCards = {
//			{ "2S", "9C", "3H", "5D", "7H" },//high card
//			{ "7S", "5C", "5H", "JD", "JH" },//2 pairs
//			{ "2S", "3D", "4S", "5S", "6S" },//straight
//			{ "AS", "AC", "QH", "QD", "QH" }//FullHouse
//			};
//	private String[][] playersCards = {
//			{ "2S", "9C", "3H", "5D", "7H" },//high card
//			{ "AS", "JS", "KS", "TS", "QS" },//royal straight flush
//			{ "2S", "3D", "4S", "5S", "6S" },//straight
//			{ "AS", "AC", "QH", "QD", "QH" }//FullHouse
//			};
	
//	private String[][] playersCards = {
//	{ "2S", "9C", "3H", "5D", "7H" },//high card
//	{ "7S", "5C", "5H", "JD", "JH" },//2 pairs
//	{ "2S", "4D", "4C", "5S", "4S" },//3 of a kind
//	{ "AS", "AC", "QH", "JD", "TH" }//1 pairs
//	};
//	private String[][] playersCards = {
//	{ "2S", "9C", "3H", "5D", "7H" },//high card
//	{ "2S", "4D", "4C", "6S", "4S" },//3 of a kind
//	{ "2S", "4D", "4C", "5S", "4S" },//3 of a kind
//	{ "AS", "AC", "QH", "JD", "TH" }//1 pairs
//	};
//	private String[][] playersCards = {
//	{ "2S", "9C", "3H", "5D", "7H" },//high card
//	{ "2S", "4D", "4C", "5S", "4S" },//3 of a kind
//	{ "2S", "4D", "4C", "5S", "4S" },//3 of a kind
//	{ "AS", "AC", "QH", "JD", "TH" }//1 pairs
//	};
	private String[][] playersCards = {
	{ "2C", "3C", "4C", "5C", "6C", "7C", "8C","9C", "TC", "JC", "QC", "KC", "AC"},//high card
	{ "2D", "3D", "4D", "5D", "6D", "7D", "8D","9D", "TD", "JD", "QD", "KD", "AD" },//3 of a kind
	{ "2H", "3H", "4H", "5H", "6H", "7H", "8H","9H", "TH", "JH", "QH", "KH", "AH" },//3 of a kind
	{ "2S", "3S", "4S", "5S", "6S", "7S", "8S","9S", "TS", "JS", "QS", "KS", "AS"}//1 pairs
	};
	
	// This is where we store the translated hands
	ArrayList<ArrayList<Card>> playerHands;
	
	/**
	 * The makeHands method is called before each test method,
	 * and prepares the translated hands. We recreate these for
	 * each test method, in case the test method damages the data.
	 */
	//@Before
	public void makeHands() {
		playerHands = makeHands(playersCards);
	}

    
    /**
     * Gather all 52 cards, and shuffle them
     */
    public void loadFromFile() {
        // Remove all cards
        cards.clear();
        makeHands();
        
        // Add all 52 cards
        for (ArrayList<Card> hand : playerHands) {
        	//add/remove players
            for (Card card : hand) {
                //Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        
        // Shuffle
        //Collections.shuffle(cards);
        cardsRemaining.setValue(cards.size());
    }

    /**
     * Take one card from the deck and return it
     * 
     * This is an example of conditional assignment
     */
    public Card dealCard() {
        Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
        cardsRemaining.setValue(cards.size());
        return card;
    }
    
	/**
	 * Make an ArrayList of hands from an array of string-arrays
	 */
	ArrayList<ArrayList<Card>> makeHands(String[][] handsIn) {
		ArrayList<ArrayList<Card>> handsOut = new ArrayList<>();
		for (String[] hand : handsIn) {
			handsOut.add(makeHand(hand));
		}
		return handsOut;
	}
	
	/**
	 * Make a hand (ArrayList<Card>) from an array of 5 strings
	 */
	ArrayList<Card> makeHand(String[] inStrings) {
		ArrayList<Card> hand = new ArrayList<>();
		for (String in : inStrings) {
			hand.add(makeCard(in));
		}
		return hand;
	}
	
	/**
	 * Create a card from a 2-character String.
	 * First character is the rank (2-9, T, J, Q, K, A) 
	 * Second character is the suit (C, D, H, S)
	 * 
	 * No validation or error handling!
	 */
	Card makeCard(String in) {
		char r = in.charAt(0);
		Card.Rank rank = null;
		if (r <= '9') rank = Card.Rank.values()[r-'0' - 2];
		else if (r == 'T') rank = Card.Rank.Ten;
		else if (r == 'J') rank = Card.Rank.Jack;
		else if (r == 'Q') rank = Card.Rank.Queen;
		else if (r == 'K') rank = Card.Rank.King;
		else if (r == 'A') rank = Card.Rank.Ace;
		
		char s = in.charAt(1);
		Card.Suit suit = null;
		if (s == 'J') suit = Card.Suit.Jade;
		if (s == 'P') suit = Card.Suit.Pagodas;
		if (s == 'S') suit = Card.Suit.Stars;
		if (s == 'W') suit = Card.Suit.Swords;

		return new Card(suit, rank);
	}
}
