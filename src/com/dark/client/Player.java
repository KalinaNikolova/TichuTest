package com.dark.client;

import java.util.ArrayList;

import com.dark.server.Card;
/**
 * The body of this class was taken from  Poker game 2019 Kalina & Ralf
 * 
 */
public class Player {
    public static final int HAND_SIZE = 13;
    
    private String playerName;////final removed
    private ArrayList<Card> cards = new ArrayList<>();//////
    
    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
    //added
    public void setPlayerName(String name) {
        this.playerName=name;
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
