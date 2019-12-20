package com.dark.client;
/**
 * If do not use this class we should remove it before uopload
 */
import java.util.ArrayList;
import java.util.Observable;

import com.dark.server.Card;

public class Winner extends Observable{
	private int index;
	private ArrayList<Card> cards;//final
	
//	public Move(int index, ArrayList<Card> cards) {
//		super();
//		this.index = index;
//		this.cards = cards;
//		//notify  here
//	}

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
