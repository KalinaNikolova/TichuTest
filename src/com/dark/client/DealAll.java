package com.dark.client;

import java.util.ArrayList;
import java.util.Observable;

import com.dark.server.Card;

public class DealAll extends Observable{
	private int index;
	private ArrayList<ArrayList<Card>> cards;//final
	
	private ArrayList<Card> table;


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {//sets cards order!!!
		this.index = index;
		setChanged();
		notifyObservers(this);
	}

	public ArrayList<ArrayList<Card>> getCards() {
		return cards;
	}

	public void setCards(ArrayList<ArrayList<Card>> cards) {
		this.cards = cards;
	}
	
	public ArrayList<Card> getTable() {
		return table;
	}

	public void setTable(ArrayList<Card> table) {
		this.table = table;
	}
}

