package com.dark.common;

import java.util.ArrayList;

import com.dark.server.Card;

public class DealAllMsg extends Message{
	/**
	 * The body of this class was taken from WI_SoftwareEngineering-master.zip from Bradley Richards
	 * chatLab example
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Card>> cards;
	private ArrayList<Card> table;
	private int position;
	
//	public DealAllMsg(ArrayList<ArrayList<Card>> cards, int position) {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	public DealAllMsg(ArrayList<ArrayList<Card>> cards, int position,ArrayList<Card> table) {
		super(MessageType.DealAll);
		this.cards=cards;
		this.position=position;
		this.table = table;
	}
	public ArrayList<ArrayList<Card>> getCards() {
		return cards;
	}
	
	public ArrayList<Card> getTable() {
		return table;
	}

//
//	public void setCards(ArrayList<Card> cards) {
//		this.cards = cards;
//	}

	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}

	//comment
	@Override
	public String toString() {
		return type.toString() + '|' + cards + '|' + position + '|' + table;////see
	}
}
