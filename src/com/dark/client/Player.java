package com.dark.client;

import java.util.ArrayList;
/**
 * 
 * @author Kalina
 *
 */
public class Player {
    public static final int HAND_SIZE = 13;
    
    private final String playerName;
    private final ArrayList<Card> cards = new ArrayList<>();
    
    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
    
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public void addCard(Card card) {
        if (cards.size() < HAND_SIZE) cards.add(card);
    }
    
    public void discardHand() {
        cards.clear();
    }
    
    public int getNumCards() {
        return cards.size();
    }

}
