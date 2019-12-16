package com.dark.client;

import java.util.ArrayList;
import java.util.Observable;

public class Deal extends Observable{
	private int index;
	private ArrayList<Card> cards;//final
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {//sets cards order!!!
		this.index = index;
		setChanged();
		notifyObservers(this);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
}
